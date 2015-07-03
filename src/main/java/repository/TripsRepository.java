package repository;

import java.util.List;

import model2.Trip;
import model2.User;

public interface TripsRepository {

	public Trip add(Trip trip);

	public boolean exists(String id);

	public List<? extends Trip> findAll();

	public Trip findById(String id);

	public List<? extends Trip> findByOwner(User owner);

	public void removeAll();

	void deleteById(final String tripId);

}