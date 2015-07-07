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
import org.junit.Ignore;

/**
 *
 * @author flpitu88
 */
public class TripServiceTest extends BaseOfyTest {

    @Test
    public void createTripTest() {

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

        final OfyTrip trip = tripsService.createTrip(1L, buildTripDetails());

        Assert.assertNotNull(trip);
        Assert.assertEquals("Buenos Aires", trip.getTripDetails().getFromCity().getName());
        Assert.assertEquals("Roma", trip.getTripDetails().getToCity().getName());
    }

    @Test
    @Ignore
    // TERMINAR
    public void findAcceptedByTargetTest() {
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

        final OfyTrip trip = tripsService.createTrip(1L, buildTripDetails());
        tripRepo.add(trip);

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
        Assert.assertEquals(1, tripsService.findAll().size());

    }

    @Test
    public void findByOwnerTest() {
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
        
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        
        Assert.assertEquals(1,tripsService.findByOwner(1L).size());
        Assert.assertEquals(user1,tripsService.findByOwner(1L).get(0).getOwner());
    }
    
    @Test
    public void removeAllTest() {
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
        
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        
        Assert.assertEquals(1,tripsService.findAll().size());
        tripsService.removeAll();
        Assert.assertEquals(0,tripsService.findAll().size());
    }
    
    @Test
    public void findByIdTest() {
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
        
        final OfyTrip trip = tripsService.createTrip(1L,
                buildTripDetails());
        
        OfyTrip viaje = tripsService.findById(trip.getId());
        Assert.assertNotNull(viaje);
        TripDetails detalles = viaje.getTripDetails();
        Assert.assertEquals("ARS", detalles.getPriceDetail().getCurrency());
        Assert.assertEquals(5545.12, detalles.getPriceDetail().getTotal(), 0);
    }
    
    @Test
    public void deleteByIdTest() {
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
        final OfyUser user2 = OfyUser.createFrom(2L,
                "user2",
                "fbuser2",
                "user2@facebook.com");

        userRepo.add(user1);
        userRepo.add(user2);
        
        final OfyTrip trip1 = tripsService.createTrip(1L,
                buildTripDetails());
        final OfyTrip trip2 = tripsService.createTrip(2L,
                buildTripDetails());
                
        Assert.assertEquals(2,tripsService.findAll().size());
        Assert.assertEquals(trip2.getId(), tripsService.findAll().get(0).getId());
        Assert.assertEquals(trip1.getId(), tripsService.findAll().get(1).getId());
        tripsService.deleteById(trip1.getId());
        Assert.assertEquals(1,tripsService.findAll().size());
        Assert.assertEquals(trip2.getId(), tripsService.findAll().get(0).getId());
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
