/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author flpitu88
 */
public class RecommendationBeanFB implements Serializable {

    // Usuario que envia la recomendacion
    @JsonProperty("idUsuario")
    private long idUser;

    @JsonProperty("idViaje")
    private int idTrip;

    public RecommendationBeanFB() {
    }

    public RecommendationBeanFB(long idUser, int idTrip) {
        this.idUser = idUser;
        this.idTrip = idTrip;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

}
