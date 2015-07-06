package repository.impl;

import java.util.List;

import model2.impl.OfyUser;
import repository.OfyUsersRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyUsersRepositoryImpl implements OfyUsersRepository {

	@Override
	public OfyUser add(final OfyUser user) {

		OfyService.ofy()
			.save()
			.entity(user)
			.now();

		return user;

	}

	@Override
	public boolean exists(final long id) {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.id(id)
			.now() != null;

	}

	@Override
	public List<OfyUser> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.order("-registrationDate")
			.list();

	}

	@Override
	public OfyUser findById(final long id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyUser.class)
				.id(id)
				.safe();
		}

		catch (final NotFoundException nfe) {

			throw new DomainLogicException("No se encontro el usuario con id " + id
				+ "\n"
				+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
				+ "\nSe deben cargar los usuarios primero");
		}

	}

	@Override
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
