/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;


import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author swemon
 */

@RequestScoped
@Named
public class MenuView {
    
    @Inject
    private UserSession userSession;

    public String getUserName() {
        System.out.println("User Name: " + userSession.getUsername());
        return userSession.getUsername();
    }
    
    public String loggedOut(){
        System.out.println("Loggout");
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest req = (HttpServletRequest)ctx.getRequest();
                
                req.getSession().invalidate();
//		HttpServletResponse resp = (HttpServletResponse)ctx.getResponse();
//		try {
//		req.getRequestDispatcher("/logout")
//				.forward(req, resp);
//		} catch (Exception ex) { ex.printStackTrace();}
//		//FacesContext.getCurrentInstance().
        return "/login";
    }
}
