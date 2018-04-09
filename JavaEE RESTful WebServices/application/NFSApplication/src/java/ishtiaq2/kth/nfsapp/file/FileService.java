/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishtiaq2.kth.nfsapp.file;

/**
 *
 * @author ishtiaq
 */
import ishtiaq2.kth.nfsapp.user.UserService;
import ishtiaq2.kth.nfsapp.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class FileService {
    
    @PersistenceContext private EntityManager em;
    @Inject UserService userService;
    private int fileId = 0;
    private int commentsId = 0;
    
    public void addFile(FFile file) {
        em.persist(file);
    }
    
    public synchronized String createFile(FFile file, String uid) {
        if (fileId == 0) {
            Query query=em.createQuery("SELECT f.id FROM FFile f", FFile.class); //(SELECT MAX(u.id) FROM u)
            List<Integer> ids = query.getResultList();
            for (int i: ids) {
                System.out.println("IDs: " + i);
                if (i >= fileId) {
                    fileId = i;
                }
            }
            fileId++;
        }
        try {
            Map<Integer, User> loggedinUsers = userService.getLoggedinUsers();
            User user = loggedinUsers.get(Integer.parseInt(uid));
            TypedQuery<FFile> query = em.createQuery("SELECT f FROM FFile f where f.title = '" + file.getTitle() + "'", FFile.class);
            List<FFile> fle = query.getResultList();
            if (fle.size() > 0) {
                return "File with this title already exist";
            } else {            
                FFile newFile = new FFile(fileId, file.getTitle(), file.getContents());
                if (user != null) {
                    newFile.setOwner(user.getName());
                }
                addFile(newFile);
                fileId++;
            }
        } catch (Exception e) {
            return "Can not create File " + file.getTitle();
        }
        return "fileCreated";
    }
    
    public FFile getFile(int id) {
        System.out.println("File id = " + id);
        try {
            FFile file = retrieveFile(id);
            if (file != null) {
                TypedQuery<Comments> query = em.createQuery("SELECT c FROM Comments c WHERE c.fileId = " + file.getId(), Comments.class);
                List<Comments> comments = query.getResultList();
                List<String> com = new ArrayList<>();
                for (Comments s: comments) {
                    com.add(s.getComment());
                }
                file.setComments(com);
                return file;
            } 
        } catch (Exception e) {
            System.out.println("Error in getFile(): " + e);
            return new FFile(0, "Error in getFile()", "Error in getFile()");
        }
        return new FFile(0, "Not Found", "Not Found");
    }
    
    public FFile[] getFileList() {
        List<FFile> file = new ArrayList<>();
        try {
            TypedQuery<FFile> query = em.createQuery("SELECT f FROM FFile f", FFile.class);
            file = query.getResultList();
        } catch (Exception e) {
            
        }
        FFile[] fles = file.toArray(new FFile[file.size()]);
        return fles;
    }
    
    public String updateFile(FFile fle, String uid) {
        int userId = 0;
        try {
            userId = Integer.parseInt(uid);
        } catch (NumberFormatException e) {
            return "User Not loggedin";
        }
        Map<Integer, User> loggedinUsers = userService.getLoggedinUsers();
        User user = loggedinUsers.get(userId);
        TypedQuery<FFile> query = em.createQuery("SELECT f FROM FFile f where f.title = '" + fle.getTitle() + "'", FFile.class);
        List<FFile> f = query.getResultList();
        FFile file = f.get(0);
        if (file == null) {
            return "File not Found";
        }
        if (user.getName().equalsIgnoreCase(file.getOwner())) {
            try {
                String oldContents = file.getContents();
                String newContents = oldContents.concat("\r\n").concat(fle.getContents());
                Query query2 = em.createQuery("UPDATE FFile f SET f.contents = '" + newContents + "'" + " where f.title = '" + fle.getTitle() + "'");
                int updateCount = query2.executeUpdate();
                return "fileupdated";
            } catch (Exception e) {
                System.out.println("Error in query 2: " + e);
                return "an error occured";
            }
        } else {
            return "Unauthorized Update  attempt, only owner allowed";
        }      
    }
    
    public String commentFile(String filId, String comment, String uid) {
        int userId = Integer.parseInt(uid);
        if ((Object)userId instanceof Integer) {
            Map<Integer, User> loggedinUsers = userService.getLoggedinUsers();
            User user = loggedinUsers.get(userId);
        }        
        int id = Integer.parseInt(filId);
        FFile file = retrieveFile(id);
        
        if (commentsId == 0) {
            Query query = em.createQuery("SELECT c.id FROM Comments c", Comments.class); //(SELECT MAX(u.id) FROM u)
            List<Integer> ids = query.getResultList();
            for (int i: ids) {
                System.out.println("IDs: " + i);
                if (i >= commentsId) {
                    commentsId = i;
                }
            }
            commentsId++;
        }
        if (file != null ) {
            em.persist(new Comments(commentsId, id, comment));
            commentsId++;
            return "Comment added";
        }
        return "File Not Found";
    }
    
    
    public String deleteFile(String title, String uid) {
        FFile file = null;
        int userId = Integer.parseInt(uid);
        if ((Object)userId instanceof Integer) {
            Map<Integer, User> loggedinUsers = userService.getLoggedinUsers();
            User user = loggedinUsers.get(userId);
            TypedQuery<FFile> query = em.createQuery("SELECT f FROM FFile f where f.title = '" + title + "'", FFile.class);
            List<FFile> f = query.getResultList();
            file = f.get(0);
            if (user.getName().equalsIgnoreCase(file.getOwner())) {
                Query query2 = em.createQuery("DELETE FROM FFile f WHERE f.title = '" + file.getTitle() + "'");
                int deletedCount = query2.executeUpdate();
                Query query3 = em.createQuery("DELETE FROM Comments c WHERE c.fileId = " + file.getId());
                int deletedCount3 = query3.executeUpdate();
                return "File and Related Comments Deleted";
            } else {
                return "Unauthorized delete  attempt, only owner allowed";
            }
        }        
        return "File Not Found";        
    }
    
    public FFile retrieveFile(int id) {
        FFile file = null;
        try { 
            TypedQuery<FFile> query = em.createQuery("SELECT f FROM FFile f where f.id = " + id, FFile.class);
            List<FFile> f = query.getResultList();
            file = f.get(0);           
       } catch (Exception e) {
           System.out.println("Error in retreieveFile: " + e);
           return null;
       }        
       return file;
    }
}