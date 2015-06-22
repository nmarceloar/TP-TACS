/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion.facebook;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author flpitu88
 */
public class RecommendationBeanFB implements Serializable {

	// Usuario que envia la recomendacion
	@JsonProperty("idUsuario")
	private String idUser;

	@JsonProperty("idViaje")
	private int idTrip;

	public RecommendationBeanFB() {
	}

	public RecommendationBeanFB(String idUser, int idTrip) {
		this.idUser = idUser;
		this.idTrip = idTrip;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public int getIdTrip() {
		return idTrip;
	}

	public void setIdTrip(int idTrip) {
		this.idTrip = idTrip;
	}

}
