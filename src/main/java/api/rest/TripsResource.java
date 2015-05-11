/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import apis.TripsAPI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Viaje;

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
    public List<Viaje> getViajesPorPasajero(@PathParam("id") String id) {
        return vjSrv.getViajesDePasajero(Integer.parseInt(id));
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarViaje(Viaje v) {
        vjSrv.guardarViaje(v);
        String result = "Guardado: " + v.getIdViaje()
                + " " + v.getItinerario().get(0).getOrigen().getCiudad()
                + " - "
                + v.getItinerario().get(v.getItinerario().size() - 1)
                .getDestino().getCiudad();

        return Response.status(201)
                .entity(result).build();
    }

}
