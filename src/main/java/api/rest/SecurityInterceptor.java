


package api.rest;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Security
public class SecurityInterceptor implements ContainerRequestFilter {
    
    @Inject
    private HttpServletRequest request;
    
    @Override
    public void
        filter(final ContainerRequestContext requestContext) throws IOException {
    
        if (!secure(request)) {
            
            requestContext.abortWith(Response.status(
                Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON)
                .build());
            
        }
        
    }
    
    private boolean secure(final HttpServletRequest request) {
    
        return (request.getSession(false) != null);
        
    }
    
    private boolean containsCookie(final HttpServletRequest request,
        final String cookieName) {
    
        final Cookie[] cookies;
        
        if ((cookies = request.getCookies()) != null) {
            
            for (final Cookie cookie : cookies) {
                
                if (cookie.getName()
                    .equals(cookieName)) {
                    return true;
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
}
