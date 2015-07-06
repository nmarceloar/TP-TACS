/**
 *
 */

package model2.impl;

import java.util.Date;

import model2.User;
import utils.DateSerializer;
import api.rest.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class OfyUser implements User {

	@Override
	public String toString() {
		return "OfyUser [getEmail()=" + getEmail()
			+ ", getFacebookLink()="
			+ getFacebookLink()
			+ ", getId()="
			+ getId()
			+ ", getName()="
			+ getName()
			+ ", getRegistrationDate()="
			+ getRegistrationDate()
			+ ", hashCode()="
			+ hashCode()
			+ "]";
	}

	@Id
	private long id;

	private String name;

	private String facebookLink;

	private String email;

	@Index
	private Long registrationDate;

	public static OfyUser createFrom(long id, String name,
		String facebookLink, String email) {

		return new OfyUser(id, name, facebookLink, email);

	}

	public static OfyUser createFrom(UserDetails userDetails) {

		return new OfyUser(userDetails);

	}

	private OfyUser() {
	}

	private OfyUser(long id, String name, String facebookLink, String email) {

		this.id = id;
		this.name = name;
		this.facebookLink = facebookLink;
		this.email = email;
		this.registrationDate = System.currentTimeMillis();

	}

	private OfyUser(UserDetails userDetails) {

		this(userDetails.getId(), userDetails.getName(), userDetails.getLink(), userDetails.getEmail());

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OfyUser)) {
			return false;
		}
		OfyUser other = (OfyUser)obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String getEmail() {

		return this.email;
	}

	@Override
	public String getFacebookLink() {

		return this.facebookLink;
	}

	@Override
	public long getId() {

		return this.id;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	@JsonSerialize(using = DateSerializer.class)
	public Date getRegistrationDate() {

		return new Date(this.registrationDate);

	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int)(this.id ^ (this.id >>> 32));
		return result;
	}

}
