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
    private Flight infoVuelo;

    public Trayecto() {

    }

    public Trayecto(Aeropuerto origen, Aeropuerto destino, Flight infoVuelo) {
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

    public Flight getInfoVuelo() {
        return infoVuelo;
    }

    public void setInfoVuelo(Flight infoVuelo) {
        this.infoVuelo = infoVuelo;
    }

    public Date getFechaSalida() {
        return getInfoVuelo()
                .getDepartureDate();
    }

    public Date getFechaArribo() {
        return getInfoVuelo()
                .getArrivalDate();
    }

}
