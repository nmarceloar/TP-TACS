/**
 * 
 */



package api.rest;

import javax.inject.Singleton;

import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;

@Singleton
public class LoggedUserIdInjectionResolver extends
    ParamInjectionResolver<LoggedUserId> {
    
    public LoggedUserIdInjectionResolver() {
    
        super(LoggedUserIdValueFactoryProvider.class);
    }
}
