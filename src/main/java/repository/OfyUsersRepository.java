package repository;

import java.util.List;

import model2.impl.OfyUser;

public interface OfyUsersRepository {

	public OfyUser add(final OfyUser user);

	public boolean exists(final long id);

	public List<OfyUser> findAll();

	public OfyUser findById(final long id);

	public void removeAll();

}