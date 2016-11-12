package epod.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/event")
public class EPODSocket {

	@Inject private EPODSessions sessions;

	@OnOpen
	public void onOpen(final Session s) {
		System.out.println(">> connected: " + s.getId());
		sessions.writeMutex(() -> {
			sessions.addSession(s);
		});
	}

	@OnClose
	public void onClose(final Session s) {
		System.out.println(">> disconnected: " + s.getId());
		sessions.writeMutex(() -> { 
			sessions.removeSession(s);
		});
	}

}
