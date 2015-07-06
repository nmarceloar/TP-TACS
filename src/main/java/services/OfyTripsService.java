package services;

import java.util.List;

import model2.impl.OfyTrip;
import api.rest.AcceptedOfyTrip;
import api.rest.views.TripDetails;

public interface OfyTripsService {

	public OfyTrip createTrip(final long userId,
		final TripDetails tripDetails);

	public List<AcceptedOfyTrip> findAcceptedByTarget(final long userId);

	public List<OfyTrip> findAll();

	public List<OfyTrip> findByOwner(final long ownerId);

	public void removeAll();

	public OfyTrip findById(String id);

	public void deleteById(final String tripId);

}
