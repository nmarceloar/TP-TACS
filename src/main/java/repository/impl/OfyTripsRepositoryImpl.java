package repository.impl;

import java.util.List;

import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import repository.OfyTripsRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyTripsRepositoryImpl implements OfyTripsRepository {

	@Override
	public OfyTrip add(final OfyTrip trip) {

		OfyService.ofy()
			.save()
			.entity(trip)
			.now();

		return trip;

	}

	@Override
	public void deleteById(final String tripId) {

		OfyService.ofy()
			.delete()
			.type(OfyTrip.class)
			.id(tripId)
			.now();

	}

	@Override
	public boolean exists(final String id) {

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
	public OfyTrip findById(final String id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyTrip.class)
				.id(id)
				.safe();

		} catch (final NotFoundException nfe) {

			throw new DomainLogicException("No se encontro el viaje con id " + id
				+ "\n"
				+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
				+ "\nSe deben cargar los viajes primero");
		}

	}

	@Override
	public List<OfyTrip> findByOwner(final OfyUser owner) {

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

}
