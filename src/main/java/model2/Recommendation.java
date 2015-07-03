package model2;

import java.util.Date;

public interface Recommendation {

	public static enum Status {

		PENDING, ACCEPTED, REJECTED

	}

	public Date getCreationDate();

	public String getId();

	public User getOwner();

	public Date getPatchDate();

	public Status getStatus();

	public User getTarget();

	public Trip getTrip();

	public Recommendation markAs(Recommendation.Status newStatus);

	public boolean wasCreatedFor(User target);

}