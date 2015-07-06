package repository;

import java.util.List;

import model2.impl.OfyTrip;
import model2.impl.OfyUser;

public interface OfyTripsRepository {

	public OfyTrip add(final OfyTrip trip);

	public boolean exists(final String id);

	public List<OfyTrip> findAll();

	public OfyTrip findById(final String id);

	public List<OfyTrip> findByOwner(final OfyUser owner);

	public void removeAll();

	void deleteById(final String tripId);

}