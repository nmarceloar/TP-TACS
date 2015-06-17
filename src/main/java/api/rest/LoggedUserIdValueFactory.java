/**
 * 
 */



package api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;

public class LoggedUserIdValueFactory extends
    AbstractContainerRequestValueFactory<Long> {
    
    @Context
    private ResourceContext context;
    
    public Long provide() {
    
        return SessionUtils.extractUserId(context.getResource(
            HttpServletRequest.class)
            .getSession(false));
        
    }
}
