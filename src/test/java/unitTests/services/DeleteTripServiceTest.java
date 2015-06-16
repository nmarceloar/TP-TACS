/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.TripsAPI;
import config.SpringConfig;
import java.util.List;
import model.Trip;
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
public class DeleteTripServiceTest {
    
    @Autowired
    TripsAPI tripServ;
    
    @Test
    public void deleteTripTest() {
        List<Trip> viajes = tripServ.getTrips();
        Assert.assertEquals(5, viajes.size());
        Assert.assertEquals(1, viajes.get(0).getIdTrip());
        Assert.assertEquals(2, viajes.get(1).getIdTrip());
        Assert.assertEquals(3, viajes.get(2).getIdTrip());
        Assert.assertEquals(4, viajes.get(3).getIdTrip());
        tripServ.deleteTrip(1);
        viajes = tripServ.getTrips();
        Assert.assertEquals(3, viajes.size());
        Assert.assertEquals(2, viajes.get(0).getIdTrip());
        Assert.assertEquals(3, viajes.get(1).getIdTrip());
        Assert.assertEquals(4, viajes.get(2).getIdTrip());
    }
}
