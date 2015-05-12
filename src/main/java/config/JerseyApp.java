package config;

import api.rest.AirportsResource;
import api.rest.CitiesResource;
import api.rest.FriendsResource;
import api.rest.PassengerResource;
import api.rest.RecommendationResource;
import api.rest.TripOptionsResource;
import api.rest.TripsResource;

import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JerseyApp extends ResourceConfig {

    private static final Logger LOGGER
            = Logger.getLogger(JerseyApp.class.getCanonicalName());

    public JerseyApp() {

        super();

		// di
//		this.register(new AbstractBinder() {
//			
//			@Override
//			protected void configure() {
//			
//				this.bind(Despegar.class)
//				    .to(TripOptionsProvider.class);
//				this.bind(Despegar.class)
//				    .to(AirportProvider.class);
//				this.bind(Despegar.class)
//				    .to(CityProvider.class);
//				
//			}
//		});
        // Enable Spring DI and Jackson configuration
        register(RequestContextFilter.class);
        // Application resources
        register(PassengerResource.class);
        register(FriendsResource.class);
        register(CitiesResource.class);
        register(TripOptionsResource.class);
        register(RecommendationResource.class);
        register(AirportsResource.class);
        register(TripsResource.class);

		// jackson and joda
        final com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider provider
                = new com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider();

        provider.setMapper(new ObjectMapper().registerModule(new JodaModule()));

        this.register(provider);

		// this.register(JacksonFeature.class);
		// packages
        this.packages(true, "api"); // y los que esten por debajo

    }
}
