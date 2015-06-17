


package api.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
public class LoggedUserIdValueFactoryProvider extends
    AbstractValueFactoryProvider {
    
    @Inject
    public LoggedUserIdValueFactoryProvider(
        MultivaluedParameterExtractorProvider mpep, ServiceLocator injector) {
    
        super(mpep, injector, Parameter.Source.UNKNOWN);
    }
    
    @Override
    public AbstractContainerRequestValueFactory<?>
        createValueFactory(Parameter parameter) {
    
        Class<?> classType = parameter.getRawType();
        
        if (classType == null || (!classType.equals(Long.class))) {
            return null;
        }
        
        return new LoggedUserIdValueFactory();
    }
    
}
