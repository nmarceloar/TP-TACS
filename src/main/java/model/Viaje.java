
package model;

import java.util.Date;
import java.util.List;

public class Viaje {
	
	private Pasajero viajante;
	
	private List<Trayecto> itinerario;
	
	public Viaje() {
	
	}
	
	public Viaje(final Pasajero viajante, final List<Trayecto> itinerario) {
	
		this.viajante = viajante;
		this.itinerario = itinerario;
	}
	
	public void agregarTrayecto(final Trayecto tray) {
	
		this.getItinerario()
		    .add(tray);
	}
	
	public Date getFechaArriboViaje() {
	
		return this.getItinerario()
		    .get(this.itinerario.size() - 1)
		    .getFechaArribo();
	}
	
	public Date getFechaSalidaViaje() {
	
		return this.getItinerario()
		    .get(0)
		    .getFechaSalida();
	}
	
	public List<Trayecto> getItinerario() {
	
		return this.itinerario;
	}
	
	public Pasajero getViajante() {
	
		return this.viajante;
	}
	
	public void setItinerario(final List<Trayecto> itinerario) {
	
		this.itinerario = itinerario;
	}
	
	public void setViajante(final Pasajero viajante) {
	
		this.viajante = viajante;
	}
	
}
