/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Passenger;

import org.springframework.stereotype.Repository;

import repository.PassengerDAO;

/**
 *
 * @author flavio
 */
@Repository
public class PassengerDAOStatic implements PassengerDAO {

	private final List<Passenger> listaPasajeros;

	public PassengerDAOStatic() {
		listaPasajeros = new ArrayList<>();

		// ID Tincho 10204737549535191 segun el 10206028316763565
		// ID Flavio 10153326807009452 o el 10153253398579452
		// Simulo la carga de amigos existentes en Facebook
		Passenger pMartin = new Passenger("10206028316763565",
			"Martin",
			"De Ciervo",
			"11",
			new ArrayList());
		Passenger pFlavio = new Passenger("10153253398579452",
			"Flavio",
			"Pietrolati",
			"22",
			new ArrayList());

		listaPasajeros.addAll(Arrays.asList(pMartin, pFlavio));
		// listaPasajeros.addAll(Arrays.asList(pMartin));
	}

	public List<Passenger> getListaPasajeros() {
		return listaPasajeros;
	}

	@Override
	public void guardarPasajero(Passenger p) {
		getListaPasajeros().add(p);
	}

	@Override
	public Passenger getPasajeroById(String id) {
		Passenger buscado = null;
		for (Passenger p : getListaPasajeros()) {
			if (p.getIdUser().equals(id)) {
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
	public List<Passenger> getAmigos(String id) {
		List<Passenger> amigos = new ArrayList();
		for (Passenger psj : getListaPasajeros()) {
			if (psj.getIdUser().equals(id)) {
				for (String idPas : psj.getFriends()) {
					amigos.add(getPasajeroById(idPas));
				}
				return amigos;
			}
		}
		return null;
	}

	@Override
	public List<String> getIdsAmigos(String id) {
		for (Passenger psj : getListaPasajeros()) {
			if (psj.getIdUser().equals(id)) {
				return psj.getFriends();
			}
		}
		return null;
	}

	@Override
	public void assignFriend(String idUser, String idFriend) {
		Passenger pass = getPasajeroById(idUser);
		pass.agregarAmigo(idFriend);
	}

}
