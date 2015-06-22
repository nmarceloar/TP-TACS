package api.rest.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyTrip;
import services.OfyTripService;
import services.OfyTripServiceImpl;
import services.TripDetails;
import api.rest.SessionUtils;

@Path("/me/created-trips")
@RequestScoped
public class CreatedTripsResource {

	private OfyTripService tripsService = OfyTripServiceImpl.getInstance();

	@Context
	private HttpServletRequest request;

	@POST
	@Consumes("application/json")
	@Produces("application/Json")
	public OfyTrip createTripForUser(TripDetails tripDetails) {

		if (request == null) {

			throw new RuntimeException("request = Null");

		}

		Long id = SessionUtils.extractUserId(this.request.getSession(false));

		if (id == null || id.equals(Long.valueOf(0L))) {

			throw new RuntimeException("id = Null OR cero");

		}

		return this.tripsService.createTrip(id, tripDetails);

	}

	@GET
	@Produces("application/json")
	public List<OfyTrip> findByOwner() {

		if (request == null) {

			throw new RuntimeException("request = Null");

		}

		Long id = SessionUtils.extractUserId(this.request.getSession(false));

		if (id == null || id.equals(Long.valueOf(0L))) {

			throw new RuntimeException("id = Null OR cero");

		}

		return this.tripsService.findByOwner(id);

	}

}
