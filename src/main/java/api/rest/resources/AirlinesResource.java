package api.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.AirlinesService;
import services.AirlinesServiceImpl;

//@Path("/airlines")
@RequestScoped
public class AirlinesResource {

	private AirlinesService airlinesService = AirlinesServiceImpl.getInstance();

	@GET
	public Response findAirlineByCode(@QueryParam("code") final String code) {

		if (code == null) {

			return Response.ok()
				.type(MediaType.APPLICATION_JSON)
				.entity(this.airlinesService.findAll())
				.build();

		}

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity(this.airlinesService.findByCode(code))
			.build();

	}

}
