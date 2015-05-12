package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;


public class Trayecto implements Serializable {

    @JsonProperty("aeropuertoO")
    private AirportBean origen;

    @JsonProperty("aeropuertoD")
    private AirportBean destino;

    @JsonProperty("vuelo")
    private Flight infoVuelo;

    public Trayecto() {

    }

    public Trayecto(AirportBean origen, AirportBean destino, Flight infoVuelo) {
        this.origen = origen;
        this.destino = destino;
        this.infoVuelo = infoVuelo;
    }

    public AirportBean getOrigen() {
        return origen;
    }

    public void setOrigen(AirportBean origen) {
        this.origen = origen;
    }

    public AirportBean getDestino() {
        return destino;
    }

    public void setDestino(AirportBean destino) {
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
