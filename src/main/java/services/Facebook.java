package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import api.rest.FriendsList;
import api.rest.TokenInfo;
import api.rest.UserDetails;

public class Facebook {

	private final static String GRAPH_API = "https://graph.facebook.com/v2.3";

	private final static String APP_ID = "1586547271608233";
	private final static String APP_SECRET = "359a6eae58ad036b4df0c599d0cdd11a";

	private final static String APPTOKEN = Facebook.APP_ID + "|"
		+ Facebook.APP_SECRET;

	private final static Client client = FacebookClient.getInstance();

	private String token;

	public static String getApptoken() {
		return APPTOKEN;
	}

	public Facebook(final String token) {

		this.token = token;

	}

	public FriendsList findFriends() {

		return this.buildFriendsListEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(FriendsList.class);

	}

	public String findTagged() {

		return this.buildTaggedEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);

	}

	public String findNotifications() {

		return this.buildNotificationsListEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);

	}

	public String findStatuses() {

		return this.buildStatusesEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);

	}

	public String findLinks() {

		return this.buildLinksEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);

	}

	public String findPosts() {

		return this.buildPostsListEndpoint()
			.request(MediaType.APPLICATION_JSON)
			.get(String.class);

	}

	public static UserDetails getUserDetails(String token) {

		return buildMeEndpoint(token).request(MediaType.APPLICATION_JSON)
			.get(UserDetails.class);

	}

	public static TokenInfo tokenInfo(String token) {

		return buildDebugEndpoint(token).request(MediaType.APPLICATION_JSON)
			.get(TokenInfo.class);

	}

	private static WebTarget buildDebugEndpoint(String token) {

		return client.target(Facebook.GRAPH_API)
			.path("debug_token")
			.queryParam("input_token", token)
			.queryParam("access_token", Facebook.APPTOKEN);

	}

	private WebTarget buildFriendsListEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/friends")
			.queryParam("access_token", token);

	}

	private static WebTarget buildMeEndpoint(String token) {

		return client.target(Facebook.GRAPH_API)
			.path("me")
			.queryParam("access_token", token);

	}

	private WebTarget buildNotificationsListEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/notifications")
			.queryParam("access_token", token);

	}

	private WebTarget buildPostsListEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/posts")
			.queryParam("access_token", token);

	}

	private WebTarget buildLinksEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/links")
			.queryParam("access_token", token);

	}

	private WebTarget buildStatusesEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/statuses")
			.queryParam("access_token", token);

	}

	private WebTarget buildTaggedEndpoint() {

		return client.target(Facebook.GRAPH_API)
			.path("/me")
			.path("/tagged")
			.queryParam("access_token", token);

	}

}
