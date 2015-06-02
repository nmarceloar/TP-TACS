/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author flavio
 */
public class Recommendation implements Serializable {

    private static int generadorId = 1;

    @JsonProperty("id")
    private int idRecomendacion;
    
    @JsonProperty("Usuario")
    private String idUsuarioRecom;
    
    @JsonProperty("DesdeUsuario")
    private String idUserFromRecom;
    
    @JsonProperty("nombreyap")
    private String nombreYAp;

    @JsonProperty("origen")
    private String ciudadOrig;
    
    @JsonProperty("destino")
    private String ciudadDest;
    
    @JsonProperty("viajeAsoc")
    private int tripRec;
    
    @JsonProperty("estado")
    private int estado;

    public int getTripRec() {
        return tripRec;
    }

    public void setTripRec(int tripRec) {
        this.tripRec = tripRec;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Recommendation() {
        idRecomendacion = generadorId++;
        nombreYAp = "";
        estado = 0;
    }

    public Recommendation(String idUser, String idFromUser, String nomYap, String origen, String destino, int viaje) {
        idRecomendacion = generadorId++;
        idUsuarioRecom = idUser;
        idUserFromRecom = idFromUser;
        ciudadOrig = origen;
        ciudadDest = destino;
        nombreYAp = nomYap;
        tripRec = viaje;
        estado = 0;
    }

    public int getIdRecomendacion() {
        return idRecomendacion;
    }

    public String getCiudadOrig() {
        return ciudadOrig;
    }

    public void setCiudadOrig(String ciudadOrig) {
        this.ciudadOrig = ciudadOrig;
    }

    public String getCiudadDest() {
        return ciudadDest;
    }

    public void setCiudadDest(String ciudadDest) {
        this.ciudadDest = ciudadDest;
    }

    public String getIdUsuarioRecom() {
        return idUsuarioRecom;
    }

    public void setIdUsuarioRecom(String idUsuarioRecom) {
        this.idUsuarioRecom = idUsuarioRecom;
    }

    public String getNombreYAp() {
        return nombreYAp;
    }

    public void setNombreYAp(String nombreYAp) {
        this.nombreYAp = nombreYAp;
    }
    
    public void aceptarRecomendacion(){
        estado = 1;
    }
    
    public void rechazarRecomendacion(){
        estado = -1;
    }

    public String getIdUserFromRecom() {
        return idUserFromRecom;
    }

    public void setIdUserFromRecom(String idUserFromRecom) {
        this.idUserFromRecom = idUserFromRecom;
    }
    
}
