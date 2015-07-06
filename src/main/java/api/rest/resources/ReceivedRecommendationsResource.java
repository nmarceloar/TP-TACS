package api.rest.resources;

import static api.rest.resources.JsonResponseFactory.okJsonFrom;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import model2.Recommendation;
import model2.impl.OfyRecommendation;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyRecommendationsService;
import utils.SessionUtils;
import api.rest.PATCH;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("/me/received-recommendations")
@RequestScoped
public class ReceivedRecommendationsResource {

	@Context
	private HttpServletRequest request;

	@Inject
	private OfyRecommendationsService recommendationService;

	private boolean checkPatchSpec(
		final RecommendationPatchJsonSpec patchSpec) {

		// esto es cualquiera (anda, pero hay que pasar a RFC6902)

		return patchSpec.getOp().equals("replace") && patchSpec.getPath()
			.equals("/status");
	}

	@GET
	@Path("/{recommendation-id}")
	public
		Response
		findRecommendationById(
			@NotNull @PathParam("recommendation-id") final String recommendationId) {

		return okJsonFrom(this.recommendationService.findById(recommendationId));

	}

	@GET
	public
		Response
		findRecommendationsByTargetAndStatus(
			@NotNull @QueryParam("status") @DefaultValue(value = "PENDING") final Recommendation.Status status) {

		return okJsonFrom(this.recommendationService.findByTargetAndStatus(this.getCurrentUserId(),
			status));

	}

	private HttpSession getCurrentSession() {

		return this.request.getSession(false);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}

	@PATCH
	@Path("/{recommendation-id}")
	@Consumes("application/json")
	public
		Response
		patchRecommendation(
			@NotNull @PathParam("recommendation-id") final String recommendationId,
			@NotNull final RecommendationPatchJsonSpec patchSpec) {

		// esto es una implementacion cualquiera(anda, pero es cualquiera).
		// hay que implementar RFC 6902.
		// esto está para cumplir con PATCH en rest

		// https://tools.ietf.org/html/rfc6902
		// https://www.mnot.net/blog/2012/09/05/patch
		// http://williamdurand.fr/2014/02/14/please-do-not-patch-like-an-idiot/

		// REPITO - Es cualquier cosa -- Lo termino en cuanto pueda -->
		// { "op": "replace", "path": "/status", "value": ACCEPTED|REJECTED }

		if (!this.checkPatchSpec(patchSpec)) {

			throw new BadRequestException("Solo se permite actualizar el estado de una recomendacion");

		}

		return okJsonFrom(this.recommendationService.patchRecommendation(this.getCurrentUserId(),
			recommendationId,
			patchSpec.getValue()));

	}

}

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class RecommendationPatchJsonSpec {

	@JsonProperty
	private final String op;

	@JsonProperty
	private final String path;

	@JsonProperty
	private final OfyRecommendation.Status value;

	public RecommendationPatchJsonSpec(
		@JsonProperty("op") final String op,
		@JsonProperty("path") final String path,
		@JsonProperty("value") final OfyRecommendation.Status value) {

		this.op = op;
		this.path = path;
		this.value = value;

	}

	public String getOp() {

		return this.op;
	}

	public String getPath() {

		return this.path;
	}

	public OfyRecommendation.Status getValue() {

		return this.value;

	}

}
