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
    
    public List<Trip> getTripsOfPassenger(int id);
    
    public void saveTrip(Trip v);
    
}
