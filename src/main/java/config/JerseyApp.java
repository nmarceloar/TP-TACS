package config;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import repository.RecommendationsRepository;
import repository.TripsRepository;
import repository.UsersRepository;
import repository.impl.OfyRecommendationRepository;
import repository.impl.OfyTripRepository;
import repository.impl.OfyUserRepository;
import services.AirlinesService;
import services.AirportsService;
import services.CitiesService;
import services.RecommendationsService;
import services.TripOptionsDescriptorService;
import services.TripOptionsService;
import services.TripsService;
import services.UsersService;
import services.impl.AirlinesServiceImpl;
import services.impl.AirportsServiceImpl;
import services.impl.CitiesServiceImpl;
import services.impl.OfyRecommendationsService;
import services.impl.OfyTripService;
import services.impl.OfyUserService;
import services.impl.TripOptionsDescriptorServiceImpl;
import services.impl.TripOptionsServiceImpl;

public class JerseyApp extends ResourceConfig {

	public JerseyApp() {

		super();

		this.packages("api");

		this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		this.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR,
			true);

		this.register(new AbstractBinder() {
			@Override
			protected void configure() {

				this.bind(TripOptionsDescriptorServiceImpl.class)
					.to(TripOptionsDescriptorService.class)
					.in(Singleton.class);

				this.bind(TripOptionsServiceImpl.class)
					.to(TripOptionsService.class)
					.in(Singleton.class);

				this.bind(CitiesServiceImpl.class)
					.to(CitiesService.class)
					.in(Singleton.class);

				this.bind(AirportsServiceImpl.class)
					.to(AirportsService.class)
					.in(Singleton.class);

				this.bind(AirlinesServiceImpl.class)
					.to(AirlinesService.class)
					.in(Singleton.class);

				this.bind(OfyUserService.class)
					.to(UsersService.class)
					.in(Singleton.class);

				this.bind(OfyUserRepository.class)
					.to(UsersRepository.class)
					.in(Singleton.class);

				this.bind(OfyTripService.class)
					.to(TripsService.class)
					.in(Singleton.class);

				this.bind(OfyTripRepository.class)
					.to(TripsRepository.class)
					.in(Singleton.class);

				this.bind(OfyRecommendationRepository.class)
					.to(RecommendationsRepository.class)
					.in(Singleton.class);

				this.bind(OfyRecommendationsService.class)
					.to(RecommendationsService.class)
					.in(Singleton.class);

			}
		});

	}

}
