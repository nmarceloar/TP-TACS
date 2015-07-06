package api.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import repository.impl.OfyUsersRepositoryImpl;
import services.Facebook;
import services.OfyUsersService;
import services.impl.OfyUsersServiceImpl;
import utils.SessionUtils;

public class LoginServlet extends HttpServlet {

	// / HACER REFACTOR DE ESTO !

	private static final long serialVersionUID = 1L;

	private OfyUsersService usersService;

	public LoginServlet() {

		super();

		// hay que encontrar la forma de injectar servicios en servlets con hk2.
		// lo dejo asi por el momento
		this.usersService = new OfyUsersServiceImpl(new OfyUsersRepositoryImpl());

	}

	private void destroySession(HttpSession session) {
		session.invalidate();
	}

	@Override
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");

		if (token == null) {

			this.noTokenResponse(response);

		}

		else {

			TokenInfo tokenInfo;

			if ((tokenInfo = Facebook.tokenInfo(token)).isValid()) {

				HttpSession session = request.getSession(false);

				if (session != null) {

					if (SessionUtils.extractToken(session).equals(token)) {

						this.existingSessionResponse(response);

					}

					else {

						this.destroySession(session);

						this.prepareSessionAndUser(request, response, token, tokenInfo);

						this.newOrUpdatedSessionResponse(response, tokenInfo);

					}

				} else {

					this.prepareSessionAndUser(request, response, token, tokenInfo);

					this.newSessionResponse(response, tokenInfo);

				}

			}

			else {

				this.invalidTokenResponse(response);

			}

		}

	}

	private void existingSessionResponse(HttpServletResponse response)
		throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().println("Ya habia sesion.");
		response.getOutputStream().flush();
	}

	private void invalidTokenResponse(HttpServletResponse response)
		throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getOutputStream().println("Token no valido.");
		response.getOutputStream().flush();
	}

	private void newOrUpdatedSessionResponse(HttpServletResponse response,
		TokenInfo tokenInfo) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().println("Ok.Se termino la sesion anterior." + "\n"
			+ "Se creo una nueva sesion. userId= "
			+ tokenInfo.getUserId());
		response.getOutputStream().flush();
	}

	private void newSessionResponse(HttpServletResponse response,
		TokenInfo tokenInfo) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().println("Ok.Se creo una nueva sesion. userId= " + tokenInfo.getUserId());
		response.getOutputStream().flush();
	}

	private void noTokenResponse(HttpServletResponse response)
		throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getOutputStream().println("No habia token!.");
		response.getOutputStream().flush();
	}

	private void prepareSessionAndUser(HttpServletRequest request,
		HttpServletResponse response, String token, TokenInfo tokenInfo)
		throws IOException {

		HttpSession session;

		synchronized (session = request.getSession(true)) {

			session.setAttribute(SessionUtils.USER_ID, tokenInfo.getUserId());
			session.setAttribute(SessionUtils.TOKEN, token);
			session.setMaxInactiveInterval(-1);
			// session.setAttribute(SessionUtils.EXPIRATION_DATE,
			// tokenInfo.getExpirationDate());

		}

		if (!this.usersService.exists(tokenInfo.getUserId())) {

			this.usersService.createUser(Facebook.getUserDetails(token));

		}

	}

}
