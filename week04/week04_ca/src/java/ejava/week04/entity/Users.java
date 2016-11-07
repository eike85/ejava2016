/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author swemon
 */
@Entity
public class Users implements Serializable {

    @Id
    String userid;  
    String password;
    
    public Users(){
        
        
    }
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
