package api.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.CitiesService;
import api.rest.views.City;

@Path("/search/cities")
@RequestScoped
public class CitiesResource {

	@Inject
	private CitiesService citiesService;

	@GET
	@Produces("application/json")
	public List<City> getByCityName(
		@NotNull @QueryParam("name") final String name) {

		return this.citiesService.findByName(name);

	}

}
