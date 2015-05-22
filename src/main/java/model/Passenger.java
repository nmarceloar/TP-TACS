package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Passenger implements Serializable {

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("apellido")
    private String surname;

    @JsonProperty("token")
    private String token;

    @JsonProperty("id")
    private long idUser;

    @JsonProperty("amigos")
    private List<Long> friends;
    
    @JsonProperty("recomendaciones")
    private List<Integer> recommendations;

    public List<Integer> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Integer> recommendations) {
        this.recommendations = recommendations;
    }

    public Passenger(long id,String nombre, String apellido, String token,
            List<Long> amigos) {
        this.name = nombre;
        this.surname = apellido;
        this.token = token;
        this.friends = amigos;
        this.recommendations = new ArrayList<>();
        this.idUser = id;
    }

    public Passenger(){
    	
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    public boolean esAmigo(Passenger pj) {
        boolean resul = false;
        for (Long idAm : getFriends()) {
            if (idAm == pj.getIdUser()) {
                resul = true;
            }
        }
        return resul;
    }
    
    public void agregarAmigo(Long idAmigo){
        getFriends().add(idAmigo);
    }
    
    public void agregarAmigo(Passenger p){
        getFriends().add(p.getIdUser());
    }
    
    public void agregarAmigos(List<Long> amigos){
        getFriends().addAll(amigos);
    }
    
    public void agregarAmigosPorPasajeros(List<Passenger> amigos){
        List<Long> listaIds = new ArrayList<>();
        for (Passenger p : amigos) {
            listaIds.add(p.getIdUser());
        }
        agregarAmigos(listaIds);
    }

}
