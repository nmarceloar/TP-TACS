package repository;

import java.util.List;

import model2.User;
import model2.impl.OfyUser;

public interface UsersRepository {

	public User add(User user);

	public boolean exists(long id);

	public List<OfyUser> findAll();

	public User findById(long id);

	public void removeAll();

}