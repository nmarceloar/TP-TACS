package services;

import api.rest.AcceptedOfyTrip;
import api.rest.AcceptedTrip;
import java.util.List;

import model2.Trip;
import api.rest.views.TripDetails;
import model2.impl.OfyTrip;

public interface TripsService {

	public Trip createTrip(long userId, TripDetails tripDetails);

	public List<? extends AcceptedTrip> findAcceptedByTarget(final long userId);

	public List<OfyTrip> findAll();

	public List<OfyTrip> findByOwner(long ownerId);

	public void removeAll();

	public Trip findById(String id);

	public void deleteById(final String tripId);

}
