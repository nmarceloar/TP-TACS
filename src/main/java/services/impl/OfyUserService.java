/**
 *
 */

package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.User;
import model2.impl.OfyUser;
import repository.UsersRepository;
import services.UsersService;
import api.rest.UserDetails;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyUserService implements UsersService {

	private UsersRepository userRepo;

	@Inject
	public OfyUserService(UsersRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public User createUser(final long id, final String name,
		final String facebookLink, final String email) {

		return OfyService.ofy()
			.transact(new Work<User>() {

				@Override
				public User run() {

					if (!userRepo.exists(id)) {

						return userRepo.add(new OfyUser(id,
							name,
							facebookLink,
							email));

					}

					throw new DomainLogicException("Ya existe un usuario con ese id");

				}
			});

	}

	@Override
	public User createUser(final UserDetails userDetails) {

		return OfyService.ofy()
			.transact(new Work<User>() {

				@Override
				public User run() {

					if (!OfyUserService.this.userRepo.exists(userDetails.getId())) {

						return userRepo.add(new OfyUser(userDetails));

					}

					throw new DomainLogicException("Ya existe un usuario con ese id");

				}
			});

	}

	@Override
	public boolean exists(long userId) {

		return this.userRepo.exists(userId);

	}

	@Override
	public List<? extends User> findAll() {

		return userRepo.findAll();

	}

	@Override
	public User findById(long userId) {

		return this.userRepo.findById(userId);

	}

	@Override
	public void removeAll() {

		this.userRepo.removeAll();

	}

}
