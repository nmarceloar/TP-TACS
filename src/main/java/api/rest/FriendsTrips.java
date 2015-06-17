


package api.rest;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import services.OfyTrip;
import services.OfyTripService;
import services.OfyTripServiceImpl;

@Path("/me/friends-trips")
@Security
public class FriendsTrips {
    
    private OfyTripService tripsService = OfyTripServiceImpl.getInstance();
    
    @GET
    @Produces("application/json")
    public List<OfyTrip>
        findTripsByOwner(@NotNull @QueryParam("friend-id") final Long friendId) {
    
        // ojo, aca habria que verificar si realmente son amigos
        // estamos asumiendo que si porque lo mandamos de nuestro front
        // pero si probamos de curl o cualquier otro cliente, cualquier
        // parametro es valido
        
        return this.tripsService.findByOwner(friendId);
        
    }
    
}
