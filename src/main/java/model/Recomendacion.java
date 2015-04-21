/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author flavio
 */
public class Recomendacion {
    
    private static int generadorId = 0;
    
    private int idRecomendacion;
    private int idUsuarioRecom;

    public Recomendacion(int idUsuarioRecom) {
        this.idUsuarioRecom = idUsuarioRecom;
        this.idRecomendacion = generadorId++;
    }

    public int getIdUsuarioRecom() {
        return idUsuarioRecom;
    }

    public void setIdUsuarioRecom(int idUsuarioRecom) {
        this.idUsuarioRecom = idUsuarioRecom;
    }
    
    
    
    
}
