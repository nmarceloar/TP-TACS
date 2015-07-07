/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.resources;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import model2.Trip;
import model2.impl.OfyTrip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import services.OfyTripsService;
import api.rest.resources.FriendsTripsResource;
import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import java.util.Arrays;
import java.util.Date;
import javax.ws.rs.core.Response;
import model2.impl.OfyUser;
import org.junit.Assert;
import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import repository.impl.OfyRecommendationsRepositoryImpl;
import repository.impl.OfyTripsRepositoryImpl;
import repository.impl.OfyUsersRepositoryImpl;
import unitTests.services.BaseOfyTest;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class FriendsTripsResourceTest extends BaseOfyTest {

    @Mock
    private OfyTripsService tripsService;

    @InjectMocks
    private FriendsTripsResource frdRes = new FriendsTripsResource();
    
    private OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
    OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
    OfyRecommendationsRepository recommendationRepo
            = new OfyRecommendationsRepositoryImpl();

    @Before
    public void prepare() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        tripRepo.add(trip);
        
        when(tripsService.findByOwner(1L)).thenReturn(tripRepo.findAll());
    }

    @Test
    public void findTripsByOwnerTest() {
        Response respuesta = frdRes.findTripsByOwner(1L);
        Assert.assertNotNull(respuesta);
        Assert.assertEquals(200, respuesta.getStatus());
        Assert.assertEquals(tripRepo.findAll(), respuesta.getEntity());
    }

    /**
     * Meotodo para crear un detalle de viajes utilizado en los tests
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
