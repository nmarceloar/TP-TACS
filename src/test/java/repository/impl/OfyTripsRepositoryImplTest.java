/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import api.rest.exceptions.DomainLogicException;
import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Assert;
import org.junit.Test;
import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import unitTests.services.BaseOfyTest;

/**
 *
 * @author flpitu88
 */
public class OfyTripsRepositoryImplTest extends BaseOfyTest {

    private OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
    OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
    OfyRecommendationsRepository recommendationRepo
            = new OfyRecommendationsRepositoryImpl();

    @Test
    public void addTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());

        Assert.assertTrue(!tripRepo.exists(trip.getId()));
        tripRepo.add(trip);
        Assert.assertTrue(tripRepo.exists(trip.getId()));
    }
    
    @Test
    public void addAllTest(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        tripRepo.add(trip);
        
        List<OfyTrip> listado = tripRepo.findAll();
        Assert.assertEquals(1,listado.size());
    }
    
    @Test
    public void findByIdTest(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        tripRepo.add(trip);
        
        OfyTrip viaje = tripRepo.findById(trip.getId());
        Assert.assertEquals(trip.getId(), viaje.getId());
        Assert.assertEquals(trip.getCreationDate(), viaje.getCreationDate());
        Assert.assertEquals(trip.getOwner(), viaje.getOwner());
        Assert.assertEquals(trip.getTripDetails(), viaje.getTripDetails());
        TripDetails detalles = viaje.getTripDetails();
        Assert.assertEquals("Buenos Aires", detalles.getFromCity().getName());
        Assert.assertEquals("Roma", detalles.getToCity().getName());
        Assert.assertEquals("ARS", detalles.getPriceDetail().getCurrency());
        Assert.assertEquals(5545.12, detalles.getPriceDetail().getTotal(),0);
    }
    
    @Test(expected = DomainLogicException.class)
    public void findByIdTestNotFound(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        tripRepo.add(trip);
        
        OfyTrip viaje = tripRepo.findById("NN");

    }
    
    @Test
    public void findByOwnerTest(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        tripRepo.add(trip);
        
        List<OfyTrip> listado = tripRepo.findByOwner(owner);
        Assert.assertEquals(1, listado.size());
        Assert.assertEquals(trip, listado.get(0));
    }
    
    @Test
    public void removeAllTest(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip1 = OfyTrip.createFrom(owner, buildTripDetails());
        OfyTrip trip2 = OfyTrip.createFrom(target, buildTripDetails());
        tripRepo.add(trip1);
        tripRepo.add(trip2);
        
        Assert.assertEquals(2, tripRepo.findAll().size());
        tripRepo.removeAll();
        Assert.assertEquals(0, tripRepo.findAll().size());
    }
    
    @Test
    public void deleteByIdTest(){
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip1 = OfyTrip.createFrom(owner, buildTripDetails());
        OfyTrip trip2 = OfyTrip.createFrom(target, buildTripDetails());
        tripRepo.add(trip1);
        tripRepo.add(trip2);
        
        Assert.assertEquals(2, tripRepo.findAll().size());
        tripRepo.deleteById(trip1.getId());
        Assert.assertEquals(1, tripRepo.findAll().size());
    }

    /**
     * Metodo para armar los detalles de un viaje
     *
     * @return
     */
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
