package api.rest.resources;

import static api.rest.resources.JsonResponseFactory.okJsonFrom;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import services.OfyTripsService;
import utils.SessionUtils;

@Path("/me/accepted-trips")
@Singleton
public class AcceptedTripsResource {

	private HttpServletRequest request;
	private OfyTripsService tripsService;

	@Inject
	public AcceptedTripsResource(@Context HttpServletRequest request,
		OfyTripsService tripsService) {

		this.request = request;
		this.tripsService = tripsService;

	}

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
