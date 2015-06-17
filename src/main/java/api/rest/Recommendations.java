


package api.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyRecommendationsService;
import services.OfyRecommendation;

@Path("/recommendations")
public class Recommendations {
    
    private OfyRecommendationsService recommendationService =
        OfyRecommendationsService.getInstance();
    
    @GET
    @Produces("application/json")
    public List<OfyRecommendation> findAll() {
    
        return this.recommendationService.findAll();
        
    }
    
}
