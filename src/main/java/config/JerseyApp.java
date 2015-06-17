


package config;

import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import api.rest.Facebook;
import api.rest.FacebookFactory;
import api.rest.HttpSessionFactory;
import api.rest.LoggedUserId;
import api.rest.LoggedUserIdInjectionResolver;
import api.rest.LoggedUserIdValueFactoryProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JerseyApp extends ResourceConfig {
    
    private static final Logger LOGGER =
        Logger.getLogger(JerseyApp.class.getCanonicalName());
    
    public JerseyApp() {
    
        super();
        
        // di
        // this.register(new AbstractBinder() {
        //
        // @Override
        // protected void configure() {
        //
        // this.bind(Despegar.class)
        // .to(TripOptionsProvider.class);
        // this.bind(Despegar.class)
        // .to(AirportProvider.class);
        // this.bind(Despegar.class)
        // .to(CityProvider.class);
        //
        // }
        // });
        //
        
        // Enable Spring DI and Jackson configuration
        this.register(RequestContextFilter.class);
        
        // // Application resources
        // this.register(PassengerResource.class);
        // this.register(FriendsResource.class);
        // this.register(CitiesResource.class);
        // this.register(RecommendationResource.class);
        // this.register(AirportsResource.class);
        // this.register(TripsResource.class);
        
        // jackson and joda
        final com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider provider =
            new com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider();
        provider.setMapper(new ObjectMapper().registerModule(new JodaModule()));
        this.register(provider);
        
        register(new AbstractBinder() {
            
            @Override
            protected void configure() {
            
                bindFactory(HttpSessionFactory.class).to(HttpSession.class)
                    .in(PerLookup.class);
                
                bindFactory(FacebookFactory.class).to(Facebook.class)
                    .in(PerLookup.class);
                
                bind(LoggedUserIdValueFactoryProvider.class).to(
                    ValueFactoryProvider.class)
                    .in(Singleton.class);
                
                bind(LoggedUserIdInjectionResolver.class).to(
                    new TypeLiteral<InjectionResolver<LoggedUserId>>() {})
                    .in(Singleton.class);
            }
        });
        
        // packages
        this.packages(true, "api");
        
        for (Class clazz : this.getClasses()) {
            
            LOGGER.info(clazz.getCanonicalName());
            
        }
        
    }
}
