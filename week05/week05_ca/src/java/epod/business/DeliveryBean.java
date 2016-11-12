package epod.business;

import epod.web.EPODSessions;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.StreamMessage;
import javax.sql.DataSource;

@MessageDriven(
		mappedName = "jms/delivery",
		activationConfig = {
			@ActivationConfigProperty(
					propertyName = "destinationType",
					propertyValue = "javax.jms.Queue"
			)
		}
)
public class DeliveryBean implements MessageListener {

	@Resource(lookup = "config/imgDir")
	private String imgDir;

	@Resource(lookup = "concurrent/myThreadPool")
	private ManagedScheduledExecutorService service;

	@Resource(lookup = "jdbc/ca3_pod")
	private DataSource ds;

	@Inject private EPODSessions sessions;

	private Random rand = new SecureRandom();

	@Override
	public void onMessage(Message message) {

		if (!(message instanceof StreamMessage))
			return;

		if (simulateNonRecepit())
			return;

		//service.submit(
				//new ProcessDeliveryTask((StreamMessage)message, imgDir, sessions));
		service.schedule(
				new ProcessDeliveryTask((StreamMessage)message, imgDir, sessions, ds),
				5, TimeUnit.SECONDS);
	}

	private boolean simulateNonRecepit() {
		return (0 != (Math.abs(rand.nextInt() % 5) % 2));
	}
}
