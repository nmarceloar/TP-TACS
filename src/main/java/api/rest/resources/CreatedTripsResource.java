package api.rest.resources;

import static api.rest.resources.JsonResponseFactory.okJsonFrom;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyTripsService;
import utils.SessionUtils;
import api.rest.views.TripDetails;

@Path("/me/created-trips")
@RequestScoped
public class CreatedTripsResource {

	@Inject
	private OfyTripsService tripsService;

	@Context
	private HttpServletRequest request;

	@POST
	@Consumes("application/json")
	public Response createTripForUser(TripDetails tripDetails) {

		return okJsonFrom(this.tripsService.createTrip(SessionUtils.extractUserId(SessionUtils.existingFrom(request)),
			tripDetails));

	}

	@Path("/{trip-id}")
	@GET
	public Response findTripById(
		@NotNull @PathParam("trip-id") final String id) {

		return okJsonFrom(this.tripsService.findById(id));

	}

	@GET
	public Response findByOwner() {

		return okJsonFrom(this.tripsService.findByOwner(SessionUtils.extractUserId(SessionUtils.existingFrom(request))));

	}

	@DELETE
	@Path("{trip-id}")
	public Response deleteTripById(
		@NotNull @PathParam("trip-id") final String tripId) {

		tripsService.deleteById(tripId);
		return okJsonFrom("Se elimino el viaje con id: " + tripId);

	}

}
