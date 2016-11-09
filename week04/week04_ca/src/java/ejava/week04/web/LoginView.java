package ejava.week04.web;

import ejava.week04.bean.NoteBean;
import ejava.week04.bean.SocketSession;
import ejava.week04.bean.UserBean;

import ejava.week04.entity.Users;
import ejava.week04.exception.UserExistedException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@ViewScoped
@Named
public class LoginView implements Serializable {
    
    @Inject
    UserBean userBean;
    
    @Inject
    SocketSession socketSession;
    
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private int count;

    public String getUsername() {
            return username;
    }
    public void setUsername(String username) {
            this.username = username;
    }

    public String getPassword() {
            return password;
    }
    public void setPassword(String password) {
            this.password = password;
    }

    public int getCount() {
            return count;
    }
    public void setCount(int count) {
            this.count = count;
    }
    
    public String login() {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequest();
        try {
          req.login(username, password);
          return ("/secure/menu");
        } catch (ServletException ex) {
                FacesMessage msg = new FacesMessage("Incorrect login");
                count++;
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return ("");
        }
    }
    
    public String register() {
        Users user = new Users();
        user.setUserid(username);
        user.setPassword(password);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userBean.createUser(user);
            System.out.println("Registration Successful");
            context.addMessage(null, new FacesMessage("Registration Successful. Please login."));
            return null;
        } catch (UserExistedException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            
            context.addMessage(null, new FacesMessage("User Already existed"));
            return null;
        } 
    }
}
