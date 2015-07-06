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
import model2.User;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Assert;
import org.junit.Test;
import repository.OfyRecommendationsRepository;
import unitTests.services.BaseOfyTest;

/**
 *
 * @author flavio
 */
public class OfyRecommendationsRepositoryImplTest extends BaseOfyTest {

    @Test
    public void testAdd() {

        OfyRecommendationsRepository repo = new OfyRecommendationsRepositoryImpl();

        User ownerUser = OfyUser.createFrom(1L, "user1", "fblink1", "mail1");
        User targetUser = OfyUser.createFrom(2L, "user2", "fblink2", "mail2");
        PriceDetail precio = new PriceDetail("ARS", 150);
        City fromCity = new City("BUE", "Buenos Aires", 100, 100);
        City toCity = new City("ROM", "Roma", 200, 200);
        Airport airFrom = new Airport("BUE", "Ezeiza", 100, 100);
        Airport airTo = new Airport("Rom", "Fumiccino", 200, 200);
        Airline airLine = new Airline("AA", "Aerolineas Argentinas");
        Segment seg1 = new Segment(airFrom, airTo, airLine, "AA01",
                new Date(), new Date(), "10");
        Segment seg2 = new Segment(airTo, airFrom, airLine, "AA02",
                new Date(), new Date(), "10");
        TripDetails detalles = new TripDetails(fromCity, toCity, precio,
                Arrays.asList(seg1, seg2), Arrays.asList(seg2, seg1));
        OfyTrip viaje = OfyTrip.createFrom( (OfyUser) ownerUser, detalles);

        OfyRecommendation rec = new OfyRecommendation((OfyUser) ownerUser, (OfyUser) targetUser, viaje);

        Assert.assertTrue(!repo.exists(rec.getId()));
        repo.add(rec);
        Assert.assertTrue(repo.exists(rec.getId()));
    }

}
