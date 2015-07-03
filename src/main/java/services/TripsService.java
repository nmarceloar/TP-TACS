package services;

import java.util.List;

import model2.Trip;
import api.rest.AcceptedTrip;
import api.rest.views.TripDetails;

public interface TripsService {

	public Trip createTrip(long userId, TripDetails tripDetails);

	public List<? extends AcceptedTrip> findAcceptedByTarget(final long userId);

	public List<? extends Trip> findAll();

	public List<? extends Trip> findByOwner(long ownerId);

	public void removeAll();

	public Trip findById(String id);

	public void deleteById(final String tripId);

}
