/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

package api.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Trip;

import org.json.JSONObject;

import services.TripsAPI;

/**
 *
 * @author Flavio L. Pietrolati
 */
// @Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsResource {

	private final TripsAPI vjSrv;

	@Inject
	public TripsResource(TripsAPI vjService) {

		this.vjSrv = vjService;
	}

	@DELETE
	@Path("{idTrip}")
	@Produces("application/json")
	public String deleteTripById(@NotNull @PathParam("idTrip") String id) {

		String result = this.vjSrv.deleteTrip(Integer.parseInt(id));
		return "Se ha eliminado el viaje con id " + result;
	}

	@GET
	@Path("one/{idTrip}")
	@Produces("application/json")
	public Trip getTripById(@NotNull @PathParam("idTrip") String id) {

		return this.vjSrv.getTrip(Integer.parseInt(id));
	}

	@GET
	@Produces("application/json")
	public List<Trip> getTrips() {

		return this.vjSrv.getTrips();
	}

	@GET
	@Path("friends/{id}")
	@Produces("application/json")
	public List<Trip> getTripsOfFriends(@PathParam("id") String id) {

		// Salvo la posibilidad de que de null los viajes de amigos
		if (this.vjSrv.getTripsOfFriendsOfUser(id) == null) {
			return new ArrayList<>();
		}
		return this.vjSrv.getTripsOfFriendsOfUser(id);
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public List<Trip> getViajesPorPasajero(@PathParam("id") String id) {

		// Salvo condicion de lista vacia
		if (this.vjSrv.getTripsOfPassenger(id) == null) {
			return new ArrayList<>();
		}
		return this.vjSrv.getTripsOfPassenger(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarViaje(Trip v) {

		this.vjSrv.saveTrip(v);
		String result = "Guardado viaje con id " + v.getIdTrip()
			+ " desde "
			+ v.getFromCity()
			+ " - hasta "
			+ v.getToCity();
		JSONObject obj = new JSONObject("{\"id\":\"" + v.getIdTrip()
			+ "\" , \"result\":\""
			+ result
			+ "\"}");

		return Response.status(201)
			.entity(obj.toString())
			.build();
	}

}
