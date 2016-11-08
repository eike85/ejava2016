/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.json.Json;
import javax.websocket.Session;
import org.apache.jasper.tagplugins.jstl.ForEach;

/**
 *
 * @author swemon
 */
@ApplicationScoped
public class SocketSession implements Serializable {

    private List<Session> boards = new ArrayList();
    
   public void add(Session sess) {
       this.boards.add(sess);
   }
   
   public void broadcast(String text) {
        String msg = Json.createObjectBuilder()
                        .add("text", text)
                        .add("time", (new Date()).toString())
                        .build()
                        .toString();
        
      for (Session s: boards) {
            Set<Session> openSessions = s.getOpenSessions();
            
            for (Session session : openSessions) {
                 try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException ex) {
                Logger.getLogger(SocketSession.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
           
   }
       
   }
    
}
