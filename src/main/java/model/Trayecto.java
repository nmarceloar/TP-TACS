
package model;

import java.util.Date;

public class Trayecto {
	
	private Aeropuerto origen;
	
	private Aeropuerto destino;
	
	private Vuelo infoVuelo;
	
	public Trayecto() {
	
	}
	
	public Trayecto(final Aeropuerto origen, final Aeropuerto destino,
	    final Vuelo infoVuelo) {
	
		super();
		this.origen = origen;
		this.destino = destino;
		this.infoVuelo = infoVuelo;
	}
	
	public Aeropuerto getDestino() {
	
		return this.destino;
	}
	
	public Date getFechaArribo() {
	
		return this.getInfoVuelo().getFechaArribo();
	}
	
	public Date getFechaSalida() {
	
		return this.getInfoVuelo().getFechaSalida();
	}
	
	public Vuelo getInfoVuelo() {
	
		return this.infoVuelo;
	}
	
	public Aeropuerto getOrigen() {
	
		return this.origen;
	}
	
	public void setDestino(final Aeropuerto destino) {
	
		this.destino = destino;
	}
	
	public void setInfoVuelo(final Vuelo infoVuelo) {
	
		this.infoVuelo = infoVuelo;
	}
	
	public void setOrigen(final Aeropuerto origen) {
	
		this.origen = origen;
	}
	
}
