/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.TripsAPI;
import config.SpringConfig;
import java.util.Arrays;
import model.Segment;
import model.Trip;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author flpitu88
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SaveTripServiceTest {

    @Autowired
    TripsAPI tripServ;

    @Test
    public void saveTripTest() {
        Assert.assertEquals(4, tripServ.getTrips().size());
        Segment seg1 = new Segment("Paris", "Berlin",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "AER", "AE-01");
        Trip viaje1 = new Trip("10153253398579452", "Paris", "Berlin",
                "13154 ARS", Arrays.asList(seg1));
        tripServ.saveTrip(viaje1);
        Assert.assertEquals(5, tripServ.getTrips().size());
    }

}
