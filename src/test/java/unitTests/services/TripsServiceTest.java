/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.TripsAPI;
import config.SpringConfig;
import java.util.Arrays;
import java.util.List;
import model.Segment;
import model.Trip;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
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
public class TripsServiceTest {
    
    @Autowired
    TripsAPI tripServ;
    
    @Test
    public void getTripsOfPassengerTest() {
        List<Trip> lista = tripServ.getTripsOfPassenger("10153253398579452");
        Assert.assertNotNull(lista);
        Assert.assertEquals(2, lista.size());
    }

    @Test
    public void getTripsTest() {
        List<Trip> lista = tripServ.getTrips();
        Assert.assertEquals(1, lista.get(0).getIdTrip());
        Assert.assertEquals(2, lista.get(1).getIdTrip());
        Assert.assertEquals(3, lista.get(2).getIdTrip());
        Assert.assertEquals(4, lista.get(3).getIdTrip());
    }

    @Test
    public void getTripTest() {
        Assert.assertEquals(1, tripServ.getTrip(1).getIdTrip());
    }

    @Test
    public void getTripsOfFriendsOfUserTest() {
        List<Trip> viajes = tripServ.getTripsOfPassenger("10153253398579452");
        Assert.assertEquals(2, viajes.size());
        Assert.assertEquals(1, viajes.get(0).getIdTrip());
        Assert.assertEquals(2, viajes.get(1).getIdTrip());
    }
    
}
