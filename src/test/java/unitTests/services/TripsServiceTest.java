/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.TripsAPI;
import config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author flpitu88
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class TripsServiceTest {
    
    @Autowired
    TripsAPI tripServ;
    
    @Test
    public void getTripsOfPassengerTest(){
        
    }
    
    @Test
    public void getTripsTest(){
        
    }
    
    @Test
    public void saveTripTest(){
        
    }
    
    @Test
    public void getTripTest(){
        
    }
    
    @Test
    public void getTripsOfFriendsOfUserTest(){
        
    }
    
    @Test
    public void deleteTripTest(){
        
    }
    
}
