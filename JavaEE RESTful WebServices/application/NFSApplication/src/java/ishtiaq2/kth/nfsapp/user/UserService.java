package ishtiaq2.kth.nfsapp.user;

import ishtiaq2.kth.nfsapp.jsonsupport.Register;
import ishtiaq2.kth.nfsapp.jsonsupport.UpdateUser;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class UserService implements Serializable {
    
    @PersistenceContext private EntityManager em;
    
    
    private Map<Integer, User> loggedinUsers = new HashMap<>();
    private int userId = 0;
    int userSessionId = 1;
    
    public UserService() {
    }
    
    public Map<Integer, User> getLoggedinUsers() {
        return loggedinUsers;
    }
    
    public void addUser(User user) {
        em.persist(user);
    }
    
    public synchronized String  createUser(Register register) {
        if (userId == 0) {
            Query query=em.createQuery("SELECT u.id FROM User u", User.class); //(SELECT MAX(u.id) FROM u)
            List<Integer> ids = query.getResultList();
            for (int i: ids) {
                if (i >= userId) {
                    userId = i;
                }
            }
            userId++;
        }
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.name = '" + register.getName() + "'", User.class); //instead retrieve user using: register.getName(), register.getPasswor()
        List<User> usr = query.getResultList();
        if (usr.size() > 0) {
            return "User with this name already exist, choose another user name";
        } else {
            String hashPassword = calculateHash(register.getName(), register.getPassword(), String.valueOf(userId));
            User user = new User(userId, register.getName(), hashPassword, "user");
            addUser(user);
            userId++;
        }
        return "usercreated";
    }
    
    public String removeUser(String name, String password) {
        User user = retrieveUser(name, password);
        if (user != null ) {
            loggedinUsers.remove(user.getId());     //need treatment.....................................................................
            try { 
                Query query = em.createQuery("DELETE FROM User u WHERE u.name = '" + user.getName() + "'");
                int deletedCount = query.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error in removeUser()" + e);
                    return "Not Found";
               }
            return "removed";
        }
        return "Not Found";
    }
    
    public String[] getUser(String name, String password) {
        User user = retrieveUser(name, password);
        String[] response = new String[3];
        if (user != null ) {
            loggedinUsers.put(userSessionId, user);
            response[0] = "loggedin,";
            response[1] = String.valueOf(userSessionId) +",";
            response[2] = user.getName();
            userSessionId++;
            return response;
        }    
        response[0] = "User Not Found,";
        response[1] = "User Not Found,";
        response[2] = "User Not Found";
        return response;
    }
    
    public String[] logOutUser(String uid) {
        String[] response = new String[3];
        loggedinUsers.remove(Integer.parseInt(uid));
        response[0] = "loggedout,";
        response[1] = "loggedout,";
        response[2] = "loggedout";
        return response;
    }
    
    public String updateUser(UpdateUser updateUser) {
        User user = retrieveUser(updateUser.getName(), updateUser.getPassword());
        if (user != null ) {
            String newHashPassword = calculateHash(user.getName(), updateUser.getNewPassword(), String.valueOf(user.getId()));
            Query query = em.createQuery("UPDATE User u SET u.password = '" + newHashPassword + "'" +  " WHERE u.name = '" + user.getName() + "'");
            int updateCount = query.executeUpdate();
            return "updated";
        }
        return "not found";
    }
    
    public String makeAdmin(Register updateUser) {
        User user = retrieveUser(updateUser.getName(), updateUser.getPassword());
        if (user != null ) {
            Query query = em.createQuery("UPDATE User u SET u.category = 'ADMIN'  WHERE u.name = '" + user.getName() + "'");
            int updateCount = query.executeUpdate();
            return updateUser.getName() + " added to the list of admins";
        }
        return updateUser.getName() + " not found to make Admin";
    }
    
    public User retrieveUser(String name, String password) {
        User user = null;
        try {
            TypedQuery<User> query2 = em.createQuery("SELECT e FROM User e where e.name = '" + name + "'", User.class);
            List<User> u2 = query2.getResultList();
            User user2 = u2.get(0);
            String salt = String.valueOf(user2.getId());
            String hashPassword = calculateHash(name, password, salt);
            TypedQuery<User> query = em.createQuery("SELECT e FROM User e where e.name = '" + name + "' and e.password = '" + hashPassword + "'", User.class);
            List<User> u = query.getResultList();
            user = u.get(0);
       } catch (Exception e) {
       }
       return user;
    }
    
    public String calculateHash(String name, String password, String salt) {
        String passwordLiterals = name.concat(password).concat(salt);
        StringBuilder sb = new StringBuilder();
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordLiterals.getBytes());
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());
        } catch (NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }
}
