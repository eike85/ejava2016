/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;

import ejava.week04.bean.SocketSession;
import java.io.IOException;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author swemon
 */
@RequestScoped
@ServerEndpoint("/board")
public class NoticeBoardEndPoint {
    
    @Inject
    SocketSession socketSession;
    
    @OnOpen
    public void open(Session sess) {
        socketSession.add(sess);
        System.out.println(">>> session id: " + sess.getId());
    }

    @OnMessage
    public void message(String text) {
       socketSession.broadcast(text);
    }
}
