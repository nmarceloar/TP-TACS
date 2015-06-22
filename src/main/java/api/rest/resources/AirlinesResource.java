package api.rest.resources;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.AirlinesService;
import api.rest.Airline;

@Path("/airlines")
@RequestScoped
public class AirlinesResource {

	@Inject
	private AirlinesService airlinesService;

	@GET
	@Produces("application/json")
	public Airline findAirlineByCode(@NotNull @QueryParam("code") String code) {

		return this.airlinesService.findByCode(code);

	}

}
