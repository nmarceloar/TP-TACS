/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import api.rest.UserDetails;
import model2.impl.OfyUser;
import org.junit.Assert;
import org.junit.Test;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import repository.impl.OfyTripsRepositoryImpl;
import repository.impl.OfyUsersRepositoryImpl;
import services.OfyUsersService;
import services.impl.OfyUsersServiceImpl;

/**
 *
 * @author flavio
 */
public class UsersServiceTest extends BaseOfyTest {

    @Test
    public void createUserTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();

        final OfyUsersService usersService = new OfyUsersServiceImpl(userRepo);

        Assert.assertEquals(0, usersService.findAll().size());
        OfyUser user = usersService.createUser(new UserDetails(1L,
                "user1",
                "mail",
                "link"));
        userRepo.add(user);
        Assert.assertEquals(1, usersService.findAll().size());
        Assert.assertEquals("user1", usersService.findAll().get(0).getName());
        Assert.assertEquals("mail", usersService.findAll().get(0).getEmail());
        Assert.assertEquals("link", usersService.findAll().get(0).getFacebookLink());
    }

    @Test
    public void existsTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();

        final OfyUsersService usersService = new OfyUsersServiceImpl(userRepo);

        Assert.assertEquals(0, usersService.findAll().size());
        OfyUser user = usersService.createUser(new UserDetails(1L,
                "user1",
                "mail",
                "link"));
        userRepo.add(user);
        
        Assert.assertTrue(usersService.exists(1L));
    }
    
    @Test
    public void findByIdTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();

        final OfyUsersService usersService = new OfyUsersServiceImpl(userRepo);

        OfyUser user1 = usersService.createUser(new UserDetails(1L,
                "user1",
                "mail1",
                "link1"));
        userRepo.add(user1);
        OfyUser user2 = usersService.createUser(new UserDetails(2L,
                "user2",
                "mail2",
                "link2"));
        userRepo.add(user2);
        
        Assert.assertEquals("user2",usersService.findById(2L).getName());
    }
    
    @Test
    public void removeAllTest() {
        final OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
        final OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();

        final OfyUsersService usersService = new OfyUsersServiceImpl(userRepo);

        OfyUser user1 = usersService.createUser(new UserDetails(1L,
                "user1",
                "mail1",
                "link1"));
        userRepo.add(user1);
        OfyUser user2 = usersService.createUser(new UserDetails(2L,
                "user2",
                "mail2",
                "link2"));
        userRepo.add(user2);
        
        Assert.assertEquals(2,usersService.findAll().size());
        usersService.removeAll();
        Assert.assertEquals(0,usersService.findAll().size());
    }

}
