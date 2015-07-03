package model2;

import java.util.Date;

import api.rest.views.TripDetails;

public interface Trip {

	public Date getCreationDate();

	public String getId();

	public User getOwner();

	public TripDetails getTripDetails();

	public boolean wasCreatedBy(User owner);

}