package services;

import java.util.List;

import model2.impl.OfyUser;
import api.rest.UserDetails;

public interface OfyUsersService {

	public OfyUser createUser(final UserDetails userDetails);

	public boolean exists(final long userId);

	public List<OfyUser> findAll();

	public OfyUser findById(final long userId);

	public void removeAll();

}