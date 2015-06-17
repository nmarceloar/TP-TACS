


package api.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.glassfish.hk2.api.Factory;

public class FacebookFactory implements Factory<Facebook> {
    
    @Inject
    private HttpSession session;
    
    @Override
    public Facebook provide() {
    
        return new Facebook(SessionUtils.extractToken(session));
        
    }
    
    @Override
    public void dispose(Facebook instance) {
    
    }
    
}
