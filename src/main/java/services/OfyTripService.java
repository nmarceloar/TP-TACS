package services;

import java.util.List;

import api.rest.AcceptedOfyTrip;

public interface OfyTripService {

	public OfyTrip createTrip(long userId, TripDetails tripDetails);

	public List<AcceptedOfyTrip> findAcceptedByTarget(final long userId);

	public List<OfyTrip> findAll();

	public List<OfyTrip> findByOwner(long ownerId);

	public void removeAll();

}
