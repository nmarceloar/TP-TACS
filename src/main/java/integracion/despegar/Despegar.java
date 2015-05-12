/**
 *
 */

package integracion.despegar;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.appengine.api.ThreadManager;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Despegar
    implements TripOptionsProvider, AirportProvider, CityProvider {
	
	private static final String ITINERARIES =
	    "https://api.despegar.com/v3/flights/itineraries";
	
	private static final String AUTOCOMPLETE =
	    "https://api.despegar.com/v3/autocomplete";
	
	private static final int MAX_CITIES = 10;
	
	private final Client restClient;
	
	public Despegar() {
	
		this.restClient = ClientBuilder.newClient();
		
		this.restClient.register(new JacksonJaxbJsonProvider(new JodaMapper(),
		    null));
		
		this.restClient.register(new ClientRequestFilter() {
			
			@Override
			public void filter(final ClientRequestContext requestContext)
			    throws IOException {
			
				requestContext.getHeaders().add("X-ApiKey",
				    "19638437094c4892a8af7cdbed49ee43");
			}
		});
		
		restClient.property(ClientProperties.CONNECT_TIMEOUT, 10000);
		restClient.property(ClientProperties.READ_TIMEOUT, 10000);
	}
	
	public Airport findByIataCode(final String iataCode) {
	
		final Response response =
		    this.restClient.target(Despegar.AUTOCOMPLETE)
		        .queryParam("query", iataCode)
		        .queryParam("locale", "es_AR")
		        .queryParam("airport_result", "10")
		        .request(MediaType.APPLICATION_JSON)
		        .get();
		
		if ((response.getStatus() == 200) && response.hasEntity()) {
			
			for (final Airport airport : response.readEntity(new GenericType<List<Airport>>() {
			})) {
				
				if (airport.codeEqualsTo(iataCode)) {
					return airport;
				}
				
			}
			
		}
		
		throw new RuntimeException("Error en la busquedad de aeropuertos");
		
	}
	
	@Override
	public List<City> findByName(final String name) {
	
		Preconditions.checkArgument((name != null) && (!name.isEmpty()));
		
		return this.restClient.target(Despegar.AUTOCOMPLETE).queryParam(
		    "query", name).queryParam("locale", "es_AR").queryParam(
		    "city_result", Despegar.MAX_CITIES).request(
		    MediaType.APPLICATION_JSON).get().readEntity(
		    new GenericType<List<City>>() {
		    });
		
	}
	
	@Override
	public TripOptions findTripOptions(final String fromCity,
	    final String toCity, final DateTime startDate, final DateTime endDate,
	    int offset, int limit) {
	
		Preconditions.checkNotNull(fromCity);
		Preconditions.checkNotNull(toCity);
		Preconditions.checkNotNull(startDate);
		Preconditions.checkNotNull(endDate);
		Preconditions.checkNotNull(offset);
		
		Preconditions.checkArgument((startDate.isAfterNow()) &&
		    (endDate.isAfter(startDate)),
		    "Error. Las fechas {0} y {1}  no representan un rango válido",
		    startDate, endDate);
		
		Preconditions.checkArgument(offset >= 0);
		Preconditions.checkArgument(limit >= 1);
		
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		ExecutorService executor =
		    Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory());
		
		final WebTarget target =
		    this.restClient.target(Despegar.ITINERARIES)
		        .queryParam("site", "ar")
		        .queryParam("from", fromCity)
		        .queryParam("to", toCity)
		        .queryParam("departure_date", fmt.print(startDate))
		        .queryParam("return_date", fmt.print(endDate))
		        .queryParam("adults", "1")
		        .queryParam("offset", offset)
		        .queryParam("limit", limit);
		
		final List<Future<TripOptions>> options;
		
		try {
			options =
			    executor.invokeAll(Lists.newArrayList(new Callable<TripOptions>() {
				    
				    @Override
				    public TripOptions call() throws Exception {
				    
					    return target.request(MediaType.APPLICATION_JSON).get(
					        TripOptions.class);
					    
				    }
				    
			    }));
			
		} catch (InterruptedException ex) {
			
			throw new RuntimeException("Error. Despegar");
			
		} finally {
			
			executor.shutdownNow();
		}
		
		TripOptions op = null;
		
		try {
			
			op = options.get(0).get();
			
		} catch (Exception ex) {
			
			throw new RuntimeException("Error. Despegar");
			
		} finally {
			
			executor.shutdownNow();
			
		}
		
		return op;
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see integracion.despegar.AirportProvider#findByIataCode(java.util.List)
	 */
	@Override
	public List<Airport> findByIataCode(final List<String> iataCodes) {
	
		ExecutorService executor =
		    Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory());
		
		final List<Callable<Airport>> tasks =
		    com.google.common.collect.Lists.newArrayList();
		
		for (final String iataCode : iataCodes) {
			
			tasks.add(new Callable<Airport>() {
				
				@Override
				public Airport call() throws Exception {
				
					return findByIataCode(iataCode);
					
				}
				
			});
			
		}
		
		List<Future<Airport>> futureAirports = null;
		
		try {
			
			futureAirports = executor.invokeAll(tasks);
			
		} catch (InterruptedException ex) {
			
			throw new RuntimeException("Error. Despegar");
			
		} finally {
			
			executor.shutdownNow();
			
		}
		
		List<Airport> airports = com.google.common.collect.Lists.newArrayList();
		
		for (Future<Airport> f : futureAirports) {
			
			try {
				
				airports.add(f.get());
				
			} catch (Exception ex) {
				
				throw new RuntimeException("Error. Despegar");
				
			} finally {
				
				executor.shutdownNow();
				
			}
			
		}
		
		return airports;
	}
}
