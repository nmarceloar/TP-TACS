/**
 *
 */

package services;

import java.util.List;

import api.rest.UserDetails;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyUserService {

	private final static OfyUserService INSTANCE = new OfyUserService();

	public final static OfyUserService getInstance() {

		return OfyUserService.INSTANCE;

	}

	private OfyUserRepository userRepo;

	private OfyUserService() {

		this.userRepo = OfyUserRepository.getInstance();

	}

	public OfyUser createUser(final long id, final String name,
			final String facebookLink, final String email) {

		return OfyService.ofy()
			.transact(new Work<OfyUser>() {

				@Override
				public OfyUser run() {

					if (!OfyUserService.this.userRepo.exists(id)) {

						return OfyUserService.this.userRepo.add(new OfyUser(id,
								name, facebookLink, email));

					}

					throw new DomainLogicException(
							"Ya existe un usuario con ese id");

				}
			});

	}

	/**
	 * @param userDetails
	 */
	public OfyUser createUser(final UserDetails userDetails) {

		return OfyService.ofy()
			.transact(new Work<OfyUser>() {

				@Override
				public OfyUser run() {

					if (!OfyUserService.this.userRepo.exists(userDetails.getId())) {

						return OfyUserService.this.userRepo.add(new OfyUser(
								userDetails));

					}

					throw new DomainLogicException(
							"Ya existe un usuario con ese id");

				}
			});

	}

	public boolean exists(long userId) {

		return this.userRepo.exists(userId);

	}

	public List<OfyUser> findAll() {

		return this.userRepo.findAll();

	}

	public OfyUser findById(Long userId) {

		return this.userRepo.findById(userId);

	}

	public void removeAll() {
		this.userRepo.removeAll();
	}

}
