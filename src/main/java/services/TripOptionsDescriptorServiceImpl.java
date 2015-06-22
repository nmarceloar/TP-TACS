package services;

import integracion.despegar.TripOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.joda.time.DateTime;

import api.rest.Airline;
import apis.TripOptionsService;

import com.google.appengine.api.ThreadManager;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class TripOptionsDescriptorServiceImpl implements
TripOptionsDescriptorService {

	private TripOptionsService tripOptionsService;
	private AirlinesService airlinesService;
	private AirportsService airportsService;

	public TripOptionsDescriptorServiceImpl() {

		this.tripOptionsService = new TripOptionsServiceImpl();
		this.airlinesService = new AirlinesServiceImpl();
		this.airportsService = new AirportsServiceImpl();

	}

	private List<Callable<Airline>> buildAirlineTasks(
			final TripOptions tripOptions) {

		return Lists.transform(
				new ArrayList<String>(tripOptions.getAirlinesCodes()),
				new Function<String, Callable<Airline>>() {

					@Override
					public Callable<Airline> apply(final String airlineCode) {

						return new Callable<Airline>() {

							@Override
							public Airline call() throws Exception {

								return TripOptionsDescriptorServiceImpl.this.airlinesService.findByCode(airlineCode);

							}

						};
					}
				});
	}

	private List<Callable<Airport>> buildAirportTasks(
			final TripOptions tripOptions) {

		return Lists.transform(
				new ArrayList<String>(tripOptions.getAiportsCodes()),
				new Function<String, Callable<Airport>>() {

					@Override
					public Callable<Airport> apply(final String airportCode) {

						return new Callable<Airport>() {

							@Override
							public Airport call() throws Exception {

								return TripOptionsDescriptorServiceImpl.this.airportsService.findByCode(airportCode);

							}

						};
					}
				});
	}

	private List<Airport> fecthAirports(
			final List<Callable<Airport>> airportTasks,
			final ExecutorService airportTasksExecutor) {

		try {

			return Lists.transform(
					airportTasksExecutor.invokeAll(airportTasks),
					new Function<Future<Airport>, Airport>() {

						@Override
						public Airport apply(final Future<Airport> input) {

							try {

								return input.get();

							} catch (final InterruptedException ex) {

								Thread.currentThread()
								.interrupt();

								throw new RuntimeException(ex.getMessage());

							} catch (final ExecutionException ex) {

								throw new RuntimeException(ex);

							} finally {

								airportTasksExecutor.shutdownNow();

							}

						}

					});

		} catch (InterruptedException ex) {

			Thread.currentThread()
			.interrupt();

			throw new RuntimeException(ex.getMessage());

		} finally {

			airportTasksExecutor.shutdownNow();

		}

	}

	private List<Airline> fetchAirlines(
			final List<Callable<Airline>> airlineTasks,
			final ExecutorService airportTasksExecutor) {

		try {

			return Lists.transform(
					airportTasksExecutor.invokeAll(airlineTasks),
					new Function<Future<Airline>, Airline>() {

						@Override
						public Airline apply(final Future<Airline> input) {

							try {

								return input.get();

							} catch (final InterruptedException ex) {

								throw new RuntimeException(ex);

							} catch (final ExecutionException ex) {

								throw new RuntimeException(ex);

							} finally {

								airportTasksExecutor.shutdownNow();

							}

						}

					});

		} catch (InterruptedException ex) {

			Thread.currentThread()
			.interrupt();

			throw new RuntimeException(ex.getMessage());

		} finally {

			airportTasksExecutor.shutdownNow();

		}

	}

	private TripOptions fetchTripOptions(String fromCity, String toCity,
			DateTime startDate, DateTime endDate, int offset, int limit) {

		return this.tripOptionsService.findTripOptions(fromCity, toCity,
				startDate, endDate, offset, limit);
	}

	@Override
	public TripOptionsDescriptor findTripOptions(String fromCity,
			String toCity, DateTime startDate, DateTime endDate, int offset,
			int limit) {

		TripOptions tripOptions = this.fetchTripOptions(fromCity, toCity,
				startDate, endDate, offset, limit);

		final List<Callable<Airport>> airportTasks = this.buildAirportTasks(tripOptions);

		final List<Callable<Airline>> airlineTasks = this.buildAirlineTasks(tripOptions);

		final List<Airport> airports = this.fecthAirports(
				airportTasks,
				Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory()));

		final List<Airline> airlines = this.fetchAirlines(
				airlineTasks,
				Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory()));

		return new TripOptionsDescriptor(tripOptions, airports, airlines);

	}

}
