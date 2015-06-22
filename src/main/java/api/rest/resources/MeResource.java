package api.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.OfyUser;
import services.OfyUserService;
import api.rest.Facebook;
import api.rest.FriendsList;
import api.rest.SessionUtils;

@Path("/me")
@RequestScoped
public class MeResource {

	@Context
	private HttpServletRequest request;

	@Path("/friends")
	@GET
	@Produces("application/json")
	public FriendsList findFriendsList() {

		return this.getFacebookService()
			.findFriends();

	}

	@Path("/links")
	@GET
	@Produces("application/json")
	public String findLinksList() {

		return this.getFacebookService()
			.findLinks();

	}

	@Path("/notifications")
	@GET
	@Produces("application/json")
	public String findNotificationsList() {

		return this.getFacebookService()
			.findNotifications();

	}

	@Path("/posts")
	@GET
	@Produces("application/json")
	public String findPostsList() {

		return this.getFacebookService()
			.findPosts();

	}

	@Path("/statuses")
	@GET
	@Produces("application/json")
	public String findStatusesList() {

		return this.getFacebookService()
			.findStatuses();

	}

	@Path("/tagged")
	@GET
	@Produces("application/json")
	public String findTaggedList() {

		return this.getFacebookService()
			.findTagged();

	}

	private String getCurrentFacebookToken() {

		return SessionUtils.extractToken(this.getCurrentSession());

	}

	private HttpSession getCurrentSession() {

		return SessionUtils.existingFrom(this.request);

	}

	private Long getCurrentUserId() {

		return SessionUtils.extractUserId(this.getCurrentSession());

	}

	private Facebook getFacebookService() {

		return new Facebook(this.getCurrentFacebookToken());

	}

	@GET
	@Produces("application/json")
	public OfyUser me() {

		return OfyUserService.getInstance()
			.findById(this.getCurrentUserId());

	}

}
