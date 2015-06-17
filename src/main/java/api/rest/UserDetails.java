/**
 * 
 */



package api.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {
    
    private final long id;
    
    private final String name;
    
    private final String email;
    
    private final String link;
    
    @JsonCreator
    public UserDetails(@JsonProperty("id") final long id,
        @JsonProperty("name") final String name,
        @JsonProperty("email") final String email,
        @JsonProperty("link") final String link) {
    
        this.id = id;
        this.name = name;
        this.email = email;
        this.link = link;
        
    }
    
    public String getEmail() {
    
        return this.email;
    }
    
    public long getId() {
    
        return this.id;
    }
    
    public String getLink() {
    
        return this.link;
    }
    
    public String getName() {
    
        return this.name;
    }
    
}
