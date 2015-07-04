package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import model2.Trip;
import model2.impl.OfyTrip;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.TripsService;

@Path("/me/friends-trips")
@RequestScoped
public class FriendsTrips {

	@Inject
	private TripsService tripsService;

	@GET
	@Produces("application/json")
	public
		List<OfyTrip>
		findTripsByOwner(
			@NotNull(message = "Debe especificar el id del amigo del cual desea ver los viajes") @QueryParam("friend-id") final Long friendId) {

		// ojo, aca habria que verificar si realmente son amigos
		// estamos asumiendo que si porque lo mandamos de nuestro front
		// pero si probamos de curl o cualquier otro cliente, cualquier
		// parametro es valido

		return this.tripsService.findByOwner(friendId);

	}

}
