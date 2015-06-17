


package api.rest;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import services.OfyUser;

@Path("/me")
@Security
public class MeResource {
    
    @Inject
    private Facebook facebook;
    
    private OfyUserService users = OfyUserService.getInstance();
    
    @GET
    @Produces("application/json")
    public OfyUser me(@NotNull @LoggedUserId final Long userId) {
    
        return users.findById(userId);
        
    }
    
    @Path("/friends")
    @GET
    @Produces("application/json")
    public FriendsList findFriendsList() {
    
        return facebook.findFriends();
        
    }
    
    @Path("/notifications")
    @GET
    @Produces("application/json")
    public String findNotificationsList() {
    
        return facebook.findNotifications();
        
    }
    
    @Path("/posts")
    @GET
    @Produces("application/json")
    public String findPostsList() {
    
        return facebook.findPosts();
        
    }
    
    @Path("/links")
    @GET
    @Produces("application/json")
    public String findLinksList() {
    
        return facebook.findLinks();
        
    }
    
    @Path("/statuses")
    @GET
    @Produces("application/json")
    public String findStatusesList() {
    
        return facebook.findStatuses();
        
    }
    
    @Path("/tagged")
    @GET
    @Produces("application/json")
    public String findTaggedList() {
    
        return facebook.findTagged();
        
    }
    
}
