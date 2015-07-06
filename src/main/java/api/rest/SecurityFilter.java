package api.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {

	public SecurityFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws IOException, ServletException {

		HttpServletRequest srequest = (HttpServletRequest)request;
		HttpServletResponse sresponse = (HttpServletResponse)response;

		if (srequest.getSession(false) == null) {

			sresponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			sresponse.getOutputStream()
				.println("No se permite el acceso a esta parte.\n" + "Se debe iniciar sesion primero");
			sresponse.getOutputStream().flush();

			return;

		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
