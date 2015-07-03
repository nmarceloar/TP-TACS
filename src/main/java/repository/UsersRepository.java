package repository;

import java.util.List;

import model2.User;

public interface UsersRepository {

	public User add(User user);

	public boolean exists(long id);

	public List<? extends User> findAll();

	public User findById(long id);

	public void removeAll();

}