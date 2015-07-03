/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package services.impl;

import integracion.facebook.ApellidoFB;
import integracion.facebook.NombreFB;
import integracion.facebook.RecommendationBeanFB;
import integracion.facebook.SearchFriendsFB;
import integracion.facebook.UserRegisteredFB;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import model.Passenger;
import model.Recommendation;
import model.Trip;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.PassengerDAO;
import repository.RecommendationDAO;
import repository.TripsDAO;
import services.PassengerAPI;
import services.RecommendationAPI;
import services.TripsAPI;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Service
public class PersistenceService implements PassengerAPI, TripsAPI,
	RecommendationAPI {

	@Autowired
	private PassengerDAO psjDao;

	@Autowired
	private TripsDAO viajeDao;

	@Autowired
	private RecommendationDAO recDao;

	public PersistenceService() {

	}

	@Override
	public void asignarPasajeroARecomendacion(Recommendation rec, String pass) {

		Passenger pj = this.psjDao.getPasajeroById(pass);
		rec.setNombreYAp(pj.getName() + " " + pj.getSurname());
	}

	/**
	 * ESTAS DOS DE ABAJO SE DEBERIAN HACER AL MOMENTO DE ENVIAR UNA
	 * RECOMENDACION Y CREARLA, NO AL MOMENTO DE LEER LAS EXISTENTES
	 *
	 * @param list
	 * @param pass
	 */
	@Override
	public void asignarPasajeroARecomendaciones(List<Recommendation> list,
		String pass) {

		for (Recommendation rec : list) {
			this.asignarPasajeroARecomendacion(rec, pass);
		}
	}

	private void assignFacebookFriendsToPassenger(Passenger pasajero) {

		ClientConfig config = new ClientConfig().register(new JacksonFeature());
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("https://graph.facebook.com/v2.3/" + pasajero.getIdUser()
			+ "/friends?fields=id,first_name,last_name&access_token="
			+ pasajero.getToken());
		Invocation.Builder invocationBuilder = target.request();
		Response response = invocationBuilder.get();
		SearchFriendsFB busqueda = response.readEntity(new GenericType<SearchFriendsFB>() {
		});

		/**
		 * Ahora solo es una asignacion unidireccional, teniendo en cuenta que
		 * se cargan estaticamente usuarios desde el inicio de la aplicacion,
		 * pero luego se quitara el comentario para que se asignen amigos hacia
		 * ambos lados de la relacion.
		 */
		for (UserRegisteredFB fbUs : busqueda.getUsuarios()) {
			if ((this.psjDao.getPasajeroById(fbUs.getId()) != null) && (this.sonAmigos(pasajero.getIdUser(),
				fbUs.getId()) == false)) {
				this.assignFriend(pasajero.getIdUser(), fbUs.getId());
				this.assignFriend(fbUs.getId(), pasajero.getIdUser());
			}
		}
	}

	@Override
	public void assignFriend(String idUser, String idFriend) {

		this.psjDao.assignFriend(idUser, idFriend);
	}

	@Override
	public void assignStateRecommendation(int idRec, String state) {

		Recommendation rec = this.recDao.getRecomendacionPorId(idRec);
		if (state.equals("acp")) {
			rec.aceptarRecomendacion();
			/**
			 * Al aceptar la recomendacion, creo un viaje con los mismos datos
			 * para el usuario que la acepto.
			 */
			Trip viajeRecom = this.viajeDao.searchTripById(rec.getTripRec());
			Trip newTrip = new Trip(rec.getIdUsuarioRecom(),
				viajeRecom.getFromCity(),
				viajeRecom.getToCity(),
				viajeRecom.getPrice(),
				viajeRecom.getItinerary());
			this.viajeDao.saveTrip(newTrip);
		} else if (state.equals("rej")) {
			rec.rechazarRecomendacion();
			// recDao.deleteRecommendation(idRec);
		}
	}

	@Override
	public void createPassenger(Passenger p) {

		this.psjDao.guardarPasajero(p);
	}

	private Passenger createPassengerToPost(String id, String shortToken) {

		Passenger buscado = null;
		for (Passenger p : this.psjDao.getTodosLosPasajeros()) {
			if (p.getIdUser()
				.equals(id)) {
				ClientConfig config = new ClientConfig().register(new JacksonFeature());
				Client client = ClientBuilder.newClient(config);
				WebTarget target = client.target("https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=1586547271608233&client_secret=359a6eae58ad036b4df0c599d0cdd11a&fb_exchange_token=" + shortToken);
				Invocation.Builder invocationBuilder = target.request();
				Response response = invocationBuilder.get();
				String longToken = response.readEntity(new GenericType<String>() {
				});
				longToken = longToken.substring(13);
				longToken = longToken.split("&", 2)[0];
				buscado = p;
				buscado.setToken(longToken);
				this.assignFacebookFriendsToPassenger(buscado);
			}
		}
		if (buscado == null) {
			ClientConfig config = new ClientConfig().register(new JacksonFeature());
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target("https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=1586547271608233&client_secret=359a6eae58ad036b4df0c599d0cdd11a&fb_exchange_token=" + shortToken);
			Invocation.Builder invocationBuilder = target.request();
			Response response = invocationBuilder.get();
			String longToken = response.readEntity(new GenericType<String>() {
			});
			longToken = longToken.substring(13);
			longToken = longToken.split("&", 2)[0];

			target = client.target("https://graph.facebook.com/" + id
				+ "?fields=first_name&access_token="
				+ longToken);
			invocationBuilder = target.request();
			response = invocationBuilder.get();
			NombreFB nombre = response.readEntity(new GenericType<NombreFB>() {
			});

			target = client.target("https://graph.facebook.com/" + id
				+ "?fields=last_name&access_token="
				+ longToken);
			invocationBuilder = target.request();
			response = invocationBuilder.get();
			ApellidoFB apellido = response.readEntity(new GenericType<ApellidoFB>() {
			});

			Passenger pasajero = new Passenger(id,
				nombre.getFirst_name(),
				apellido.getLast_name(),
				longToken,
				new ArrayList());

			this.psjDao.guardarPasajero(pasajero);
			this.assignFacebookFriendsToPassenger(pasajero);

			return pasajero;
		}
		return buscado;
	}

	@Override
	public String deleteTrip(int id) {

		String s = this.viajeDao.deleteTrip(id);
		return s;
	}

	@Override
	public List<Passenger> getFriendsOfPassenger(String idPsj) {

		return this.psjDao.getAmigos(idPsj);
	}

	@Override
	public List<Passenger> getListOfPassengers() {

		return this.psjDao.getTodosLosPasajeros();
	}

	@Override
	public Passenger getPassengerById(String id) {

		Passenger psj = this.psjDao.getPasajeroById(id);
		return psj;
	}

	@Override
	public Recommendation getRecommendationById(int id) {

		return this.recDao.getRecomendacionPorId(id);
	}

	@Override
	public List<Recommendation> getRecommendationsOfUser(String id) {

		return this.recDao.getRecomendacionesDeUsuarioPorId(id);
	}

	@Override
	public String getRecommendationToString(int id) {

		Recommendation rec = this.recDao.getRecomendacionPorId(id);
		Passenger pass = this.psjDao.getPasajeroById(rec.getIdUsuarioRecom());
		return pass.getName() + " "
			+ pass.getSurname()
			+ " te recomienda viajar de "
			+ rec.getCiudadOrig()
			+ " a "
			+ rec.getCiudadDest();
	}

	@Override
	public Trip getTrip(int id) {

		return this.viajeDao.searchTripById(id);
	}

	@Override
	public List<Trip> getTrips() {

		return this.viajeDao.getTrips();
	}

	@Override
	public List<Trip> getTripsOfFriendsOfUser(String id) {

		List<String> amigos = this.psjDao.getIdsAmigos(id);
		List<Trip> viajes = new ArrayList<>();
		if (amigos.isEmpty()) {
			return viajes;
		} else {
			for (String fr : amigos) {
				viajes.addAll(this.viajeDao.searchTripByPassenger(fr));
			}
		}
		return viajes;
	}

	@Override
	public List<Trip> getTripsOfPassenger(String id) {

		return this.viajeDao.searchTripByPassenger(id);
	}

	@Override
	/**
	 * Instancia una recomendacion a partir de los datos recibidos, y la guarda
	 * en el DAO correspondiente.
	 */
	public void instanceAndSaveRecommendation(RecommendationBeanFB recBean,
		String idUser) {

		int idViajeRecom = recBean.getIdTrip();
		String idUsuarioQueRecomienda = recBean.getIdUser();
		Trip viaje = this.viajeDao.searchTripById(idViajeRecom);
		Passenger psj = this.psjDao.getPasajeroById(idUsuarioQueRecomienda);
		Recommendation rec = new Recommendation(idUser,
			idUsuarioQueRecomienda,
			psj.getName() + ' ' + psj.getSurname(),
			viaje.getFromCity(),
			viaje.getToCity(),
			idViajeRecom);
		this.recDao.saveRecommendation(rec);
	}

	@Override
	public Passenger postPassengerByIdToken(String id, String shortToken) {

		Passenger psj = this.createPassengerToPost(id, shortToken);
		return psj;
	}

	@Override
	public void saveRecommendation(Recommendation rec) {

		this.recDao.saveRecommendation(rec);
	}

	@Override
	public void saveTrip(Trip v) {

		this.viajeDao.saveTrip(v);
	}

	// #########################################################################
	private boolean sonAmigos(String id, String idFriend) {

		Passenger p = this.getPassengerById(id);
		// Chequeo que si no existe ese usuario, devuelva lista vacia
		if (p.getFriends() == null) {
			return false;
		}
		if (p.getFriends()
			.contains(idFriend)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Recommendation> getRecommendations() {
		return recDao.getRecomendaciones();
	}

}