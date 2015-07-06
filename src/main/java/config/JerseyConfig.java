package config;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import repository.impl.OfyRecommendationsRepositoryImpl;
import repository.impl.OfyTripsRepositoryImpl;
import repository.impl.OfyUsersRepositoryImpl;
import services.AirlinesService;
import services.AirportsService;
import services.CitiesService;
import services.OfyRecommendationsService;
import services.OfyTripsService;
import services.OfyUsersService;
import services.TripOptionsDescriptorService;
import services.TripOptionsService;
import services.impl.AirlinesServiceImpl;
import services.impl.AirportsServiceImpl;
import services.impl.CitiesServiceImpl;
import services.impl.OfyRecommendationsServiceImpl;
import services.impl.OfyTripsServiceImpl;
import services.impl.OfyUsersServiceImpl;
import services.impl.TripOptionsDescriptorServiceImpl;
import services.impl.TripOptionsServiceImpl;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {

		super();

		this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		this.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR,
			true);

		this.packages("api");

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

				this.bind(OfyUsersServiceImpl.class)
					.to(OfyUsersService.class)
					.in(Singleton.class);

				this.bind(OfyUsersRepositoryImpl.class)
					.to(OfyUsersRepository.class)
					.in(Singleton.class);

				this.bind(OfyTripsServiceImpl.class)
					.to(OfyTripsService.class)
					.in(Singleton.class);

				this.bind(OfyTripsRepositoryImpl.class)
					.to(OfyTripsRepository.class)
					.in(Singleton.class);

				this.bind(OfyRecommendationsRepositoryImpl.class)
					.to(OfyRecommendationsRepository.class)
					.in(Singleton.class);

				this.bind(OfyRecommendationsServiceImpl.class)
					.to(OfyRecommendationsService.class)
					.in(Singleton.class);

			}
		});

	}

}
