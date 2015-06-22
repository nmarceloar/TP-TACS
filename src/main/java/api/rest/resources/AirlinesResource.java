


package api.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/airlines")
@Security
public class AirlinesResource {
    
    @Inject
    private AirlinesService airlinesService;
    
    @GET
    @Produces("application/json")
    public Airline findAirlineByCode(@QueryParam("code") String code) {
    
        return this.airlinesService.findByCode(code);
        
    }
    
}
