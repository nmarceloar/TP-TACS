/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import api.rest.Airline;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import services.AirlinesService;
import services.impl.AirlinesServiceImpl;

/**
 *
 * @author flavio
 */
public class AirlinesServicesTest {

    private AirlinesService airSrv = new AirlinesServiceImpl();

    @Test
    public void findByCodeTest() {
        Airline linea1 = airSrv.findByCode("AR");
        Assert.assertEquals("AR", linea1.getCode());
        Assert.assertEquals("AR - (ARGENTINA) - Aerolineas Argentinas - Argentina",
                linea1.getName());
    }
    
    @Test
    public void findAllTest() {
        List<Airline> lista = airSrv.findAll();
        Assert.assertNotNull(lista);
        Assert.assertEquals(646, lista.size());
    }

}
