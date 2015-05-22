package integracion.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApellidoFB implements Serializable{
	
	
	@JsonProperty("last_name")
	String last_name;
	@JsonProperty("id")
	long id;
	
	public String getLast_name() {
		return last_name;
	}
	public void setlast_name(String last_name) {
		this.last_name = last_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ApellidoFB(String last_name, long id) {
		super();
		this.last_name = last_name;
		this.id = id;
	}
	
	public ApellidoFB(){
		
	}
}