/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Passenger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio
 */
@Repository
public class PassengerDAOStatic implements PassengerDAO {

    private final List<Passenger> listaPasajeros;

    public PassengerDAOStatic() {
        listaPasajeros = new ArrayList<>();
        Passenger pasajero1 = new Passenger("pasajero1", "apellido1", 111111, new ArrayList());
        Passenger pasajero2 = new Passenger("pasajero2", "apellido2", 222222, new ArrayList());
        Passenger pasajero3 = new Passenger("pasajero3", "apellido3", 333333, new ArrayList());
        Passenger pasajero4 = new Passenger("pasajero4", "apellido4", 444444, new ArrayList());
        Passenger pasajero5 = new Passenger("pasajero5", "apellido5", 555555, new ArrayList());
        Passenger pasajero6 = new Passenger("pasajero6", "apellido6", 666666, new ArrayList());
        Passenger pasajero7 = new Passenger("pasajero7", "apellido7", 777777, new ArrayList());

        pasajero1.agregarAmigo(pasajero3);
        pasajero1.setRecommendations(Arrays.asList(1));
        pasajero1.agregarAmigo(pasajero5);
        pasajero2.agregarAmigosPorPasajeros(Arrays.asList(pasajero4, pasajero6, pasajero7));

        listaPasajeros.addAll(Arrays.asList(pasajero1, pasajero2, pasajero3, pasajero4,
                pasajero5, pasajero6, pasajero7));
    }

    public List<Passenger> getListaPasajeros() {
        return listaPasajeros;
    }

    @Override
    public void guardarPasajero(Passenger p) {
        getListaPasajeros().add(p);
    }

    @Override
    public Passenger getPasajeroById(int id) {
        Passenger buscado = null;
        for (Passenger p : getListaPasajeros()) {
            if (p.getIdUser() == id) {
                buscado = p;
            }
        }
        return buscado;
    }

    @Override
    public List<Passenger> getTodosLosPasajeros() {
        return getListaPasajeros();
    }

    @Override
    public List<Passenger> getAmigos(int id) {
        List<Passenger> amigos = new ArrayList();
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == id) {
                for (Integer idPas : psj.getFriends()) {
                    amigos.add(getPasajeroById(idPas));
                }
                return amigos;
            }
        }
        return null;
    }

    @Override
    public List<Integer> getIdsAmigos(int id) {
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == id) {
                return psj.getFriends();
            }
        }
        return null;
    }

    @Override
    public List<Integer> getRecomendacionesDeUsuario(int pas) {
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == pas) {
                return psj.getRecommendations();
            }
        }
        return null;
    }

    @Override
    public void assignFriend(int idUser, int idFriend) {
        Passenger pass = getPasajeroById(idUser);
        pass.agregarAmigo(idFriend);
    }

}
