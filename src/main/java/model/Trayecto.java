package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;

public class Trayecto implements Serializable {

    @JsonProperty("aeropuertoO")
    private Aeropuerto origen;

    @JsonProperty("aeropuertoD")
    private Aeropuerto destino;

    @JsonProperty("vuelo")
    private Vuelo infoVuelo;

    public Trayecto() {

    }

    public Trayecto(Aeropuerto origen, Aeropuerto destino, Vuelo infoVuelo) {
        this.origen = origen;
        this.destino = destino;
        this.infoVuelo = infoVuelo;
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

    public Date getFechaSalida() {
        return getInfoVuelo()
                .getFechaSalida();
    }

    public Date getFechaArribo() {
        return getInfoVuelo()
                .getFechaArribo();
    }

}
