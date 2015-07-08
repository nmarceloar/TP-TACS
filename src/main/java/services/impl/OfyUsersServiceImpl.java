/**
 *
 */

package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.impl.OfyUser;
import repository.OfyUsersRepository;
import services.OfyUsersService;
import api.rest.UserDetails;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyUsersServiceImpl implements OfyUsersService {

	private final OfyUsersRepository userRepo;

	@Inject
	public OfyUsersServiceImpl(final OfyUsersRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public OfyUser createUser(final UserDetails userDetails) {

		return OfyService.ofy()
			.transact(new Work<OfyUser>() {

				@Override
				public OfyUser run() {
					if (!userRepo.exists(userDetails.getId())) {

						return userRepo.add(OfyUser.createFrom(userDetails));

					}

					throw new DomainLogicException("Ya existe un usuario con ese id");

				}
			});

	}

	@Override
	public boolean exists(final long userId) {

		return this.userRepo.exists(userId);

	}

	@Override
	public List<OfyUser> findAll() {

		return this.userRepo.findAll();

	}

	@Override
	public OfyUser findById(final long userId) {

		return this.userRepo.findById(userId);

	}

	@Override
	public void removeAll() {

		this.userRepo.removeAll();

	}

}
