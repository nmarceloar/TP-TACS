


package api.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/logout")
@Security
public class LogoutResource {
    
    private HttpSession getCurrentSession(final HttpServletRequest request) {
    
        return request.getSession(false);
        
    }
    
    private boolean inSession(final HttpServletRequest request) {
    
        return this.getCurrentSession(request) != null;
        
    }
    
    @GET
    public Response logout(@NotNull @Context final HttpServletRequest request,
        @NotNull @Context final HttpServletResponse response) {
    
        getCurrentSession(request).invalidate();
        
        removeCookies(request, response);
        
        return Response.ok()
            .type(MediaType.APPLICATION_JSON)
            .entity("Sesion terminada correctamente")
            .build();
        
    }
    
    private void removeCookies(final HttpServletRequest request,
        final HttpServletResponse response) {
    
        final Cookie[] cookies;
        
        if ((cookies = request.getCookies()) == null) {
            
            throw new RuntimeException("No habia cookies");
            
        }
        
        for (Cookie cookie : cookies) {
            
            response.addCookie(new SecureCookie(cookie.getName(),
                cookie.getValue(), 0));
            
        }
        
    }
}
