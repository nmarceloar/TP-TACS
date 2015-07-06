package api.rest.resources;

import static api.rest.resources.JsonResponseFactory.okJsonFrom;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyRecommendationsService;
import utils.SessionUtils;

@Path("/me/created-recommendations")
@RequestScoped
public class CreatedRecommendationsResource {

	@Inject
	private OfyRecommendationsService recommendationService;

	@Context
	private HttpServletRequest request;

	@POST
	public Response createRecommendation(
		@FormParam("targetid") Long targetId,
		@FormParam("tripid") String tripId) {

		// ojo, aca ya estariamos suponiendo que son amigos !

		return okJsonFrom(this.recommendationService.createRecommendation(this.getCurrentUserId(),
			targetId,
			tripId));

	}

	@GET
	public Response findByOwner() {

		return okJsonFrom(this.recommendationService.findByOwner(this.getCurrentUserId()));

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}
}
