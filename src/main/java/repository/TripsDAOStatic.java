/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import model.Aeropuerto;
import model.Trayecto;
import model.Viaje;
import model.Vuelo;
import org.springframework.stereotype.Repository;

/**
 *
 * @author flavio
 */
@Repository
public class TripsDAOStatic implements TripsDAO {

    private final List<Viaje> listaViajes;

    public TripsDAOStatic() {
        Trayecto tray1 = new Trayecto(new Aeropuerto("BUE", "Argentina", "Buenos Aires"),
                new Aeropuerto("GRU", "Brasil", "Sao Paulo"),
                new Vuelo("LAN", "L01", "Airbus", new Date(2015, 1, 2), new Date(2015, 1, 30)));
        Viaje viaje1 = new Viaje(0, Arrays.asList(tray1));
        listaViajes = Arrays.asList(viaje1);
    }

    public List<Viaje> getListaViajes() {
        return listaViajes;
    }

    @Override
    public void guardarViaje(Viaje v) {
        getListaViajes().add(v);
    }

    @Override
    public Viaje buscarViajePorId(int id) {
        Viaje buscado = null;
        for (Viaje v : getListaViajes()) {
            if (v.getIdViaje() == id) {
                buscado = v;
            }
        }
        return buscado;
    }

    @Override
    public List<Viaje> getViajesDePasajero(int id) {
        List<Viaje> lista = new ArrayList<>();
        for (Viaje v : getListaViajes()) {
            if (v.getIdViajante() == id) {
                lista.add(v);
            }
        }
        return lista;
    }

}
