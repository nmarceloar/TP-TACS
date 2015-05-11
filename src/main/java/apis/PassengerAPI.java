/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import java.util.List;
import model.Passenger;

/**
 *
 * @author Flavio L. Pietrolati
 */

public interface PassengerAPI {
    
    public List<Passenger> getListado();
    
    public void crearPassenger(Passenger p);
    
    public List<Passenger> getAmigosDePassenger(int id);
    
    public Passenger getPassengerPorId(int id);
    
}
