package repository;

import java.util.List;

import model2.Trip;
import model2.User;
import model2.impl.OfyTrip;

public interface TripsRepository {

	public Trip add(Trip trip);

	public boolean exists(String id);

	public List<OfyTrip> findAll();

	public Trip findById(String id);

	public List<OfyTrip> findByOwner(User owner);

	public void removeAll();

	void deleteById(final String tripId);

}