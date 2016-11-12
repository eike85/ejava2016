package epod.web;

import epod.business.ProcessDeliveryTask;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

public class GetImageTask implements Runnable {

	private final AsyncContext ctx;
	private final Path root;
	private String path;

	public GetImageTask(AsyncContext c, String path, Path root) {
		this.ctx = c;
		this.root = root;
		this.path = path;
	}

	@Override
	public void run() {

		try {

			HttpServletResponse resp = (HttpServletResponse)ctx.getResponse();

			String[] terms = path.substring(1).split("/");
			if (terms.length < 2) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Path imgPath = Paths.get(root.toString(), path);
			if (!Files.exists(imgPath)) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			String imgType = path.substring(path.indexOf(".") + 1);

			resp.setContentType("image/" + imgType);
			resp.setStatus(HttpServletResponse.SC_OK);

			try (BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream())) {
				byte[] buffer = Files.readAllBytes(imgPath);
				bos.write(buffer, 0, buffer.length);
				bos.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} finally {
			ctx.complete();
		}
	}

	
	
}
