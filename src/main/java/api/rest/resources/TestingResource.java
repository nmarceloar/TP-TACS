


package api.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyRecommendationsService;
import services.OfyRecommendation;
import services.OfyTrip;
import services.OfyUser;
import services.OfyTripService;
import services.OfyTripServiceImpl;

@Path("/testing")
@Security
public class TestingResource {
    
    private OfyUserService users = OfyUserService.getInstance();
    
    private OfyTripService trips = OfyTripServiceImpl.getInstance();
    
    private OfyRecommendationsService recommendations =
        OfyRecommendationsService.getInstance();
    
    @GET
    @Path("/users")
    @Produces("application/json")
    public List<OfyUser> findAllUsers() {
    
        return this.users.findAll();
        
    }
    
    @GET
    @Path("/recommendations")
    @Produces("application/json")
    public List<OfyRecommendation> findAllRecommendations() {
    
        return this.recommendations.findAll();
        
    }
    
    @GET
    @Path("/trips")
    @Produces("application/json")
    public List<OfyTrip> findAllTrips() {
    
        return this.trips.findAll();
        
    }
}
