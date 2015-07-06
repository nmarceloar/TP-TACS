/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model2.Recommendation.Status;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;

import org.junit.Assert;
import org.junit.Test;

import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import unitTests.services.BaseOfyTest;
import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;

/**
 *
 * @author flavio
 */
public class OfyRecommendationsRepositoryImplTest extends BaseOfyTest {

	@Test
	public void separar() {

		OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
		OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
		OfyRecommendationsRepository recommendationRepo = new OfyRecommendationsRepositoryImpl();

		OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
		OfyUser target = OfyUser.createFrom(1L, "name2", "link2", "mail2");

		owner = userRepo.add(owner);
		target = userRepo.add(target);

		OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());

		trip = tripRepo.add(trip);

		OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
			target,
			trip);

		recommendation = recommendationRepo.add(recommendation);

		// separar en varios test, es un ejemplo

		Assert.assertTrue(recommendationRepo.exists(recommendation.getId()));

		Assert.assertEquals(recommendation,
			recommendationRepo.findById(recommendation.getId()));

		Assert.assertEquals(1, recommendationRepo.findAll()
			.size());

		Assert.assertEquals(owner,
			recommendationRepo.findById(recommendation.getId())
				.getOwner());

		Assert.assertEquals(1,
			recommendationRepo.findByOwnerAndStatus(owner, Status.PENDING)
				.size());

		Assert.assertEquals(1,
			recommendationRepo.findByTargetAndStatus(target,
				Status.PENDING)
				.size());

		Assert.assertEquals(recommendationRepo.findByOwnerAndStatus(owner,
			Status.PENDING),
			recommendationRepo.findByTargetAndStatus(target,
				Status.PENDING));

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
