/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.PassengerAPI;
import config.SpringConfig;
import java.util.List;
import model.Passenger;
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
public class PassengerServiceTest {

    @Autowired
    PassengerAPI psjServ;

    @Test
    public void getAllPassengersTest() {
        List<Passenger> lista = psjServ.getListOfPassengers();
        Assert.assertNotNull(lista);
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals("10206028316763565", lista.get(0).getIdUser());
        Assert.assertEquals("10153253398579452", lista.get(1).getIdUser());
    }

    @Test
    public void getPasajeroByIdTest() {
        String id = "10153253398579452";
        Passenger psj = psjServ.getPassengerById(id);
        Assert.assertEquals(id, psj.getIdUser());
    }

    @Test
    public void savePassengerTest() {
        Assert.assertEquals(2, psjServ.getListOfPassengers().size());
        Passenger p = new Passenger();
        p.setIdUser("111111");
        p.setName("NombrePrueba");
        p.setSurname("ApellidoPrueba");
        psjServ.createPassenger(p);
        Assert.assertEquals(3, psjServ.getListOfPassengers().size());
    }

}
