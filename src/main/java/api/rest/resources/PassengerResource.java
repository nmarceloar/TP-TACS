/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

package api.rest.resources;

import integracion.facebook.IdToken;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Passenger;
import apis.PassengerAPI;

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
	@Path("{userId}")
	@Produces("application/json")
	public Passenger getPasajeroPorId(@PathParam("userId") String id) {

		return this.pjSrv.getPassengerById(id);
	}

	@GET
	@Produces("application/json")
	public List<Passenger> getPasajeros() {

		return this.pjSrv.getListOfPassengers();
	}

	@GET
	@Path("/query")
	public String getTokenById(@NotNull @QueryParam("id") String userId) {

		Passenger pass = this.pjSrv.getPassengerById(userId);
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

		this.pjSrv.createPassenger(psj);

		return Response.status(201)
				.entity("Creado nuevo pasajero " + psj.getIdUser())
				.build();

	}

	@POST
	@Path("/idToken")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response nuevoPasajero(IdToken idToken) {

		Passenger pass = this.pjSrv.postPassengerByIdToken(idToken.getId(),
				idToken.gettoken());
		return Response.status(201)
				.entity("Logueado correctamente el pasajero " + pass.getIdUser())
				.build();
	}

}
