package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model2.Trip;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.TripsService;
import utils.SessionUtils;
import api.rest.views.TripDetails;
import model2.impl.OfyTrip;

@Path("/me/created-trips")
@RequestScoped
public class CreatedTripsResource {

	@Context
	private HttpServletRequest request;

	@Inject
	private TripsService tripsService;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Trip createTripForUser(TripDetails tripDetails) {

		return this.tripsService.createTrip(SessionUtils.extractUserId(SessionUtils.existingFrom(request)),
			tripDetails);

	}

	@Path("/{trip-id}")
	@GET
	@Produces("application/json")
	public Trip findTripById(@NotNull @PathParam("trip-id") final String id) {

		return this.tripsService.findById(id);

	}

	@GET
	@Produces("application/json")
	public List<OfyTrip> findByOwner() {

		return this.tripsService.findByOwner(SessionUtils.extractUserId(SessionUtils.existingFrom(request)));

	}

	@DELETE
	@Path("{trip-id}")
	@Produces("application/json")
	public Response deleteTripById(
		@NotNull @PathParam("trip-id") final String tripId) {

		try {

			tripsService.deleteById(tripId);

			return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity("Ok. Se elimino el viaje con id: " + tripId)
				.build();

		} catch (Exception ex) {

			return Response.serverError()
				.type(MediaType.APPLICATION_JSON)
				.entity("Error al intentar eliminar el viaje con id: " + tripId
					+ "\n"
					+ ex.getMessage())
				.build();
		}

	}
}
