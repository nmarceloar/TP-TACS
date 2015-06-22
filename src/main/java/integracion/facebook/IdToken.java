package integracion.facebook;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdToken implements Serializable {

	@JsonProperty("id")
	String id;
	@JsonProperty("token")
	String token;

	public String gettoken() {
		return token;
	}

	public void settoken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdToken(String token, String id) {
		super();
		this.token = token;
		this.id = id;
	}

	public IdToken() {

	}
}