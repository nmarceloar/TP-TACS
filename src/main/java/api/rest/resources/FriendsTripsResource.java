package api.rest.resources;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyTripsService;

import static api.rest.resources.JsonResponseFactory.*;

@Path("/me/friends-trips")
@RequestScoped
public class FriendsTripsResource {

	@Inject
	private OfyTripsService tripsService;

	@GET
	public Response findTripsByOwner(
		@NotNull @QueryParam("friend-id") final Long friendId) {

		// ojo, aca habria que verificar si realmente son amigos
		// estamos asumiendo que si porque lo mandamos de nuestro front
		// pero si probamos de curl o cualquier otro cliente, cualquier
		// parametro es valido

		return okJsonFrom(this.tripsService.findByOwner(friendId));

	}

}
