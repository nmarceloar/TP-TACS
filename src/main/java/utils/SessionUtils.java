package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

public class SessionUtils {

	public final static String TOKEN = "TOKEN";
	public final static String EXPIRATION_DATE = "EXPIRATION_DATE";
	public final static String USER_ID = "USERID";

	public static String extractToken(HttpSession session) {

		return (String)session.getAttribute(TOKEN);

	}

	public static DateTime extractExpirationDate(HttpSession session) {

		return (DateTime)session.getAttribute(EXPIRATION_DATE);

	}

	public static Long extractUserId(HttpSession session) {

		return (Long)session.getAttribute(USER_ID);

	}

	public static HttpSession existingFrom(HttpServletRequest request) {

		return request.getSession(false);

	}

}
