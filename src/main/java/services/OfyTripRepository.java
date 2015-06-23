package services;

import static services.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.NotFoundException;

import api.rest.exceptions.DomainLogicException;

public class OfyTripRepository {

	private final static OfyTripRepository INSTANCE = new OfyTripRepository();

	private OfyTripRepository() {

	}

	public static OfyTripRepository getInstance() {

		return OfyTripRepository.INSTANCE;

	}

	public OfyTrip add(final OfyTrip trip) {

		OfyService.ofy().save().entity(trip).now();

		return trip;

	}

	public boolean exists(String id) {

		return OfyService.ofy().load().type(OfyTrip.class).id(id).now() != null;

	}

	public List<OfyTrip> findAll() {

		return OfyService.ofy().load().type(OfyTrip.class)
				.order("-creationDate").list();

	}

	public OfyTrip findById(String id) {

		try {

			return OfyService.ofy().load().type(OfyTrip.class).id(id).safe();

		} catch (NotFoundException nfe) {

			throw new DomainLogicException(
					"No se encontro el viaje con id "
							+ id
							+ "\n"
							+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
							+ "\nSe deben cargar los viajes primero");
		}

	}

	public List<OfyTrip> findByOwner(OfyUser owner) {

		return OfyService.ofy().load().type(OfyTrip.class)
				.filter("owner", owner).order("creationDate").list();

	}

	public void deleteById(final String tripId) {

		OfyService.ofy().delete().type(OfyTrip.class).id(tripId).now();

	}

}
