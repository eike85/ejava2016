package epod.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	@Resource(lookup = "jms/connectionFactory")
	private ConnectionFactory factory;

	@Resource(lookup = "jms/delivery")
	private Queue deliveryQueue;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String teamId = req.getParameter("teamId");
		String podId = req.getParameter("podId");
		String callback = req.getParameter("callback");
		if (Objects.isNull(teamId) || Objects.isNull(podId) || Objects.isNull(callback)) {
			resp.setContentType(MediaType.APPLICATION_JSON);
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try (PrintWriter pw = resp.getWriter()) {
				writeError("Missing teamId, podId or callback", pw);
			}
			return;
		}

		String note = Objects.toString(req.getParameter("note"), "");
		Part imagePart = req.getPart("image");
		byte[] image = readPart(imagePart);

		if ((null == image) || (image.length <= 0)) {
			resp.setContentType(MediaType.APPLICATION_JSON);
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try (PrintWriter pw = resp.getWriter()) {
				writeError("Missing image", pw);
			}
			return;
		}

		try (JMSContext ctx = factory.createContext()) {
			JMSProducer prod = ctx.createProducer();
			StreamMessage strMsg = ctx.createStreamMessage();
			strMsg.clearBody();
			strMsg.writeString(teamId);
			strMsg.writeLong(Long.parseLong(podId));
			strMsg.writeString(note);
			strMsg.writeString(callback);
			strMsg.writeString(imageType(imagePart.getContentType()));
			strMsg.writeInt(image.length);
			strMsg.writeBytes(image);
			strMsg.reset();
			prod.send(deliveryQueue, strMsg);
		} catch (JMSException ex) {
			ex.printStackTrace();
			resp.setContentType(MediaType.APPLICATION_JSON);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			try (PrintWriter pw = resp.getWriter()) {
				writeError(ex.getMessage(), pw);
			}
			return;
		}

		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

	private void writeError(String msg, PrintWriter pw) {
		JsonObject obj = Json.createObjectBuilder()
				.add("message", msg)
				.build();
		pw.println(obj.toString());
	}

	private String imageType(String str) {
		int i = str.indexOf("/");
		return (str.substring(i +1));
	}

	private byte[] readPart(Part p) throws IOException  {
		if (null == p)
			return (null);

		byte[] buffer = new byte[1024 * 8];
		int sz = 0;
		try (BufferedInputStream bis = new BufferedInputStream(p.getInputStream())) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (-1 != (sz = bis.read(buffer))) 
				baos.write(buffer, 0, sz);
			buffer = baos.toByteArray();
			baos.close();
		}
		return (buffer);
	}
}
