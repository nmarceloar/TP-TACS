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

	@Id
	private long id;

	private String name;

	private String facebookLink;

	private String email;

	@Index
	private Long registrationDate;

	private OfyUser() {

	}

	public OfyUser(long id, String name, String facebookLink, String email) {

		this.id = id;
		this.name = name;
		this.facebookLink = facebookLink;
		this.email = email;
		this.registrationDate = System.currentTimeMillis();

	}

	public OfyUser(UserDetails userDetails) {

		this.id = userDetails.getId();
		this.name = userDetails.getName();
		this.facebookLink = userDetails.getLink();
		this.email = userDetails.getEmail();
		this.registrationDate = System.currentTimeMillis();

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

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("OfyUser [getId()=");
		builder.append(this.getId());
		builder.append(", getName()=");
		builder.append(this.getName());
		builder.append(", getRegistrationDate()=");
		builder.append(this.getRegistrationDate());
		builder.append(", getFacebookLink()=");
		builder.append(this.getFacebookLink());
		builder.append(", getEmail()=");
		builder.append(this.getEmail());
		builder.append("]");
		return builder.toString();
	}

}
