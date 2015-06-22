


package api.rest;

import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyRecommendationsService;
import services.OfyRecommendation;

@Path("/me/created-recommendations")
@Security
public class CreatedRecommendationsResource {
    
    private OfyRecommendationsService recommendationService =
        OfyRecommendationsService.getInstance();
    
    @POST
    @Produces("application/json")
    public OfyRecommendation
        createRecommendation(@NotNull @LoggedUserId final Long userId,
            @FormParam("targetid") Long targetId,
            @FormParam("tripid") String tripId) {
    
        // ojo, aca ya estariamos suponiendo que son amigos !
        
        return recommendationService.createRecommendation(userId, targetId,
            tripId);
        
    }
    
}
