package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model2.Trip;
import model2.User;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.RecommendationsService;
import services.TripsService;
import services.UsersService;

@Path("/testing")
@RequestScoped
public class TestingResource {

	@Inject
	private UsersService users;

	@Inject
	private TripsService trips;

	@Inject
	private RecommendationsService recommendations;

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
