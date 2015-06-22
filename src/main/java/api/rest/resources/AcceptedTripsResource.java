package api.rest.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyTripService;
import services.OfyTripServiceImpl;
import api.rest.AcceptedOfyTrip;
import api.rest.SessionUtils;

@Path("/me/accepted-trips")
@RequestScoped
public class AcceptedTripsResource {

	@Context
	private HttpServletRequest request;

	private OfyTripService tripsService = OfyTripServiceImpl.getInstance();

	@GET
	public Response findAcceptedTrips() {

		List<AcceptedOfyTrip> acceptedTrips;

		if (request == null) {

			return Response.serverError()
				.type(MediaType.APPLICATION_JSON)
				.entity("request nulL")
				.build();

		}

		try {

			acceptedTrips = this.tripsService.findAcceptedByTarget(this.getCurrentUserId());

		} catch (Exception ex) {

			return Response.serverError()
				.type(MediaType.APPLICATION_JSON)
				.entity(ex.getMessage())
				.build();

		}

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity(acceptedTrips)
			.build();

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}
}
