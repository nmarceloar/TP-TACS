/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import integracion.facebook.NombreFB;
import integracion.facebook.ApellidoFB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import model.Passenger;


import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
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
        Passenger pasajero1 = new Passenger(1,"pasajero1", "apellido1", "111111", new ArrayList());
        Passenger pasajero2 = new Passenger(2,"pasajero2", "apellido2", "222222", new ArrayList());
        Passenger pasajero3 = new Passenger(3,"pasajero3", "apellido3", "333333", new ArrayList());
        Passenger pasajero4 = new Passenger(4,"pasajero4", "apellido4", "444444", new ArrayList());
        Passenger pasajero5 = new Passenger(5,"pasajero5", "apellido5", "555555", new ArrayList());
        Passenger pasajero6 = new Passenger(6,"pasajero6", "apellido6", "666666", new ArrayList());
        Passenger pasajero7 = new Passenger(7,"pasajero7", "apellido7", "777777", new ArrayList());

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
    public Passenger getPasajeroById(long id) {
        Passenger buscado = null;
        for (Passenger p : getListaPasajeros()) {
            if (p.getIdUser() == id) {
                buscado = p;
            }
        }
        return buscado;
    }
    
    @Override
    public Passenger postPasajeroByIdToken(long id, String shortToken){
        Passenger buscado = null;
        for (Passenger p : getListaPasajeros()) {
            if (p.getIdUser() == id) {
            	ClientConfig config = new ClientConfig().register(new JacksonFeature());
                Client client = ClientBuilder.newClient(config);
                WebTarget target = client.target("https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=1586547271608233&client_secret=359a6eae58ad036b4df0c599d0cdd11a&fb_exchange_token="+shortToken);       
                Invocation.Builder invocationBuilder = target.request();
                Response response = invocationBuilder.get();
                String longToken = response.readEntity(new GenericType<String>() {});
                longToken = longToken.substring(13);
                longToken = longToken.split("&",2)[0];
                buscado = p;
                buscado.setToken(longToken);
            }
        }
        if(buscado == null){
        	ClientConfig config = new ClientConfig().register(new JacksonFeature());
            Client client = ClientBuilder.newClient(config);
            WebTarget target = client.target("https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=1586547271608233&client_secret=359a6eae58ad036b4df0c599d0cdd11a&fb_exchange_token="+shortToken);       
            Invocation.Builder invocationBuilder = target.request();
            Response response = invocationBuilder.get();
            String longToken = response.readEntity(new GenericType<String>() {});
            longToken = longToken.substring(13);
            longToken = longToken.split("&",2)[0];
            
            
            target = client.target("https://graph.facebook.com/"+id+"?fields=first_name&access_token="+longToken);       
            invocationBuilder = target.request();
            response = invocationBuilder.get();
            NombreFB nombre = response.readEntity(new GenericType<NombreFB>() {});

            
            
            target = client.target("https://graph.facebook.com/"+id+"?fields=last_name&access_token="+longToken);       
            invocationBuilder = target.request();
            response = invocationBuilder.get();
            ApellidoFB apellido = response.readEntity(new GenericType<ApellidoFB>() {});
            
        	
            Passenger pasajero = new Passenger(id,nombre.getFirst_name(), apellido.getLast_name(), longToken, new ArrayList());
        	guardarPasajero(pasajero);
        	return pasajero;
        }
        return buscado;
    }

    @Override
    public List<Passenger> getTodosLosPasajeros() {
        return getListaPasajeros();
    }

    @Override
    public List<Passenger> getAmigos(long id) {
        List<Passenger> amigos = new ArrayList();
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == id) {
                for (Long idPas : psj.getFriends()) {
                    amigos.add(getPasajeroById(idPas));
                }
                return amigos;
            }
        }
        return null;
    }

    @Override
    public List<Long> getIdsAmigos(long id) {
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == id) {
                return psj.getFriends();
            }
        }
        return null;
    }

    @Override
    public List<Integer> getRecomendacionesDeUsuario(long pas) {
        for (Passenger psj : getListaPasajeros()) {
            if (psj.getIdUser() == pas) {
                return psj.getRecommendations();
            }
        }
        return null;
    }

    @Override
    public void assignFriend(long idUser, long idFriend) {
        Passenger pass = getPasajeroById(idUser);
        pass.agregarAmigo(idFriend);
    }

}
