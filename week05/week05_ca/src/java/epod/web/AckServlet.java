package epod.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ack")
public class AckServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		System.out.println(">>> ack: " + req.getRequestURL().toString() + "?" + req.getQueryString());

		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
