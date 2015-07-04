package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import model2.Recommendation;
import model2.impl.OfyRecommendation;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.RecommendationsService;
import utils.SessionUtils;

@Path("/me/created-recommendations")
@RequestScoped
public class CreatedRecommendationsResource {

	@Inject
	private RecommendationsService recommendationService;

	@Context
	private HttpServletRequest request;

	@POST
	@Produces("application/json")
	public Recommendation
		createRecommendation(@FormParam("targetid") Long targetId,
			@FormParam("tripid") String tripId) {

		// ojo, aca ya estariamos suponiendo que son amigos !

		return this.recommendationService.createRecommendation(this.getCurrentUserId(),
			targetId,
			tripId);

	}

	@GET
	@Produces("application/json")
	public List<OfyRecommendation> findByOwner() {

		return this.recommendationService.findByOwner(this.getCurrentUserId());

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}
}
