


package api.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import services.FacebookClient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Facebook {
    
    // https://graph.facebook.com/debug_token?input_token=value&access_token=app_id|app_secret
    
    private final static String GRAPH_API = "https://graph.facebook.com/v2.3";
    
    private final static String APP_ID = "1586547271608233";
    
    private final static String APP_SECRET = "359a6eae58ad036b4df0c599d0cdd11a";
    
    private final static String APPTOKEN = Facebook.APP_ID + "|" +
        Facebook.APP_SECRET;
    
    private final static Client client = FacebookClient.getInstance();
    
    private String token;
    
    // /{user-id}/links shows only the links that were published by this person.
    // /{user-id}/posts shows only the posts that were published by this person.
    // /{user-id}/statuses shows only the status update posts that were
    // published by this person.
    // /{user-id}/tagged shows only the posts that this person was tagged in.
    
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

@JsonIgnoreProperties(ignoreUnknown = true)
class TokenInfo {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Data {
        
        private final long app_id;
        
        private final String application;
        
        private final long expires_at;
        
        private final boolean is_valid;
        
        private final List<String> scopes;
        
        private final long user_id;
        
        @JsonCreator
        public Data(@JsonProperty("app_id") final long app_id,
            @JsonProperty("application") final String application,
            @JsonProperty("expires_at") final long expires_at,
            @JsonProperty("is_valid") final boolean is_valid,
            @JsonProperty("scopes") final List<String> scopes,
            @JsonProperty("user_id") final long user_id) {
        
            this.app_id = app_id;
            this.application = application;
            this.expires_at = expires_at;
            this.is_valid = is_valid;
            this.scopes = scopes;
            this.user_id = user_id;
            
        }
        
        public long getApp_id() {
        
            return this.app_id;
            
        }
        
        public String getApplication() {
        
            return this.application;
        }
        
        public long getExpires_at() {
        
            return this.expires_at;
        }
        
        public List<String> getScopes() {
        
            return this.scopes;
        }
        
        public long getUser_id() {
        
            return this.user_id;
        }
        
        public boolean isIs_valid() {
        
            return this.is_valid;
        }
        
    }
    
    private final Data data;
    
    @JsonCreator
    public TokenInfo(@JsonProperty("data") final Data data) {
    
        this.data = data;
    }
    
    public Data getData() {
    
        return this.data;
    }
    
    public DateTime getExpirationDate() {
    
        return new DateTime(Long.valueOf(this.data.getExpires_at() * 1000));
    }
    
    public long getUserId() {
    
        return this.data.getUser_id();
    }
    
    public boolean isValid() {
    
        return this.data.isIs_valid();
    }
    
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class FriendsList {
    
    private List<Friend> data;
    
    private Summary summary;
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    static class Friend {
        
        private long id;
        
        private String name;
        
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    static class Summary {
        
        private String total_count;
        
    }
    
}
