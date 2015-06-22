package api.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyRecommendation;
import services.OfyRecommendationsService;
import services.OfyTrip;
import services.OfyTripService;
import services.OfyTripServiceImpl;
import services.OfyUser;
import services.OfyUserService;

@Path("/testing")
@RequestScoped
public class TestingResource {

	private OfyUserService users = OfyUserService.getInstance();
	private OfyTripService trips = OfyTripServiceImpl.getInstance();
	private OfyRecommendationsService recommendations = OfyRecommendationsService.getInstance();

	@GET
	@Path("/recommendations")
	@Produces("application/json")
	public List<OfyRecommendation> findAllRecommendations() {

		return this.recommendations.findAll();

	}

	@GET
	@Path("/trips")
	@Produces("application/json")
	public List<OfyTrip> findAllTrips() {

		return this.trips.findAll();

	}

	@GET
	@Path("/users")
	@Produces("application/json")
	public List<OfyUser> findAllUsers() {

		return this.users.findAll();

	}

	@GET
	@Path("/recommendations/removeall")
	public Response removeAllRecommendations() {

		this.recommendations.removeAll();

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity("Ok")
			.build();

	}

	@GET
	@Path("/trips/removeall")
	public Response removeAllTrips() {

		this.trips.removeAll();

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity("Ok")
			.build();

	}

	@GET
	@Path("/users/removeall")
	public Response removeAllUsers() {

		this.users.removeAll();

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity("Ok")
			.build();

	}
}
