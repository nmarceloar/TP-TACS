package services;

import java.util.List;

import model2.User;
import api.rest.UserDetails;
import model2.impl.OfyUser;

public interface UsersService {

	public User createUser(final long id, final String name,
		final String facebookLink, final String email);

	public User createUser(final UserDetails userDetails);

	public boolean exists(final long userId);

	public List<OfyUser> findAll();

	public User findById(final long userId);

	public void removeAll();

}