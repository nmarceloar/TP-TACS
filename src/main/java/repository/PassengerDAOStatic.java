/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import integracion.facebook.NombreFB;
import integracion.facebook.ApellidoFB;
import integracion.facebook.SearchFriendsFB;
import integracion.facebook.UserRegisteredFB;

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

        // ID Tincho 10204737549535191 segun el 10206028316763565
        // ID Flavio 10153326807009452 o el 10153253398579452
        // Simulo la carga de amigos existentes en Facebook
        Passenger pMartin = new Passenger(10206028316763565L, "Martin", "De Ciervo", "11", new ArrayList());
        Passenger pFlavio = new Passenger(10153253398579452L, "Flavio", "Pietrolati", "22", new ArrayList());

        listaPasajeros.addAll(Arrays.asList(pMartin, pFlavio));
//        listaPasajeros.addAll(Arrays.asList(pMartin));
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
    public Passenger postPasajeroByIdToken(long id, String shortToken) {
        Passenger buscado = null;
        for (Passenger p : getListaPasajeros()) {
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

            guardarPasajero(pasajero);
            obtenerAmigosFB(pasajero);

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

//    @Override
//    public List<Integer> getRecomendacionesDeUsuario(long pas) {
//        for (Passenger psj : getListaPasajeros()) {
//            if (psj.getIdUser() == pas) {
//                return psj.getRecommendations();
//            }
//        }
//        return null;
//    }

    @Override
    public void assignFriend(long idUser, long idFriend) {
        Passenger pass = getPasajeroById(idUser);
        pass.agregarAmigo(idFriend);
    }

    private void obtenerAmigosFB(Passenger pasajero) {

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
            if (getPasajeroById(fbUs.getId()) != null) {
                assignFriend(pasajero.getIdUser(), fbUs.getId());
//                assignFriend(p.getIdUser(), pasajero.getIdUser());
            }
        }

    }

}
