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

import services.TripsService;
import utils.SessionUtils;

@Path("/me/accepted-trips")
@RequestScoped
public class AcceptedTripsResource {

	@Context
	private HttpServletRequest request;

	@Inject
	private TripsService tripsService;

	@Path("/{trip-id}")
	@GET
	public Response findAcceptedTripById(@PathParam("trip-id") String tripId) {

		try {

			return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity(this.tripsService.findById(tripId))
				.build();

		} catch (Exception ex) {

			return Response.serverError()
				.type(MediaType.APPLICATION_JSON)
				.entity(ex.getMessage())
				.build();

		}

	}

	@GET
	public Response findAcceptedTrips() {

		try {

			return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity(this.tripsService.findAcceptedByTarget(this.getCurrentUserId()))
				.build();

		} catch (Exception ex) {

			return Response.serverError()
				.type(MediaType.APPLICATION_JSON)
				.entity(ex.getMessage())
				.build();
		}

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}
}
