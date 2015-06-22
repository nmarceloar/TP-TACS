


package api.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import services.City;
import apis.CitiesService;

@Path("/cities")
@Security
public class CitiesResource {
    
    private final CitiesService provider;
    
    @Inject
    public CitiesResource(final CitiesService provider) {
    
        this.provider = provider;
    }
    
    @GET
    @Produces("application/json")
    public List<City>
        getByCityName(@NotNull @QueryParam("name") final String name) {
    
        return this.provider.findByName(name);
        
    }
    
}
