/**
 *
 */

package services;

import java.util.List;

import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyUserRepository {

	private final static OfyUserRepository INSTANCE = new OfyUserRepository();

	public static OfyUserRepository getInstance() {

		return OfyUserRepository.INSTANCE;

	}

	private OfyUserRepository() {

	}

	public OfyUser add(final OfyUser user) {

		OfyService.ofy()
			.save()
			.entity(user)
			.now();

		return user;

	}

	public boolean exists(long id) {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.id(id)
			.now() != null;

	}

	public List<OfyUser> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.order("-registrationDate")
			.list();

	}

	public OfyUser findById(long id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyUser.class)
				.id(id)
				.safe();
		}

		catch (NotFoundException nfe) {

			throw new DomainLogicException(
					"No se encontro el usuario con id "
							+ id
							+ "\n"
							+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
							+ "\nSe deben cargar los usuarios primero");
		}

	}

	public void removeAll() {

		OfyService.ofy()
			.delete()
			.keys(OfyService.ofy()
				.load()
				.type(OfyUser.class)
				.keys()
				.list())
			.now();

	}

}
