package integracion.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NombreFB implements Serializable{
	
	
	@JsonProperty("first_name")
	String first_name;
	@JsonProperty("id")
	String id;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public NombreFB(String first_name, String id) {
		super();
		this.first_name = first_name;
		this.id = id;
	}
	
	public NombreFB(){
		
	}
}