/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import api.rest.views.Airline;
import api.rest.views.Airport;
import java.util.Arrays;
import java.util.List;

import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;

import org.junit.Assert;
import org.junit.Test;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import services.OfyRecommendationsService;
import services.impl.OfyRecommendationsServiceImpl;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import java.util.Date;
import model2.Recommendation;
import repository.impl.OfyRecommendationsRepositoryImpl;
import repository.impl.OfyTripsRepositoryImpl;
import repository.impl.OfyUsersRepositoryImpl;
import services.OfyTripsService;
import services.impl.OfyTripsServiceImpl;

/**
 *
 * @author flpitu88
 */
public class RecommendationsServiceTest extends BaseOfyTest {

    @Test
    public void createRecommendationTest() {

        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());

        Assert.assertEquals(0, recService.findAll().size());
        recService.createRecommendation(user1.getId(), user2.getId(), trip.getId());
        Assert.assertEquals(1, recService.findAll().size());
    }

    @Test
    public void findByIdTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        OfyRecommendation recom = recService.createRecommendation(user1.getId(),
                user2.getId(), trip.getId());
        recommendationRepo.add(recom);

        OfyRecommendation rec = recService.findById(recom.getId());
        Assert.assertEquals(rec.getId(), recom.getId());
        Assert.assertEquals(user1.getId(), recom.getOwner().getId());
        Assert.assertEquals(user2.getId(), recom.getTarget().getId());
    }

    @Test
    public void removeAllTest() {
       final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        OfyRecommendation recom = recService.createRecommendation(user1.getId(),
                user2.getId(), trip.getId());
        recommendationRepo.add(recom);

        Assert.assertEquals(1, recService.findAll().size());
        recService.removeAll();
        Assert.assertEquals(0, recService.findAll().size());
    }

    @Test
    public void findByOwnerAndStatusTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        OfyRecommendation recom = recService.createRecommendation(user1.getId(),
                user2.getId(), trip.getId());
        recommendationRepo.add(recom);

        List<OfyRecommendation> lista = recService.findByOwnerAndStatus(
                user1.getId(), Recommendation.Status.PENDING);
        Assert.assertEquals(1, lista.size());
        List<OfyRecommendation> lista2 = recService.findByOwnerAndStatus(
                user1.getId(), Recommendation.Status.ACCEPTED);
        Assert.assertEquals(0, lista2.size());
    }

    @Test
    public void findByTargetAndStatusTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        OfyRecommendation recom = recService.createRecommendation(user1.getId(),
                user2.getId(), trip.getId());
        recommendationRepo.add(recom);

        List<OfyRecommendation> lista = recService.findByTargetAndStatus(
                user2.getId(), Recommendation.Status.PENDING);
        Assert.assertEquals(1, lista.size());
        List<OfyRecommendation> lista2 = recService.findByTargetAndStatus(
                user2.getId(), Recommendation.Status.ACCEPTED);
        Assert.assertEquals(0, lista2.size());
        List<OfyRecommendation> lista3 = recService.findByTargetAndStatus(
                user1.getId(), Recommendation.Status.PENDING);
        Assert.assertEquals(0, lista3.size());
    }

    @Test
    public void pathRecommendationTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        final OfyRecommendationsRepositoryImpl recommendationRepo = new OfyRecommendationsRepositoryImpl();
        final OfyTripsService tripsService
                = new OfyTripsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);
        final OfyRecommendationsService recService
                = new OfyRecommendationsServiceImpl(userRepo,
                        tripRepo,
                        recommendationRepo);

        final OfyUser user1 = OfyUser.createFrom(1L,
                "user1",
                "fbuser1",
                "user1@facebook.com");
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        final OfyTrip trip2 = tripsService.createTrip(2L,
                buildTripDetails());
        OfyRecommendation recom = recService.createRecommendation(user1.getId(),
                user2.getId(), trip.getId());
        OfyRecommendation recom2 = recService.createRecommendation(user2.getId(),
                user1.getId(), trip2.getId());
        recommendationRepo.add(recom);
        recommendationRepo.add(recom2);

        Assert.assertEquals(Recommendation.Status.PENDING.name(),
                recom.getStatus().name());
        recService.patchRecommendation(user2.getId(), recom.getId(),
                Recommendation.Status.ACCEPTED);
        Assert.assertEquals(Recommendation.Status.ACCEPTED.name(),
                recom.getStatus().name());
        recService.patchRecommendation(user1.getId(), recom2.getId(),
                Recommendation.Status.REJECTED);
        Assert.assertEquals(Recommendation.Status.REJECTED.name(),
                recom2.getStatus().name());
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
