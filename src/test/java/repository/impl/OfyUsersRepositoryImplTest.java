package repository.impl;

import api.rest.exceptions.DomainLogicException;
import model2.User;
import model2.impl.OfyUser;

import org.junit.Assert;
import org.junit.Test;

import repository.OfyUsersRepository;
import unitTests.services.BaseOfyTest;

public class OfyUsersRepositoryImplTest extends BaseOfyTest {

    @Test
    public void testAdd() {

        OfyUsersRepository repo = new OfyUsersRepositoryImpl();

        User user = repo.add(OfyUser.createFrom(1L,
                "user1",
                "fblink",
                "mail"));

        Assert.assertEquals(1L, user.getId());
        Assert.assertTrue(repo.exists(1L));
        Assert.assertEquals(user, repo.findById(1L));

    }

    @Test
    public void testExists() {

        OfyUsersRepository repo = new OfyUsersRepositoryImpl();

        repo.add(OfyUser.createFrom(1L, "user1", "fblink", "mail"));

        Assert.assertTrue(repo.exists(1L));

    }

    @Test
    public void testFindAll() {

        OfyUsersRepository repo = new OfyUsersRepositoryImpl();

        User user1 = repo.add(OfyUser.createFrom(1L,
                "user1",
                "fblink",
                "mail"));

        User user2 = repo.add(OfyUser.createFrom(2L,
                "user2",
                "fblink",
                "mail"));

        Assert.assertEquals(2, repo.findAll()
                .size());

        Assert.assertTrue(repo.findAll()
                .contains(user1));

        Assert.assertTrue(repo.findAll()
                .contains(user2));

    }

    @Test
    public void testFindById() {

        OfyUsersRepository repo = new OfyUsersRepositoryImpl();

        OfyUser user = OfyUser.createFrom(1L, "user1", "fblink", "mail");

        repo.add(user);

        Assert.assertTrue(repo.exists(1L));
        Assert.assertEquals(user, repo.findById(1L));

    }

    @Test(expected = DomainLogicException.class)
    public void testFindByIdNotFound() {
        OfyUsersRepository repo = new OfyUsersRepositoryImpl();
        OfyUser user = OfyUser.createFrom(1L, "user1", "fblink", "mail");
        repo.add(user);

        repo.findById(3L);

    }

    @SuppressWarnings("unused")
    @Test
    public void testRemoveAll() {

        OfyUsersRepository repo = new OfyUsersRepositoryImpl();

        User user1 = repo.add(OfyUser.createFrom(1L,
                "user1",
                "fblink",
                "mail"));

        User user2 = repo.add(OfyUser.createFrom(2L,
                "user2",
                "fblink",
                "mail"));

        Assert.assertEquals(2, repo.findAll()
                .size());

        repo.removeAll();

        Assert.assertEquals(0, repo.findAll()
                .size());

    }

}
