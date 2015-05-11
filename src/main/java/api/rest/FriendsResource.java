/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;


import apis.PassengerAPI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Passenger;


/**
 *
 * @author flavio
 */
@Path("/friends")
@Produces(MediaType.APPLICATION_JSON)
public class FriendsResource {
    
    private final PassengerAPI pjSrv;

    @Inject
    public FriendsResource(PassengerAPI passgService) {
        this.pjSrv = passgService;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public List<Passenger> getAmigosDeUsuario(@PathParam("id") String id) {
        return pjSrv.getFriendsOfPassenger(Integer.parseInt(id));
    }
    
}
