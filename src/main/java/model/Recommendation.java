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
    private long idUsuarioRecom;

    @JsonProperty("origen")
    private String ciudadOrig;
    
    @JsonProperty("destino")
    private String ciudadDest;

    public Recommendation() {
        idRecomendacion = generadorId++;
    }

    public Recommendation(long idUser, String origen, String destino) {
        idRecomendacion = generadorId++;
        idUsuarioRecom = idUser;
        ciudadOrig = origen;
        ciudadDest = destino;
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

    public long getIdUsuarioRecom() {
        return idUsuarioRecom;
    }

    public void setIdUsuarioRecom(long idUsuarioRecom) {
        this.idUsuarioRecom = idUsuarioRecom;
    }

}
