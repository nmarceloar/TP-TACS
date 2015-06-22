package integracion.facebook;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApellidoFB implements Serializable {

	@JsonProperty("last_name")
	String last_name;
	@JsonProperty("id")
	String id;

	public String getLast_name() {
		return last_name;
	}

	public void setlast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ApellidoFB(String last_name, String id) {
		super();
		this.last_name = last_name;
		this.id = id;
	}

	public ApellidoFB() {

	}
}