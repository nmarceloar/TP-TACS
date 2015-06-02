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
    
    public Passenger getPasajeroById(String id);
    
//    public Passenger postPasajeroByIdToken(long id, String shortToken);
    
    public List<Passenger> getTodosLosPasajeros();
    
    public List<Passenger> getAmigos(String id);
    
    public List<String> getIdsAmigos(String id);
    
//    public List<Integer> getRecomendacionesDeUsuario(long pas);
    
    public void assignFriend(String idUser, String idFriend);
    
}
