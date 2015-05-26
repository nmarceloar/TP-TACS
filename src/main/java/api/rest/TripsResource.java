/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import apis.TripsAPI;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Trip;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsResource {

    private final TripsAPI vjSrv;

    @Inject
    public TripsResource(TripsAPI vjService) {
        this.vjSrv = vjService;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public List<Trip> getViajesPorPasajero(@PathParam("id") String id) {
        return vjSrv.getTripsOfPassenger(Integer.parseInt(id));
    }
    
    @GET
    @Path("one/{idTrip}")
    @Produces("application/json")
    public Trip getTripById(@NotNull @PathParam("idTrip") String id){
        return vjSrv.getTrip(Integer.parseInt(id));
    }
    
    @GET
    @Path("friends/{id}")
    @Produces("application/json")
    public List<Trip> getTripsOfFriends(@PathParam("id") String id) {
        return vjSrv.getTripsOfFriendsOfUser(Integer.parseInt(id));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarViaje(Trip v) {
        vjSrv.saveTrip(v);
        String result = "Guardado viaje con id "+v.getIdTrip()+" desde " + v.getItinerary().get(0).getFrom()
                + " - hasta "
                + v.getItinerary().get(v.getItinerary().size() - 1)
                .getFrom();
        	

        return Response.status(201)
                .entity(result).build();
    }

}
