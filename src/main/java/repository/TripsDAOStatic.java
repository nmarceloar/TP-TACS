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
        listaViajes = new ArrayList<>();
        
        Segment seg1 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "LAN", "A01");
        Trip viaje1 = new Trip(1, Arrays.asList(seg1));
        listaViajes.add(viaje1);
        
        Segment seg2 = new Segment("Roma", "Paris",
                DateTime.now().plusDays(15).toString(),
                DateTime.now().plusDays(45).toString(),
                Integer.toString(30), "AA", "AA-054");
        Trip viaje2 = new Trip(3, Arrays.asList(seg2));
        listaViajes.add(viaje2);
        
        Segment seg3 = new Segment("Berlin", "Amsterdam",
                DateTime.now().plusDays(6).toString(),
                DateTime.now().plusDays(12).toString(),
                Integer.toString(6), "LAN", "L23");
        Trip viaje3 = new Trip(3, Arrays.asList(seg3));
        listaViajes.add(viaje2);
        
        Segment seg4 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(8).toString(),
                DateTime.now().plusDays(12).toString(),
                Integer.toString(4), "Gol", "GO-012");
        Trip viaje4 = new Trip(5, Arrays.asList(seg4));
        listaViajes.add(viaje2);
    }
    
    public List<Trip> getTripList() {
        return listaViajes;
    }
    
    @Override
    public void saveTrip(Trip v) {
        listaViajes.add(v);
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
