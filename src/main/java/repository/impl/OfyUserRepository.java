package repository.impl;

import java.util.List;

import model2.User;
import model2.impl.OfyUser;
import repository.UsersRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyUserRepository implements UsersRepository {

	@Override
	public User add(final User user) {

		OfyService.ofy()
			.save()
			.entity(user)
			.now();

		return user;

	}

	@Override
	public boolean exists(long id) {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.id(id)
			.now() != null;

	}

	@Override
	public List<? extends User> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyUser.class)
			.order("-registrationDate")
			.list();

	}

	@Override
	public OfyUser findById(long id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyUser.class)
				.id(id)
				.safe();
		}

		catch (NotFoundException nfe) {

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
