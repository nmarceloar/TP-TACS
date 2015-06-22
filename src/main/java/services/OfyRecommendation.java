package services;

import java.util.Date;

import api.rest.exceptions.DomainLogicException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class OfyRecommendation {

	public static enum Status {

		PENDING, ACCEPTED, REJECTED

	}

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

	public OfyRecommendation(final OfyUser owner, final OfyUser target,
			final OfyTrip trip) {

		this.owner = Ref.create(owner);
		this.target = Ref.create(target);
		this.trip = Ref.create(trip);

		this.id = this.buildId(owner, target, trip);

		this.status = Status.PENDING;
		this.creationDate = System.currentTimeMillis();
		this.patchDate = 0; // significa no actualizada todavia

	}

	private String buildId(final OfyUser owner, final OfyUser target,
			final OfyTrip trip) {

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
		OfyRecommendation other = (OfyRecommendation) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getCreationDate() {

		return new Date(this.creationDate);

	}

	public String getId() {

		return this.id;
	}

	public OfyUser getOwner() {

		return this.owner.get();

	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getPatchDate() {

		return new Date(this.patchDate);

	}

	public Status getStatus() {

		return this.status;
	}

	public OfyUser getTarget() {

		return this.target.get();
	}

	public OfyTrip getTrip() {

		return this.trip.get();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	private boolean isPending() {

		return this.status == OfyRecommendation.Status.PENDING;
	}

	public OfyRecommendation markAs(final OfyRecommendation.Status newStatus) {

		// refactoring cuanto antes

		if (newStatus == Status.PENDING) {

			throw new DomainLogicException(
					"No se puede actualizar la recomendacion.\n"
							+ "Se debe especificar " + Status.ACCEPTED + " o "
							+ Status.REJECTED);

		}

		if (this.isPending()) {

			this.status = newStatus;
			this.patchDate = System.currentTimeMillis();

			return this;

		}

		throw new DomainLogicException(
				"No se puede actualizar la recomendacion.\nLa recomendacion no estaba pendiente");

	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("OfyRecommendation [getCreationDate()=");
		builder.append(this.getCreationDate());
		builder.append(", getId()=");
		builder.append(this.getId());
		builder.append(", getOwner()=");
		builder.append(this.getOwner());
		builder.append(", getStatus()=");
		builder.append(this.getStatus());
		builder.append(", getTarget()=");
		builder.append(this.getTarget());
		builder.append(", getTrip()=");
		builder.append(this.getTrip());
		builder.append(", isPending()=");
		builder.append(this.isPending());
		builder.append(", getPatchDate()=");
		builder.append(this.getPatchDate());
		builder.append("]");
		return builder.toString();
	}

	public boolean wasCreatedFor(final OfyUser target) {

		return this.getTarget()
				.equals(target);

	}

}
