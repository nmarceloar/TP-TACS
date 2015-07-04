package repository.impl;

import java.util.List;

import model2.Trip;
import model2.User;
import model2.impl.OfyTrip;
import repository.TripsRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyTripRepository implements TripsRepository {

	@Override
	public Trip add(final Trip trip) {

		OfyService.ofy()
			.save()
			.entity(trip)
			.now();

		return trip;

	}

	@Override
	public boolean exists(String id) {

		return OfyService.ofy()
			.load()
			.type(OfyTrip.class)
			.id(id)
			.now() != null;

	}

	@Override
	public List<OfyTrip> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyTrip.class)
			.order("-creationDate")
			.list();

	}

	@Override
	public Trip findById(String id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyTrip.class)
				.id(id)
				.safe();

		} catch (NotFoundException nfe) {

			throw new DomainLogicException("No se encontro el viaje con id " + id
				+ "\n"
				+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
				+ "\nSe deben cargar los viajes primero");
		}

	}

	@Override
	public List<OfyTrip> findByOwner(User owner) {

		return OfyService.ofy()
			.load()
			.type(OfyTrip.class)
			.filter("owner", owner)
			.order("-creationDate")
			.list();

	}

	@Override
	public void removeAll() {

		OfyService.ofy()
			.delete()
			.keys(OfyService.ofy()
				.load()
				.type(OfyTrip.class)
				.keys()
				.list())
			.now();

	}

	@Override
	public void deleteById(final String tripId) {

		OfyService.ofy()
			.delete()
			.type(OfyTrip.class)
			.id(tripId)
			.now();

	}

}
