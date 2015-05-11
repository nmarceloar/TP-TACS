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
import java.util.List;
import model.Passenger;
import model.Recommendation;
import org.springframework.stereotype.Service;
import model.Viaje;
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
    public List<Passenger> getListado() {
        return psjDao.getTodosLosPasajeros();
    }

    @Override
    public void crearPassenger(Passenger p) {
        psjDao.guardarPasajero(p);
    }

    @Override
    public List<Passenger> getAmigosDePassenger(int idPsj) {
        return psjDao.getAmigos(idPsj);
    }

    @Override
    public Passenger getPassengerPorId(int id) {
        return psjDao.getPasajeroById(id);
    }

    @Override
    public List<Recommendation> getRecomendacionesDeUsario(int id) {
        return recDao.getRecomendacionesPorId(
                psjDao.getRecomendacionesDeUsuario(id));
    }

    @Override
    public String getRecommendationToString(int id) {
        Recommendation rec = recDao.getRecomendacionPorId(id);
        Passenger pass = psjDao.getPasajeroById(rec.getIdUsuarioRecom());
        return pass.getNombre() + " " + pass.getApellido()
                + " te recomienda viajar de " + rec.getCiudadOrig()
                + " a " + rec.getCiudadDest();
    }

    @Override
    public List<Viaje> getViajesDePasajero(int id) {
        return viajeDao.getViajesDePasajero(id);
    }

    @Override
    public void guardarViaje(Viaje v) {
        viajeDao.guardarViaje(v);
    }

}
