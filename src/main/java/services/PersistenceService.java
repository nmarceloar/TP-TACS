/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import apis.PassengerAPI;
import apis.RecommendationAPI;
import apis.TripsAPI;
import integracion.facebook.ApellidoFB;
import integracion.facebook.NombreFB;
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

import org.springframework.stereotype.Service;

import model.Trip;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
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
    public List<Passenger> getFriendsOfPassenger(long idPsj) {
        return psjDao.getAmigos(idPsj);
    }

    @Override
    public Passenger getPassengerById(long id) {
        Passenger psj = psjDao.getPasajeroById(id);
        return psj;
    }

    @Override
    public Passenger postPassengerByIdToken(long id, String shortToken) {
        Passenger psj = createPassengerToPost(id, shortToken);
        return psj;
    }

    @Override
    public List<Recommendation> getRecommendationsOfUser(long id) {
        return recDao.getRecomendacionesDeUsuarioPorId(id);
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
    public List<Trip> getTripsOfPassenger(long id) {
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
    public void assignFriend(long idUser, long idFriend) {
        psjDao.assignFriend(idUser, idFriend);
    }

    @Override
    public Trip getTrip(int id) {
        return viajeDao.searchTripById(id);
    }

    @Override
    public List<Trip> getTripsOfFriendsOfUser(long id) {
        List<Long> amigos = psjDao.getIdsAmigos(id);
        List<Trip> viajes = new ArrayList<>();
        if (amigos.isEmpty()) {
            return viajes;
        } else {
            for (Long fr : amigos) {
                viajes.addAll(viajeDao.searchTripByPassenger(fr));
            }
        }
        return viajes;
    }

    /**
     * ESTAS DOS DE ABAJO SE DEBERIAN HACER AL MOMENTO DE ENVIAR UNA
     * RECOMENDACION Y CREARLA, NO AL MOMENTO DE LEER LAS EXISTENTES
     *
     * @param list
     * @param pass
     */
    @Override
    public void asignarPasajeroARecomendaciones(List<Recommendation> list, long pass) {
        for (Recommendation rec : list) {
            asignarPasajeroARecomendacion(rec, pass);
        }
    }

    @Override
    public void asignarPasajeroARecomendacion(Recommendation rec, long pass) {
        Passenger pj = psjDao.getPasajeroById(pass);
        rec.setNombreYAp(pj.getName() + " " + pj.getSurname());
    }

    // #########################################################################
    private boolean sonAmigos(long id, long idFriend){
    	Passenger p = getPassengerById(id);
        // Chequeo que si no existe ese usuario, devuelva lista vacia
    	if (p.getFriends() == null){
            return false;
        }
    	if(p.getFriends().contains(idFriend)){
        return true;
    	}
    	return false;
    }
    private void assignFacebookFriendsToPassenger(Passenger pasajero) {

        ClientConfig config = new ClientConfig().register(new JacksonFeature());
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("https://graph.facebook.com/v2.3/" + pasajero.getIdUser() + "/friends?fields=id,first_name,last_name&access_token=" + pasajero.getToken());
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
            if (psjDao.getPasajeroById(fbUs.getId()) != null && sonAmigos(pasajero.getIdUser(),fbUs.getId())==false ) {
                assignFriend(pasajero.getIdUser(), fbUs.getId());
                assignFriend(fbUs.getId(), pasajero.getIdUser());
            }
        }
    }

    private Passenger createPassengerToPost(long id, String shortToken) {
        Passenger buscado = null;
        for (Passenger p : psjDao.getTodosLosPasajeros()) {
            if (p.getIdUser() == id) {
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
                assignFacebookFriendsToPassenger(buscado);
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

            target = client.target("https://graph.facebook.com/" + id + "?fields=first_name&access_token=" + longToken);
            invocationBuilder = target.request();
            response = invocationBuilder.get();
            NombreFB nombre = response.readEntity(new GenericType<NombreFB>() {
            });

            target = client.target("https://graph.facebook.com/" + id + "?fields=last_name&access_token=" + longToken);
            invocationBuilder = target.request();
            response = invocationBuilder.get();
            ApellidoFB apellido = response.readEntity(new GenericType<ApellidoFB>() {
            });

            Passenger pasajero = new Passenger(id, nombre.getFirst_name(), apellido.getLast_name(), longToken, new ArrayList());
            
            psjDao.guardarPasajero(pasajero);
            assignFacebookFriendsToPassenger(pasajero);

            return pasajero;
        }
        return buscado;
    }

}
