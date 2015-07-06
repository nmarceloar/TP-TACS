/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model2.impl.OfyTrip;
import model2.impl.OfyUser;

import org.junit.Assert;
import org.junit.Test;

import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import repository.impl.OfyRecommendationsRepositoryImpl;
import repository.impl.OfyTripsRepositoryImpl;
import repository.impl.OfyUsersRepositoryImpl;
import services.OfyTripsService;
import services.OfyUsersService;
import services.impl.OfyTripsServiceImpl;
import services.impl.OfyUsersServiceImpl;
import api.rest.UserDetails;
import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;

/**
 *
 * @author flpitu88
 */
public class TripServiceTest extends BaseOfyTest {

	@Test
	public void createTripTest() {

		// esto esta testeando varias cosas en realidad, separar.

		final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
		final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
		final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();

		final OfyTripsService tripsService = new OfyTripsServiceImpl(userRepo,
			tripRepo,
			recommendationRepo);

		final OfyUsersService usersService = new OfyUsersServiceImpl(userRepo);

		usersService.createUser(new UserDetails(1L,
			"user1",
			"mail",
			"link"));

		final OfyTrip trip = tripsService.createTrip(1L,
			buildTripDetails());

		Assert.assertNotEquals(null, trip);
		Assert.assertEquals(trip, tripsService.findById(trip.getId()));
		Assert.assertTrue(tripsService.findByOwner(1L)
			.size() == 1);
		Assert.assertTrue(tripsService.findByOwner(1L)
			.get(0)
			.equals(trip));

	}

	@Test
	public void findAcceptedByTargetTest() {

	}

	@Test
	public void findAllTest() {

		final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
		final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
		final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();

		final OfyTripsService tripsService = new OfyTripsServiceImpl(userRepo,
			tripRepo,
			recommendationRepo);

		final OfyUser user1 = OfyUser.createFrom(1L,
			"user1",
			"fbuser1",
			"user1@facebook.com");

		userRepo.add(user1);

		Assert.assertTrue(userRepo.exists(1L));
		Assert.assertFalse(userRepo.findAll()
			.isEmpty());
		Assert.assertTrue(userRepo.findById(1L)
			.getFacebookLink()
			.equals(user1.getFacebookLink()));

		final OfyTrip trip = tripsService.createTrip(1L,
			buildTripDetails());

		Assert.assertTrue(trip != null);
		Assert.assertTrue(!tripsService.findAll()
			.isEmpty());

	}

	private TripDetails buildTripDetails() {

		final City c1 = new City("BUE", "Buenos Aires", 4566.321, 56565.34);
		final City c2 = new City("ROM", "Roma", 4566.321, 56565.34);
		final PriceDetail pd = new PriceDetail("ARS", 5545.12);

		Airport fromAirport = new Airport("EZE", "Ezeiza", 123.12, 564.12);
		Airport toAirport = new Airport("MOR",
			"Aeropuerto de Roma",
			123.12,
			564.12);

		Airline airline = new Airline("AE1", "aerolinea1");

		Segment segment = new Segment(fromAirport,
			toAirport,
			airline,
			"fid1",
			new Date(),
			new Date(),
			"45:45");

		Segment segment2 = new Segment(toAirport,
			fromAirport,
			airline,
			"fid1",
			new Date(),
			new Date(),
			"45:45");

		final List<Segment> outit = Arrays.asList(segment);
		final List<Segment> init = Arrays.asList(segment2);

		TripDetails td = new TripDetails(c1, c2, pd, outit, init);

		return td;

	}

}
