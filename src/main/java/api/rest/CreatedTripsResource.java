


package api.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyTrip;
import services.TripDetails;
import services.OfyTripService;
import services.OfyTripServiceImpl;

@Path("/me/created-trips")
@Security
public class CreatedTripsResource {
    
    private final static Logger LOGGER =
        Logger.getLogger(CreatedTripsResource.class.getCanonicalName());
    
    private OfyTripService tripsService = OfyTripServiceImpl.getInstance();
    
    @POST
    @Consumes("application/json")
    @Produces("application/Json")
    public OfyTrip createTripForUser(@NotNull @LoggedUserId final Long userId,
        TripDetails tripDetails) {
    
        LOGGER.info("userId: " + userId);
        
        return this.tripsService.createTrip(userId, tripDetails);
        
    }
    
    @GET
    @Produces("application/json")
    public List<OfyTrip> findByOwner(@NotNull @LoggedUserId final Long userId) {
    
        LOGGER.info("userId: " + userId);
        
        return this.tripsService.findByOwner(userId);
        
    }
    
}
