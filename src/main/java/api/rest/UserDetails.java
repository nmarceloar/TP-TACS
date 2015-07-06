/**
 * 
 */

package api.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {

	private long id;
	private String name;
	private String email;
	private String link;

	@JsonCreator
	public UserDetails(@JsonProperty("id") long id,
		@JsonProperty("name") String name,
		@JsonProperty("email") String email,
		@JsonProperty("link") String link) {

		this.id = id;
		this.name = name;
		this.email = email;
		this.link = link;

	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getLink() {
		return link;
	}

}
