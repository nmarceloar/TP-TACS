/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion.facebook;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author flpitu88
 */
public class UserRegisteredFB implements Serializable {

	@JsonProperty("id")
	String id;

	@JsonProperty("first_name")
	String firstName;

	@JsonProperty("last_name")
	String surname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserRegisteredFB(String id, String firstName, String surname) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
	}

	public UserRegisteredFB() {
	}

}
