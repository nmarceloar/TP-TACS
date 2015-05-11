/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Viaje;

/**
 *
 * @author flavio
 */
public interface TripsDAO {
    
    public void guardarViaje(Viaje v);
    
    public Viaje buscarViajePorId(int id);
    
    public List<Viaje> getViajesDePasajero(int id);
    
//    public void asignarRecomendacion(Recomendacion rec, Pasajero p);
    
}
