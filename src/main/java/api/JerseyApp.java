
package api;

import integracion.despegar.AirportProvider;
import integracion.despegar.CityProvider;
import integracion.despegar.Despegar;
import integracion.despegar.TripOptionsProvider;

import java.util.logging.Logger;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JerseyApp
    extends ResourceConfig {
	
	private static final Logger LOGGER =
	    Logger.getLogger(JerseyApp.class.getCanonicalName());
	
	public JerseyApp() {
	
		super();
		
		// di
		
		this.register(new AbstractBinder() {
			
			@Override
			protected void configure() {
			
				this.bind(Despegar.class).to(TripOptionsProvider.class);
				this.bind(Despegar.class).to(AirportProvider.class);
				this.bind(Despegar.class).to(CityProvider.class);
				
			}
		});
		
		// jackson and joda
		
		final com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider provider =
		    new com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider();
		
		provider.setMapper(new ObjectMapper().registerModule(new JodaModule()));
		
		this.register(provider);
		
		// this.register(JacksonFeature.class);
		
		// packages
		
		this.packages(true, "api"); // y los que esten por debajo
		
	}
}
