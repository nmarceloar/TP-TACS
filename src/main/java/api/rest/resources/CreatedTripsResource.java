package api.rest.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	@Produces("application/json")
	public OfyTrip createTripForUser(TripDetails tripDetails) {

		return this.tripsService.createTrip(
				SessionUtils.extractUserId(SessionUtils.existingFrom(request)),
				tripDetails);

	}

	@Path("/{trip-id}")
	@GET
	@Produces("application/json")
	public OfyTrip findTripById(@NotNull @PathParam("trip-id") final String id) {

		return this.tripsService.findById(id);

	}

	@GET
	@Produces("application/json")
	public List<OfyTrip> findByOwner() {

		return this.tripsService.findByOwner(SessionUtils.extractUserId(SessionUtils.existingFrom(request)));

	}
}
