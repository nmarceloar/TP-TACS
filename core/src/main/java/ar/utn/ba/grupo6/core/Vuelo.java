package ar.utn.ba.grupo6.core;

import java.util.Date;

public class Vuelo {

    private String aerolinea;
    private String codVuelo;
    private String avion;
    private Date fechaSalida;
    private Date fechaArribo;

    public Vuelo() {
    }

    public Vuelo(String aerolinea, String codVuelo, String avion, Date salida, Date arribo) {
        this.aerolinea = aerolinea;
        this.codVuelo = codVuelo;
        this.avion = avion;
        this.fechaSalida = salida;
        this.fechaArribo = arribo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaArribo() {
        return fechaArribo;
    }

    public void setFechaArribo(Date fechaArribo) {
        this.fechaArribo = fechaArribo;
    }
    
    
}
