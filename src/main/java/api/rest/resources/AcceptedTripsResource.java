package api.rest.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyTripsService;
import utils.SessionUtils;

import static api.rest.resources.JsonResponseFactory.*;

@Path("/me/accepted-trips")
@RequestScoped
public class AcceptedTripsResource {

	@Context
	private HttpServletRequest request;

	@Inject
	private OfyTripsService tripsService;

	@Path("/{trip-id}")
	@GET
	public Response findAcceptedTripById(
		@PathParam("trip-id") String tripId) {

		return okJsonFrom(this.tripsService.findById(tripId));

	}

	@GET
	public Response findAcceptedTrips() {

		return okJsonFrom(this.tripsService.findAcceptedByTarget(this.getCurrentUserId()));

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}
}
