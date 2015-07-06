package model2.impl;

import java.util.Date;

import model2.Recommendation;
import model2.Trip;
import model2.User;
import utils.DateSerializer;
import api.rest.exceptions.DomainLogicException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class OfyRecommendation implements Recommendation {

	@Id
	private String id;

	@Index
	@Load
	private Ref<OfyUser> owner;

	@Index
	@Load
	private Ref<OfyUser> target;

	@Load
	private Ref<OfyTrip> trip;

	@Index
	private long creationDate;

	@Index
	private long patchDate;

	@Index
	private Status status;

	private OfyRecommendation() {

	}

	public static OfyRecommendation createFrom(OfyUser owner,
		OfyUser target, OfyTrip trip) {

		return new OfyRecommendation(owner, target, trip);

	}

	private OfyRecommendation(final OfyUser owner, final OfyUser target,
		final OfyTrip trip) {

		this.owner = Ref.create(owner);
		this.target = Ref.create(target);
		this.trip = Ref.create(trip);

		this.id = this.buildId(owner, target, trip);

		this.status = Status.PENDING;

		this.creationDate = System.currentTimeMillis();
		this.patchDate = 0; // significa no actualizada todavia

	}

	private String buildId(final User owner, final User target,
		final Trip trip) {

		final String fromId = String.valueOf(owner.getId());
		final String toId = String.valueOf(target.getId());
		final String tripId = trip.getId();

		return toId + tripId;

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OfyRecommendation)) {
			return false;
		}
		OfyRecommendation other = (OfyRecommendation)obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	@JsonSerialize(using = DateSerializer.class)
	public Date getCreationDate() {

		return new Date(this.creationDate);

	}

	@Override
	public String getId() {

		return this.id;
	}

	@Override
	public User getOwner() {

		return this.owner.get();

	}

	@Override
	@JsonSerialize(using = DateSerializer.class)
	public Date getPatchDate() {

		return new Date(this.patchDate);

	}

	@Override
	public Status getStatus() {

		return this.status;
	}

	@Override
	public User getTarget() {

		return this.target.get();
	}

	@Override
	public Trip getTrip() {

		return this.trip.get();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0
			: this.id.hashCode());
		return result;
	}

	private boolean isPending() {

		return this.status == Recommendation.Status.PENDING;
	}

	@Override
	public OfyRecommendation markAs(
		final OfyRecommendation.Status newStatus) {

		// refactoring cuanto antes
		if (newStatus == Status.PENDING) {

			throw new DomainLogicException("No se puede actualizar la recomendacion.\n" + "Se debe especificar "
				+ Status.ACCEPTED
				+ " o "
				+ Status.REJECTED);

		}

		if (this.isPending()) {

			this.status = newStatus;
			this.patchDate = System.currentTimeMillis();

			return this;

		}

		throw new DomainLogicException("No se puede actualizar la recomendacion.\nLa recomendacion no estaba pendiente");

	}

	@Override
	public boolean wasCreatedFor(final User target) {

		return this.getTarget()
			.equals(target);

	}

}
