


package api.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyUser;

@Path("/users")
@Security
public class OUserResource {
    
    private OfyUserService ofyUserService = OfyUserService.getInstance();
    
    @GET
    @Produces("application/json")
    public List<OfyUser> findall() {
    
        return this.ofyUserService.findAll();
        
    }
}
