package services;

import java.util.List;

import model2.User;
import api.rest.UserDetails;

public interface UsersService {

	public User createUser(final long id, final String name,
		final String facebookLink, final String email);

	public User createUser(final UserDetails userDetails);

	public boolean exists(final long userId);

	public List<? extends User> findAll();

	public User findById(final long userId);

	public void removeAll();

}