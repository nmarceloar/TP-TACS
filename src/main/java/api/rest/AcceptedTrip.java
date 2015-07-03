package api.rest;

import java.util.Date;

import model2.User;
import api.rest.views.TripDetails;

public interface AcceptedTrip {

	public User getOwner();

	public String getTripId();

	public TripDetails getTripDetails();

	public Date getCreationDate();

	public Date getPatchDate();

}