package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Passenger implements Serializable {

    private static int contadorId = 1;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("apellido")
    private String surname;

    @JsonProperty("dni")
    private long dni;

    @JsonProperty("id")
    private int idUser;

    @JsonProperty("amigos")
    private List<Integer> friends;
    
    @JsonProperty("recomendaciones")
    private List<Integer> recommendations;

    public List<Integer> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Integer> recommendations) {
        this.recommendations = recommendations;
    }

    public Passenger(String nombre, String apellido, long dni,
            List<Integer> amigos) {
        this.name = nombre;
        this.surname = apellido;
        this.dni = dni;
        this.friends = amigos;
        this.recommendations = new ArrayList<>();
        this.idUser = contadorId++;
    }

    public Passenger() {
        this.idUser = contadorId++;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }

    public boolean esAmigo(Passenger pj) {
        boolean resul = false;
        for (Integer idAm : getFriends()) {
            if (idAm == pj.getIdUser()) {
                resul = true;
            }
        }
        return resul;
    }
    
    public void agregarAmigo(int idAmigo){
        getFriends().add(idAmigo);
    }
    
    public void agregarAmigo(Passenger p){
        getFriends().add(p.getIdUser());
    }
    
    public void agregarAmigos(List<Integer> amigos){
        getFriends().addAll(amigos);
    }
    
    public void agregarAmigosPorPasajeros(List<Passenger> amigos){
        List<Integer> listaIds = new ArrayList<>();
        for (Passenger p : amigos) {
            listaIds.add(p.getIdUser());
        }
        agregarAmigos(listaIds);
    }

}
