// /**
// *
// */
//
//
//
// package services;
//
// import integracion.despegar.Airport;
// import integracion.despegar.City;
// import integracion.despegar.IATACode;
// import integracion.despegar.TripOptions;
//
// import java.io.IOException;
// import java.util.List;
// import java.util.concurrent.Callable;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
//
// import javax.inject.Named;
// import javax.inject.Singleton;
// import javax.ws.rs.client.Client;
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.client.ClientRequestContext;
// import javax.ws.rs.client.ClientRequestFilter;
// import javax.ws.rs.client.WebTarget;
// import javax.ws.rs.core.GenericType;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
//
// import org.glassfish.jersey.client.ClientProperties;
// import org.joda.time.DateTime;
// import org.joda.time.format.DateTimeFormat;
// import org.joda.time.format.DateTimeFormatter;
//
// import apis.AirportProvider;
// import apis.CityProvider;
// import apis.TripOptionsService;
//
// import com.fasterxml.jackson.datatype.joda.JodaMapper;
// import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
// import com.google.appengine.api.ThreadManager;
// import com.google.common.base.Preconditions;
//
// public class Despegar implements TripOptionsService, AirportProvider,
// CityProvider {
//
// private static final String ITINERARIES =
// "https://api.despegar.com/v3/flights/itineraries";
//
// private static final String AUTOCOMPLETE =
// "https://api.despegar.com/v3/autocomplete";
//
// private static final int MAX_CITIES = 10;
//
// private static final Client restClient = ClientBuilder.newClient()
// .property(ClientProperties.CONNECT_TIMEOUT, 0)
// .property(ClientProperties.READ_TIMEOUT, 0)
// .register(new JacksonJaxbJsonProvider(new JodaMapper(), null))
// .register(new ClientRequestFilter() {
//
// @Override
// public void
// filter(final ClientRequestContext requestContext) throws IOException {
//
// requestContext.getHeaders()
// .add("X-ApiKey", "19638437094c4892a8af7cdbed49ee43");
// }
// });
//
// public Despegar() {
//
// }
//
// @Override
// public List<Airport> findByIataCode(final List<String> iataCodes) {
//
// final ExecutorService executor =
// Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory());
//
// final List<Callable<Airport>> tasks =
// com.google.common.collect.Lists.newArrayList();
//
// for (final String iataCode : iataCodes) {
//
// tasks.add(new Callable<Airport>() {
//
// @Override
// public Airport call() throws Exception {
//
// return Despegar.this.findByIataCode(iataCode);
//
// }
//
// });
//
// }
//
// List<Future<Airport>> futureAirports = null;
//
// try {
//
// futureAirports = executor.invokeAll(tasks);
//
// } catch (final InterruptedException ex) {
//
// throw new RuntimeException("Error. Despegar");
//
// } finally {
//
// executor.shutdownNow();
//
// }
//
// final List<Airport> airports =
// com.google.common.collect.Lists.newArrayList();
//
// for (final Future<Airport> f : futureAirports) {
//
// try {
//
// airports.add(f.get());
//
// } catch (final Exception ex) {
//
// throw new RuntimeException("Error. Despegar");
//
// } finally {
//
// executor.shutdownNow();
//
// }
//
// }
//
// return airports;
// }
//
// @Override
// public Airport findByIataCode(final String iataCode) {
//
// IATACode.checkValid(iataCode);
//
// final Response response =
// Despegar.restClient.target(Despegar.AUTOCOMPLETE)
// .queryParam("query", iataCode)
// .queryParam("locale", "es_AR")
// .queryParam("airport_result", "10")
// .request(MediaType.APPLICATION_JSON)
// .get();
//
// if ((response.getStatus() == 200) && response.hasEntity()) {
//
// for (final Airport airport : response.readEntity(new
// GenericType<List<Airport>>() {})) {
//
// if (airport.codeEqualsTo(iataCode)) {
//
// return airport;
//
// }
//
// }
//
// }
//
// throw new RuntimeException("Error en la busquedad de aeropuertos");
//
// }
//
// @Override
// public List<City> findByName(final String name) {
//
// Preconditions.checkArgument((name != null) && (!name.isEmpty()));
//
// return Despegar.restClient.target(Despegar.AUTOCOMPLETE)
// .queryParam("query", name)
// .queryParam("locale", "es_AR")
// .queryParam("city_result", Despegar.MAX_CITIES)
// .request(MediaType.APPLICATION_JSON)
// .get()
// .readEntity(new GenericType<List<City>>() {});
//
// }
//
// @Override
// public TripOptions findTripOptions(final String fromCity,
// final String toCity,
// final DateTime startDate,
// final DateTime endDate,
// final int offset,
// final int limit) {
//
// final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
//
// final WebTarget target =
// Despegar.restClient.target(Despegar.ITINERARIES)
// .queryParam("site", "ar")
// .queryParam("from", fromCity)
// .queryParam("to", toCity)
// .queryParam("departure_date", fmt.print(startDate))
// .queryParam("return_date", fmt.print(endDate))
// .queryParam("adults", "1")
// .queryParam("offset", offset)
// .queryParam("limit", limit);
//
// final TripOptions op = target.request(MediaType.APPLICATION_JSON)
// .get(TripOptions.class);
//
// return op;
//
// }
// }
