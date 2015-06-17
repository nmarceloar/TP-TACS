//
//
//
// package api.rest;
//
// import integracion.despegar.Airport;
// import integracion.despegar.TripOptions;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.Callable;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
//
// import javax.inject.Inject;
// import javax.validation.constraints.NotNull;
// import javax.ws.rs.DefaultValue;
// import javax.ws.rs.GET;
// import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.QueryParam;
//
// import org.joda.time.format.DateTimeFormat;
// import org.joda.time.format.DateTimeFormatter;
//
// import apis.AirportProvider;
// import apis.TripOptionsService;
//
// import com.google.appengine.api.ThreadManager;
// import com.google.common.base.Function;
// import com.google.common.collect.Lists;
//
// @Path("/trip-options")
// @AuthenticationNeeded
// public class TripOptionsResource {
//
// private final TripOptionsService provider;
//
// private final AirlinesService airlinesService;
//
// private final AirportProvider airportProvider;
//
// @Inject
// public TripOptionsResource(final TripOptionsService provider,
// final AirlinesService airlinesService,
// final AirportProvider airportProvider) {
//
// this.provider = provider;
// this.airlinesService = airlinesService;
// this.airportProvider = airportProvider;
//
// }
//
// private List<Callable<Airline>>
// buildAirlineTasks(final TripOptions tripOptions) {
//
// return Lists.transform(new ArrayList<String>(
// tripOptions.getAirlinesCodes()),
// new Function<String, Callable<Airline>>() {
//
// @Override
// public Callable<Airline> apply(final String airlineCode) {
//
// return new Callable<Airline>() {
//
// @Override
// public Airline call() throws Exception {
//
// return airlinesService.findByCode(airlineCode);
//
// }
//
// };
// }
// });
// }
//
// private List<Callable<Airport>>
// buildAirportTasks(final TripOptions tripOptions) {
//
// return Lists.transform(new ArrayList<String>(
// tripOptions.getAiportsCodes()),
// new Function<String, Callable<Airport>>() {
//
// @Override
// public Callable<Airport> apply(final String airportCode) {
//
// return new Callable<Airport>() {
//
// @Override
// public Airport call() throws Exception {
//
// return TripOptionsResource.this.airportProvider.findByIataCode(airportCode);
//
// }
//
// };
// }
// });
// }
//
// private List<Airport>
// fecthAirports(final List<Callable<Airport>> airportTasks,
// final ExecutorService airportTasksExecutor) {
//
// try {
//
// return Lists.transform(
// airportTasksExecutor.invokeAll(airportTasks),
// new Function<Future<Airport>, Airport>() {
//
// @Override
// public Airport apply(final Future<Airport> input) {
//
// try {
//
// return input.get();
//
// } catch (final InterruptedException ex) {
//
// Thread.currentThread()
// .interrupt();
//
// throw new RuntimeException(ex.getMessage());
//
// } catch (final ExecutionException ex) {
//
// throw new RuntimeException(ex);
//
// } finally {
//
// airportTasksExecutor.shutdownNow();
//
// }
//
// }
//
// });
//
// } catch (InterruptedException ex) {
//
// Thread.currentThread()
// .interrupt();
//
// throw new RuntimeException(ex.getMessage());
//
// } finally {
//
// airportTasksExecutor.shutdownNow();
//
// }
//
// }
//
// private List<Airline>
// fetchAirlines(final List<Callable<Airline>> airlineTasks,
// final ExecutorService airportTasksExecutor) {
//
// try {
//
// return Lists.transform(
// airportTasksExecutor.invokeAll(airlineTasks),
// new Function<Future<Airline>, Airline>() {
//
// @Override
// public Airline apply(final Future<Airline> input) {
//
// try {
//
// return input.get();
//
// } catch (final InterruptedException ex) {
//
// throw new RuntimeException(ex);
//
// } catch (final ExecutionException ex) {
//
// throw new RuntimeException(ex);
//
// } finally {
//
// airportTasksExecutor.shutdownNow();
//
// }
//
// }
//
// });
//
// } catch (InterruptedException ex) {
//
// Thread.currentThread()
// .interrupt();
//
// throw new RuntimeException(ex.getMessage());
//
// } finally {
//
// airportTasksExecutor.shutdownNow();
//
// }
//
// }
//
// private TripOptions fetchTripOptions(final String fromCity,
// final String toCity,
// final String startDate,
// final String endDate,
// final int offset,
// final int limit,
// final DateTimeFormatter fmt) {
//
// return this.provider.findTripOptions(fromCity, toCity,
// fmt.parseDateTime(startDate), fmt.parseDateTime(endDate), offset,
// limit);
//
// }
//
// @GET
// @Produces("application/json")
// public TripOptions
// findTripOptions(@NotNull @QueryParam("fromCity") final String fromCity,
// @NotNull @QueryParam("toCity") final String toCity,
// @NotNull @QueryParam("startDate") final String startDate,
// @NotNull @QueryParam("endDate") final String endDate,
// @NotNull @DefaultValue(value = "0") @QueryParam("offset") final int offset,
// @NotNull @DefaultValue(value = "1") @QueryParam("limit") final int limit) {
//
// // tener en cuenta el formato en la UI por el momento
// final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
//
// final TripOptions tripOptions =
// this.fetchTripOptions(fromCity, toCity, startDate, endDate, offset,
// limit, fmt);
//
// final List<Callable<Airport>> airportTasks =
// buildAirportTasks(tripOptions);
//
// final List<Callable<Airline>> airlineTasks =
// buildAirlineTasks(tripOptions);
//
// final ExecutorService airportTasksExecutor =
// Executors.newFixedThreadPool(airportTasks.size(),
// ThreadManager.currentRequestThreadFactory());
//
// final ExecutorService airlineTasksExecutor =
// Executors.newFixedThreadPool(airlineTasks.size(),
// ThreadManager.currentRequestThreadFactory());
//
// final List<Airport> airports =
// fecthAirports(airportTasks, airportTasksExecutor);
//
// final List<Airline> airlines =
// fetchAirlines(airlineTasks, airlineTasksExecutor);
//
// return new TripOptions(tripOptions, airports, airlines);
//
// }
//
// }
