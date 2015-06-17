


package api.rest;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.joda.time.DateTime;

/**
 * Implemento esto porque no encuentro otra forma de hacer expirar la sesion en
 * un tiempo dado. App Engine no permite usar timers ni background threads. Si
 * alguno encuentra una mejor forma...
 * 
 * La sesion termina o bien despues del tiempo de inactividad (30min) o bien
 * despues de que pasa el tiempo de expiracion. Lo primero que se de.
 * 
 * No modificar las anotaciones del filtro !
 *
 */
@Provider
@Priority(Priorities.USER)
@Security
public class SessionExpirationInterceptor implements ContainerRequestFilter {
    
    @Context
    private HttpServletRequest request;
    
    @Context
    private HttpServletResponse response;
    
    @Override
    public void
        filter(ContainerRequestContext requestContext) throws IOException {
    
        HttpSession session = getCurrentSession();
        
        if (session == null) {
            
            throw new RuntimeException("NO había sesión!");
            
        }
        
        if (sessionTimeout(session)) {
            
            destroySession(session);
            
            removeCookies(request, response);
            
            requestContext.abortWith(Response.status(Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity("La sesion ha expirado.")
                .build());
            
        }
        
    }
    
    private HttpSession getCurrentSession() {
    
        return request.getSession(false);
    }
    
    private boolean sessionTimeout(HttpSession session) {
    
        return DateTime.now()
            .isAfter(SessionUtils.extractExpirationDate(session));
    }
    
    private void destroySession(HttpSession session) {
    
        session.invalidate();
    }
    
    private void removeCookies(final HttpServletRequest request,
        final HttpServletResponse response) {
    
        final Cookie[] cookies;
        
        if ((cookies = request.getCookies()) == null) {
            
            throw new RuntimeException("Aca pasó algo raro. No habia cookies");
            
        }
        
        for (Cookie cookie : cookies) {
            
            response.addCookie(new SecureCookie(cookie.getName(),
                cookie.getValue(), 0));
            
        }
        
    }
}
