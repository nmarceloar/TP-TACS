/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Segment;
import model.Trip;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import repository.TripsDAO;

/**
 *
 * @author flavio
 */
@Repository
public class TripsDAOStatic implements TripsDAO {

	private final List<Trip> listaViajes;

	public TripsDAOStatic() {
		listaViajes = new ArrayList<>();

		Segment seg1 = new Segment("Buenos Aires", "San Pablo", DateTime.now()
			.plusDays(5)
			.toString(), DateTime.now()
			.plusDays(30)
			.toString(), Integer.toString(25), "LAN", "A01");

		Trip viaje1 = new Trip("10153253398579452",
			"Buenos Aires",
			"San Pablo",
			"4500 ARS",
			Arrays.asList(seg1));
		listaViajes.add(viaje1);

		Segment seg2 = new Segment("Roma", "Paris", DateTime.now()
			.plusDays(15)
			.toString(), DateTime.now()
			.plusDays(45)
			.toString(), Integer.toString(30), "AA", "AA-054");

		Trip viaje2 = new Trip("10153253398579452",
			"Roma",
			"Paris",
			"4500 ARS",
			Arrays.asList(seg2));
		listaViajes.add(viaje2);

		Segment seg3 = new Segment("Berlin", "Amsterdam", DateTime.now()
			.plusDays(6)
			.toString(), DateTime.now()
			.plusDays(12)
			.toString(), Integer.toString(6), "LAN", "L23");

		Trip viaje3 = new Trip("10206028316763565",
			"Berlin",
			"Amsterdam",
			"4500 ARS",
			Arrays.asList(seg3));
		listaViajes.add(viaje3);

		Segment seg4 = new Segment("Buenos Aires", "San Pablo", DateTime.now()
			.plusDays(8)
			.toString(), DateTime.now()
			.plusDays(12)
			.toString(), Integer.toString(4), "Gol", "GO-012");

		Trip viaje4 = new Trip("10206028316763565",
			"Fortaleza",
			"Madrid",
			"4500 ARS",
			Arrays.asList(seg4));
		listaViajes.add(viaje4);
	}

	public List<Trip> getTripList() {
		return listaViajes;
	}

	@Override
	public void saveTrip(Trip v) {
		listaViajes.add(v);
	}

	@Override
	public String deleteTrip(int id) {
		Trip trip = searchTripById(id);
		String idResult = Integer.toString(trip.getIdTrip());
		listaViajes.remove(trip);
		return idResult;
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
	public List<Trip> getTrips() {
		return listaViajes;
	}

	@Override
	public List<Trip> searchTripByPassenger(String id) {
		List<Trip> lista = new ArrayList<>();
		for (Trip v : getTripList()) {
			if (v.getIdPassenger()
				.equals(id)) {
				lista.add(v);
			}
		}
		return lista;
	}

}