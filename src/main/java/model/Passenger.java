package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Passenger implements Serializable {

    private static int contadorId = 1;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellido")
    private String apellido;

    @JsonProperty("dni")
    private long dni;

    @JsonProperty("id")
    private int idUser;

    @JsonProperty("amigos")
    private List<Integer> amigos;
    
    @JsonProperty("recomendaciones")
    private List<Integer> recomendaciones;

    public List<Integer> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<Integer> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Passenger(String nombre, String apellido, long dni,
            List<Integer> amigos) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.amigos = amigos;
        this.recomendaciones = new ArrayList<>();
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

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getApellido() {

        return apellido;
    }

    public void setApellido(String apellido) {

        this.apellido = apellido;
    }

    public long getDni() {

        return dni;
    }

    public void setDni(long dni) {

        this.dni = dni;
    }

    public List<Integer> getAmigos() {

        return amigos;
    }

    public void setAmigos(List<Integer> amigos) {

        this.amigos = amigos;
    }

    public boolean esAmigo(Passenger pj) {
        boolean resul = false;
        for (Integer idAm : getAmigos()) {
            if (idAm == pj.getIdUser()) {
                resul = true;
            }
        }
        return resul;
    }
    
    public void agregarAmigo(int idAmigo){
        getAmigos().add(idAmigo);
    }
    
    public void agregarAmigo(Passenger p){
        getAmigos().add(p.getIdUser());
    }
    
    public void agregarAmigos(List<Integer> amigos){
        getAmigos().addAll(amigos);
    }
    
    public void agregarAmigosPorPasajeros(List<Passenger> amigos){
        List<Integer> listaIds = new ArrayList<>();
        for (Passenger p : amigos) {
            listaIds.add(p.getIdUser());
        }
        agregarAmigos(listaIds);
    }

}
