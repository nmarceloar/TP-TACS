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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import model.Passenger;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Path("/passengers")
@Produces(MediaType.APPLICATION_JSON)
public class PassengerResource {

    private final PassengerAPI pjSrv;

    @Inject
    public PassengerResource(PassengerAPI passgService) {
        this.pjSrv = passgService;
    }

    @GET
    @Produces("application/json")
    public List<Passenger> getPasajeros() {
        return pjSrv.getListOfPassengers();
    }
    
    @GET
    @Path("{userId}")
    @Produces("application/json")
    public Passenger getPasajeroPorId(@PathParam("userId") String id){
        return pjSrv.getPassengerById(Integer.parseInt(id));
    }

    /**
     * Probar mediante un test si esta bien este metodo. De alguna manera tiene
     * que tomar los datos del usuario a crear.
     *
     * @param psj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarPasajero(Passenger psj) {
        pjSrv.createPassenger(psj);
        return Response.status(201)
                .entity("Creado nuevo pasajero " + psj.getIdUser())
                .build();
    }

}
