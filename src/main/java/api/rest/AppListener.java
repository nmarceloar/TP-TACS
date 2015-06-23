package api.rest;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import services.AirlinesServiceImpl;
import services.CitiesServiceImpl;
import services.OfyRecommendationsService;
import services.OfyTripServiceImpl;
import services.OfyUserService;
import services.TripOptionsDescriptorServiceImpl;
import services.TripOptionsServiceImpl;

public class AppListener implements ServletContextListener {

	public AppListener() {
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {

		Logger.getLogger(AppListener.class.getCanonicalName())
			.info("Preparando servicios...");

		CitiesServiceImpl.getInstance();
		AirlinesServiceImpl.getInstance();
		AirlinesServiceImpl.getInstance();
		TripOptionsServiceImpl.getInstance();
		TripOptionsDescriptorServiceImpl.getInstance();

		OfyTripServiceImpl.getInstance();
		OfyRecommendationsService.getInstance();
		OfyUserService.getInstance();

	}

}
