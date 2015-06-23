/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

package api.rest.resources;

import java.util.ArrayList;
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

import model.Passenger;
import apis.PassengerAPI;

/**
 *
 * @author flavio
 */
//@Path("/friends")
@Produces(MediaType.APPLICATION_JSON)
public class FriendsResource {

	private final PassengerAPI pjSrv;

	@Inject
	public FriendsResource(PassengerAPI passgService) {

		this.pjSrv = passgService;
	}

	@POST
	@Path("{idUser}/{idFriend}")
	@Consumes("application/json")
	public Response assignFriend(@PathParam("idUser") String idUser,
			@PathParam("idFriend") String idFriend) {

		this.pjSrv.assignFriend(idUser, idFriend);
		this.pjSrv.assignFriend(idFriend, idUser);
		String result = "El usuario " + idUser + " es amigo del usuario "
				+ idFriend;
		return Response.status(201)
				.entity(result)
				.build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Passenger> getAmigosDeUsuario(@PathParam("id") String id) {

		// Chequeo que si no existe ese usuario, devuelva lista vacia
		if (this.pjSrv.getFriendsOfPassenger(id) == null) {
			return new ArrayList<>();
		}
		return this.pjSrv.getFriendsOfPassenger(id);
	}

	@GET
	@Path("{idUser}/{idFriend}")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean getSonAmigos(@PathParam("idUser") String id,
			@PathParam("idFriend") String idFriend) {

		// Chequeo que si no existe ese usuario, devuelva lista vacia
		if (this.pjSrv.getFriendsOfPassenger(id) == null) {
			return false;
		}
		if (this.pjSrv.getFriendsOfPassenger(id)
				.contains(idFriend)) {
			return true;
		}
		return false;
	}

}
