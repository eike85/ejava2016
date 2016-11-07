/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;

import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;



/**
 *
 * @author swemon
 */

@SessionScoped
public class UserSession implements Serializable {
    
   @Inject private Principal user;
    
   public String getUsername() {
		return (user.getName());
	}
   public void setUserName(String n) { }
}
