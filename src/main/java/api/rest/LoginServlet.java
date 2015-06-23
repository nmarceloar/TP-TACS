package api.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Facebook;
import services.OfyUserService;

public class LoginServlet extends HttpServlet {
	
	
	/// HACER REFACTOR DE ESTO !

	private static final long serialVersionUID = 1L;

	public LoginServlet() {

		super();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");

		if (token == null) {

			noTokenResponse(response);

		}

		else {

			TokenInfo tokenInfo;

			if ((tokenInfo = Facebook.tokenInfo(token)).isValid()) {

				HttpSession session = request.getSession(false);

				if (session != null) {

					if (SessionUtils.extractToken(session)
						.equals(token)) {

						existingSessionResponse(response);

					}

					else {

						destroySession(session);

						prepareSessionAndUser(request, response, token,
								tokenInfo);

						newOrUpdatedSessionResponse(response, tokenInfo);

					}

				} else {

					prepareSessionAndUser(request, response, token, tokenInfo);

					newSessionResponse(response, tokenInfo);

				}

			}

			else {

				invalidTokenResponse(response);

			}

		}

	}

	private void newSessionResponse(HttpServletResponse response,
			TokenInfo tokenInfo) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream()
			.println(
					"Ok.Se creo una nueva sesion. userId= "
							+ tokenInfo.getUserId());
		response.getOutputStream()
			.flush();
	}

	private void existingSessionResponse(HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream()
			.println("Ya habia sesion.");
		response.getOutputStream()
			.flush();
	}

	private void newOrUpdatedSessionResponse(HttpServletResponse response,
			TokenInfo tokenInfo) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream()
			.println(
					"Ok.Se termino la sesion anterior." + "\n"
							+ "Se creo una nueva sesion. userId= "
							+ tokenInfo.getUserId());
		response.getOutputStream()
			.flush();
	}

	private void destroySession(HttpSession session) {
		session.invalidate();
	}

	private void invalidTokenResponse(HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getOutputStream()
			.println("Token no valido.");
		response.getOutputStream()
			.flush();
	}

	private void noTokenResponse(HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getOutputStream()
			.println("No habia token!.");
		response.getOutputStream()
			.flush();
	}

	private void prepareSessionAndUser(HttpServletRequest request,
			HttpServletResponse response, String token, TokenInfo tokenInfo)
			throws IOException {

		HttpSession session;

		synchronized (session = request.getSession(true)) {

			session.setAttribute(SessionUtils.USER_ID, tokenInfo.getUserId());
			session.setAttribute(SessionUtils.TOKEN, token);
			session.setAttribute(SessionUtils.EXPIRATION_DATE,
					tokenInfo.getExpirationDate());

		}

		if (!OfyUserService.getInstance()
			.exists(tokenInfo.getUserId())) {

			OfyUserService.getInstance()
				.createUser(Facebook.getUserDetails(token));

		}

	}

}
