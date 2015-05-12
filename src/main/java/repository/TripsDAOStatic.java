/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Trip;
import model.Segment;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio
 */
@Repository
public class TripsDAOStatic implements TripsDAO {
    
    private final List<Trip> listaViajes;
    
    public TripsDAOStatic() {
        Segment seg1 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "LAN", "A01");
        Trip viaje1 = new Trip(0, Arrays.asList(seg1));
        listaViajes = Arrays.asList(viaje1);
    }
    
    public List<Trip> getTripList() {
        return listaViajes;
    }
    
    @Override
    public void saveTrip(Trip v) {
        getTripList().add(v);
    }
    
    @Override
    public Trip searchTripById(int id) {
        Trip buscado = null;
        for (Trip v : getTripList()) {
            if (v.getIdTrip() == id) {
                buscado = v;
            }
        }
        return buscado;
    }
    
    @Override
    public List<Trip> searchTripByPassenger(int id) {
        List<Trip> lista = new ArrayList<>();
        for (Trip v : getTripList()) {
            if (v.getIdPassenger() == id) {
                lista.add(v);
            }
        }
        return lista;
    }
    
}
