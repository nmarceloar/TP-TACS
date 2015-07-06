package api.rest.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import model2.User;

import org.glassfish.jersey.process.internal.RequestScoped;

import services.Facebook;
import services.OfyUsersService;
import utils.SessionUtils;
import api.rest.FriendsList;

@Path("/me")
@RequestScoped
public class MeResource {

	@Context
	private HttpServletRequest request;

	@Inject
	private OfyUsersService usersService;

	@Path("/friends")
	@GET
	@Produces("application/json")
	public FriendsList findFriendsList() {

		return this.getFacebookService().findFriends();

	}

	@Path("/token")
	@GET
	@Produces("application/json")
	public String getAppToken() {

		return Facebook.getApptoken(); // ?????

	}

	@Path("/links")
	@GET
	@Produces("application/json")
	public String findLinksList() {

		return this.getFacebookService().findLinks();

	}

	@Path("/notifications")
	@GET
	@Produces("application/json")
	public String findNotificationsList() {

		return this.getFacebookService().findNotifications();

	}

	@Path("/posts")
	@GET
	@Produces("application/json")
	public String findPostsList() {

		return this.getFacebookService().findPosts();

	}

	@Path("/statuses")
	@GET
	@Produces("application/json")
	public String findStatusesList() {

		return this.getFacebookService().findStatuses();

	}

	@Path("/tagged")
	@GET
	@Produces("application/json")
	public String findTaggedList() {

		return this.getFacebookService().findTagged();

	}

	@GET
	@Produces("application/json")
	public User me() {

		return usersService.findById(this.getCurrentUserId());

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

}
