/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsPasajeros;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Passenger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author flavio
 */
public class PassengerResourceTests {
 
    @Test
    public void testGetListaDePasajeros() {
        ClientConfig config = new ClientConfig().register(new JacksonFeature());
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("http://localhost:8080/api/passengers");
        final Invocation.Builder invocationBuilder = target.request()
                .accept(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
//        Assert.assertTrue(response != null);
        List<Passenger> lista = response.readEntity(new GenericType<List<Passenger>>(){});
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals(200,response.getStatusInfo().getStatusCode());
    }

    @Test
    public void testGetPasajero() {
        ClientConfig config = new ClientConfig().register(new JacksonFeature());
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("http://localhost:8080/api/passengers/10153253398579452");       
        final Invocation.Builder invocationBuilder = target.request()
                .accept(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Passenger pass = response.readEntity(new GenericType<Passenger>() {});
        Assert.assertEquals("10153253398579452", pass.getIdUser());
        Assert.assertEquals(200, response.getStatusInfo().getStatusCode());
    }
}
