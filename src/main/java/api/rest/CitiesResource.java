package api.rest;

import integracion.despegar.City;
import apis.CityProvider;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/cities")
public class CitiesResource {

    private final CityProvider provider;

    @Inject
    public CitiesResource(final CityProvider provider) {

        this.provider = provider;
    }

    @GET
    @Produces("application/json")
    public List<City> getByCityName(
            @NotNull @QueryParam("name") final String name) {

        return this.provider.findByName(name);

    }

}
