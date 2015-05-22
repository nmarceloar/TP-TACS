package integracion.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IdToken implements Serializable{
	
	@JsonProperty("id")
	long id;
	@JsonProperty("token")
	String token;
	
	
	public String gettoken() {
		return token;
	}
	public void settoken(String token) {
		this.token = token;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public IdToken(String token, long id) {
		super();
		this.token = token;
		this.id = id;
	}	
	public IdToken(){
		
	}
}