/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Trip;

/**
 *
 * @author flavio
 */
public interface TripsDAO {
    
    public void guardarViaje(Trip v);
    
    public Trip buscarViajePorId(int id);
    
    public List<Trip> getViajesDePasajero(int id);
    
//    public void asignarRecomendacion(Recomendacion rec, Pasajero p);
    
}
