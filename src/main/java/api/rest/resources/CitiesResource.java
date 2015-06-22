package api.rest.resources;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.CitiesServiceImpl;
import services.City;
import apis.CitiesService;

@Path("/cities")
@RequestScoped
public class CitiesResource {

	private final CitiesService provider = new CitiesServiceImpl();

	@GET
	@Produces("application/json")
	public List<City> getByCityName(
			@NotNull @QueryParam("name") final String name) {

		return this.provider.findByName(name);

	}

}
