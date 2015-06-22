package api.rest.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyRecommendation;
import services.OfyRecommendationsService;
import api.rest.PATCH;
import api.rest.SessionUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("/me/received-recommendations")
@RequestScoped
public class ReceivedRecommendationsResource {

	@Context
	private HttpServletRequest request;

	private OfyRecommendationsService recommendationService = OfyRecommendationsService.getInstance();

	private boolean checkPatchSpec(final RecommendationPatchJsonSpec patchSpec) {

		// esto es cualquiera (anda, pero hay que pasar a RFC6902)

		return patchSpec.getOp()
				.equals("replace") && patchSpec.getPath()
				.equals("/status");
	}

	@GET
	@Path("/{recid}")
	@Produces("application/json")
	public OfyRecommendation findRecommendationById(
			@NotNull @PathParam("recid") final String recommendationId) {

		return this.recommendationService.findById(recommendationId);

	}

	@GET
	@Produces("application/json")
	public List<OfyRecommendation> findRecommendationsByTargetAndStatus(
			@NotNull @QueryParam("status") @DefaultValue(value = "PENDING") final OfyRecommendation.Status status) {

		return this.recommendationService.findByTargetAndStatus(
				this.getCurrentUserId(), status);

	}

	private String getCurrentFacebookToken() {

		return SessionUtils.extractToken(this.getCurrentSession());

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
	@Produces("application/json")
	public OfyRecommendation patchRecommendation(
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

			throw new BadRequestException(
					"Solo se permite actualizar el estado de una recomendacion");

		}

		return this.recommendationService.patchRecommendation(
				this.getCurrentUserId(), recommendationId, patchSpec.getValue());

	}

}

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class RecommendationPatchJsonSpec {

	@JsonProperty
	private String op;

	@JsonProperty
	private String path;

	@JsonProperty
	private OfyRecommendation.Status value;

	public RecommendationPatchJsonSpec(@JsonProperty("op") String op,
			@JsonProperty("path") String path,
			@JsonProperty("value") OfyRecommendation.Status value) {

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
