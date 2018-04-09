/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishtiaq2.kth.nfsapp.jsonsupport;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ishtiaq
 */
@XmlRootElement
public class UpdateUser {
    String name;
    String password;
    String newName;
    String newPassword;
    
    public UpdateUser() {}
    public UpdateUser(String name, String password, String newName, String newPassword) {
        this.name = name;
        this.password = password;
        this.newName = newName;
        this.newPassword = newPassword;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    
    public void setNewName(String newName) {
        this.newName = newName;
    }
    public String getNewName() {
        return newName;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    
}
