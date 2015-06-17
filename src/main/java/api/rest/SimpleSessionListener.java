


package api.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SimpleSessionListener implements HttpSessionListener {
    
    private static final Logger LOG =
        Logger.getLogger(SimpleSessionListener.class.getCanonicalName());
    
    public SimpleSessionListener() {
    
    }
    
    public void sessionCreated(HttpSessionEvent se) {
    
        LOG.info("Nueva sesion creada: " + se.getSession()
            .getId());
        
    }
    
    public void sessionDestroyed(HttpSessionEvent se) {
    
        LOG.info("Sesion terminada: " + se.getSession()
            .getId());
    }
    
}
