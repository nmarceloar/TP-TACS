package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model2.Recommendation;
import model2.Trip;
import model2.User;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyRecommendationsService;
import services.OfyTripsService;
import services.OfyUsersService;

import static api.rest.resources.JsonResponseFactory.*;

@Path("/testing")
@RequestScoped
public class TestingResource {

	@Inject
	private OfyUsersService users;

	@Inject
	private OfyTripsService trips;

	@Inject
	private OfyRecommendationsService recommendations;

	@GET
	@Path("/recommendations")
	public Response findAllRecommendations() {

		return okJsonFrom(this.recommendations.findAll());

	}

	@GET
	@Path("/trips")
	public Response findAllTrips() {

		return okJsonFrom(this.trips.findAll());

	}

	@GET
	@Path("/users")
	public Response findAllUsers() {

		return okJsonFrom(this.users.findAll());

	}

	@GET
	@Path("/recommendations/removeall")
	public void removeAllRecommendations() {

		this.recommendations.removeAll();

	}

	@GET
	@Path("/trips/removeall")
	public void removeAllTrips() {

		this.trips.removeAll();

	}

	@GET
	@Path("/users/removeall")
	public void removeAllUsers() {

		this.users.removeAll();

	}
}
