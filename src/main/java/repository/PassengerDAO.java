/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Passenger;

/**
 *
 * @author flavio
 */
public interface PassengerDAO {
    
    public void guardarPasajero(Passenger p);
    
    public Passenger getPasajeroById(int id);
    
    public List<Passenger> getTodosLosPasajeros();
    
    public List<Passenger> getAmigos(int id);
    
    public List<Integer> getIdsAmigos(int id);
    
    public List<Integer> getRecomendacionesDeUsuario(int pas);
    
    public void assignFriend(int idUser, int idFriend);
    
}
