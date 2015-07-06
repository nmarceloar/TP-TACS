package api.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SessionUtils;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		if (inSession(request)) {

			SessionUtils.existingFrom(request).invalidate();

			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().println("Ok. Sesion terminada.");
			response.getOutputStream().flush();

		}

		else {

			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getOutputStream()
				.println("Error.\n" + "No habia sesion previa");
			response.getOutputStream().flush();
		}

	}

	private boolean inSession(HttpServletRequest request) {
		return SessionUtils.existingFrom(request) != null;
	}

}
