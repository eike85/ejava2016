package epod.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.websocket.Session;

@ApplicationScoped
public class EPODSessions {

	private final Map<String, Session> sessions = new HashMap<>();
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public void addSession(Session s) {
		sessions.put(s.getId(), s);
	}

	public void removeSession(Session s) {
		sessions.remove(s.getId());
	}

	public void broadcast(JsonObject msg) {
		final String text = msg.toString();
		for (Session s: sessions.values())
			try {
				s.getBasicRemote().sendText(text);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

	public void writeMutex(Runnable r) {
		final Lock write = rwLock.writeLock();
		write.lock();
		try {
			r.run();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			write.unlock();
		} 
	}
	public void readMutex(Runnable r) {
		final Lock read = rwLock.readLock();
		read.lock();
		try {
			r.run();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			read.unlock();
		} 
	}
	
}
