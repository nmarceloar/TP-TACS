


package api.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyTrip;
import services.OfyTripService;
import services.OfyTripServiceImpl;

@Path("/me/accepted-trips")
@Security
public class AcceptedTripsResource {
    
    private OfyTripService tripsService = OfyTripServiceImpl.getInstance();
    
    @GET
    @Produces("application/json")
    public List<AcceptedOfyTrip>
        findAcceptedTrips(@NotNull @LoggedUserId final Long userId) {
    
        return this.tripsService.findAcceptedByTarget(userId);
        
    }
}
