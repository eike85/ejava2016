/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import ejava.week04.entity.Notes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
   
   public void broadcast(Collection<Notes> notes) {
       JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
       for (Notes note : notes) {
           JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                                .add("noteid", note.getNoteId())
                                                .add("title", note.getTitle())
                                                .add("category", note.getCategory())
                                                .add("content", note.getContent())
                                                .add("userid", note.getUsers().getUserid())
                                                .add("postedDate", note.getPostedDateTime().toString());
           jsonArrayBuilder.add(objBuilder);
       }
        String msg = jsonArrayBuilder.build().toString();
        System.out.println(msg);
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
