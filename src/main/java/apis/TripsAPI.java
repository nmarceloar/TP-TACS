/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import java.util.List;
import model.Trip;

/**
 *
 * @author Flavio L. Pietrolati
 */
public interface TripsAPI {
    
    public List<Trip> getTripsOfPassenger(String id);
    
    public List<Trip> getTrips();
    
    public void saveTrip(Trip v);
    
    public Trip getTrip(int id);
    
    public List<Trip> getTripsOfFriendsOfUser(String id);
    
    public String deleteTrip(int id);
    
}
