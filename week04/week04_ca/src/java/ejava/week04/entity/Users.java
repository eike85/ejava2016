/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author swemon
 */
@Entity
public class Users implements Serializable {

    @Id
    private String userid;
    
    private String password;
    
    @OneToMany(mappedBy = "users")
    private List<Groups> groups;

    @OneToMany(mappedBy = "users")
    private List<Notes> notes;
    
    public Users(){
    }
    
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Notes> getNotes() {
        return notes;
    }
    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }    
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Groups> getGroups() {
        return groups;
    }
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
}
