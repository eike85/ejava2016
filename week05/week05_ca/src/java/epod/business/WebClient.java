package epod.business;

import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebClient {

	private static Client client = null;

	public static Optional<String> call(String url, long podId) {

		String ackId = UUID.randomUUID().toString().substring(0, 8);

		if (null == client)
			client = ClientBuilder.newClient();

		WebTarget target = client.target(url)
				.queryParam("podId", podId)
				.queryParam("ackId", ackId);

		Response response = target.request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		if (response.getStatus() >= 400)
			return (Optional.empty());

		return (Optional.of(ackId));
	}
	
}
