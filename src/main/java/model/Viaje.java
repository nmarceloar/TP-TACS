package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Viaje implements Serializable {

    @JsonProperty("idPasajero")
    private int idViajante;

    @JsonProperty("itinerario")
    private List<Trayecto> itinerario;

    @JsonProperty("id")
    private int idViaje;

    public Viaje() {
    }

    public Viaje(int viajante, List<Trayecto> itinerario) {
        this.idViajante = viajante;
        this.itinerario = itinerario;
    }

    public int getIdViajante() {
        return idViajante;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public void setViajante(int viajante) {
        this.idViajante = viajante;
    }

    public List<Trayecto> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<Trayecto> itinerario) {
        this.itinerario = itinerario;
    }

    public Date getFechaSalidaViaje() {
        return getItinerario()
                .get(0).getFechaSalida();
    }

    public Date getFechaArriboViaje() {
        return getItinerario()
                .get(itinerario.size() - 1).getFechaArribo();
    }

    public void agregarTrayecto(Trayecto tray) {
        getItinerario().add(tray);
    }

}
