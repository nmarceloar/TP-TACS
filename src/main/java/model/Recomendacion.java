/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

package model;

/**
 *
 * @author flavio
 */
public class Recomendacion {
	
	private static int generadorId = 0;
	
	private final int idRecomendacion;
	
	private int idUsuarioRecom;
	
	public Recomendacion(final int idUsuarioRecom) {
	
		this.idUsuarioRecom = idUsuarioRecom;
		this.idRecomendacion = Recomendacion.generadorId++;
	}
	
	public int getIdUsuarioRecom() {
	
		return this.idUsuarioRecom;
	}
	
	public void setIdUsuarioRecom(final int idUsuarioRecom) {
	
		this.idUsuarioRecom = idUsuarioRecom;
	}
	
}
