package ar.utn.ba.grupo6.core;

import java.util.Date;

public class Trayecto {

    private Aeropuerto origen;
    private Aeropuerto destino;
    private Vuelo infoVuelo;

    public Trayecto() {
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public Vuelo getInfoVuelo() {
        return infoVuelo;
    }

    public void setInfoVuelo(Vuelo infoVuelo) {
        this.infoVuelo = infoVuelo;
    }

    private Date getSalida() {
        return getInfoVuelo().getFechaSalida();
    }

    private Date getLlegada() {
        return getInfoVuelo().getFechaArribo();
    }

}
