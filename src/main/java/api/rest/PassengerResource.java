/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import apis.PassengerAPI;
import integracion.despegar.Airport;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import integracion.facebook.*;
import services.Despegar;
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
        return pjSrv.getPassengerById(Long.parseLong(id));
    }
    
    @GET
    @Path("/query")
    public  String   getTokenById(
    		@NotNull @QueryParam("id") String userId)
    {    	
    	Passenger pass = pjSrv.getPassengerById(Long.parseLong( userId));
    	return pass.getToken();
    	    	
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
    
    @POST
    @Path("/idToken")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoPasajero(IdToken idToken) {
    	Passenger pass = pjSrv.postPassengerByIdToken(idToken.getId(),idToken.gettoken());
        return Response.status(201)
                .entity("Logueado correctamente el pasajero " + pass.getIdUser())
                .build();
    }

}
