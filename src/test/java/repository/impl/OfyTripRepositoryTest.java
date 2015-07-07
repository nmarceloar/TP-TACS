/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Test;
import repository.OfyTripsRepository;
import unitTests.services.BaseOfyTest;

/**
 *
 * @author flpitu88
 */
public class OfyTripRepositoryTest extends BaseOfyTest {

    @Test
    public void addTest() {
        OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        System.out.println("Id del trip: " + trip.getId());
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
