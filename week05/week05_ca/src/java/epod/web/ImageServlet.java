package epod.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/image/*", asyncSupported = true)
public class ImageServlet extends HttpServlet {

	@Resource(lookup = "config/imgDir")
	private String imgDir;

	private Path imgPath = null;

	@Override
	public void init() throws ServletException {
		imgPath = Paths.get(imgDir);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String img = req.getPathInfo();
		if ((null == img) || (img.trim().length() <= 0)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}


		AsyncContext ctx = req.startAsync(req, resp);
		ctx.start(new GetImageTask(ctx, img, imgPath));
	}
}
