package ar.utn.ba.grupo6.core;

import java.util.Date;
import java.util.List;

public class Viaje {

    private Pasajero viajante;
    private List<Trayecto> itinerario;

    public Viaje() {
    }

    public Pasajero getViajante() {
        return viajante;
    }

    public void setViajante(Pasajero viajante) {
        this.viajante = viajante;
    }

    public List<Trayecto> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<Trayecto> itinerario) {
        this.itinerario = itinerario;
    }

    public Viaje(Pasajero viajante, List<Trayecto> itinerario) {
        this.viajante = viajante;
        this.itinerario = itinerario;
    }

    public Date getFechaSalida() {
        return getItinerario().get(0)
                .getInfoVuelo()
                .getFechaSalida();
    }

    public Date getFechaArribo() {
        return getItinerario().get(getItinerario().size() - 1)
                .getInfoVuelo().getFechaArribo();
    }
}
