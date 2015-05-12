/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import apis.PassengerAPI;
import apis.RecommendationAPI;
import apis.TripsAPI;
import integracion.despegar.TripOption;
import apis.TripOptionsProvider;
import java.util.ArrayList;
import java.util.List;
import model.Passenger;
import model.Recommendation;
import org.springframework.stereotype.Service;
import model.Trip;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import repository.PassengerDAO;
import repository.RecommendationDAO;
import repository.TripsDAO;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Service
public class PersistenceService implements PassengerAPI, TripsAPI, RecommendationAPI {

    @Autowired
    private PassengerDAO psjDao;

    @Autowired
    private TripsDAO viajeDao;

    @Autowired
    private RecommendationDAO recDao;

    public PersistenceService() {
    }

    @Override
    public List<Passenger> getListOfPassengers() {
        return psjDao.getTodosLosPasajeros();
    }

    @Override
    public void createPassenger(Passenger p) {
        psjDao.guardarPasajero(p);
    }

    @Override
    public List<Passenger> getFriendsOfPassenger(int idPsj) {
        return psjDao.getAmigos(idPsj);
    }

    @Override
    public Passenger getPassengerById(int id) {
        return psjDao.getPasajeroById(id);
    }

    @Override
    public List<Recommendation> getRecommendationsOfUser(int id) {
        return recDao.getRecomendacionesPorId(
                psjDao.getRecomendacionesDeUsuario(id));
    }

    @Override
    public String getRecommendationToString(int id) {
        Recommendation rec = recDao.getRecomendacionPorId(id);
        Passenger pass = psjDao.getPasajeroById(rec.getIdUsuarioRecom());
        return pass.getName() + " " + pass.getSurname()
                + " te recomienda viajar de " + rec.getCiudadOrig()
                + " a " + rec.getCiudadDest();
    }

    @Override
    public List<Trip> getTripsOfPassenger(int id) {
        return viajeDao.searchTripByPassenger(id);
    }

    @Override
    public void saveTrip(Trip v) {
        viajeDao.saveTrip(v);
    }

    @Override
    public Recommendation getRecommendationById(int id) {
        return recDao.getRecomendacionPorId(id);
    }

    @Override
    public void saveRecommendation(Recommendation rec) {
        recDao.saveRecommendation(rec);
    }

    @Override
    public void assignFriend(int idUser, int idFriend) {
        psjDao.assignFriend(idUser, idFriend);
    }

    @Override
    public Trip getTrip(int id) {
        return viajeDao.searchTripById(id);
    }

    @Override
    public List<Trip> getTripsOfFriendsOfUser(int id) {
        List<Integer> amigos = psjDao.getIdsAmigos(id);
        List<Trip> viajes = new ArrayList<>();
        if (amigos.isEmpty()) {
            return viajes;
        } else {
            for (Integer fr : amigos) {
                viajes.addAll(viajeDao.searchTripByPassenger(fr));
            }
        }
        return viajes;
    }

}
