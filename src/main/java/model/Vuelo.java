
package model;

import java.util.Date;

public class Vuelo {
	
	private String aerolinea;
	
	private String nroVuelo;
	
	private String avion;
	
	private Date fechaSalida;
	
	private Date fechaArribo;
	
	public Vuelo() {
	
		super();
	}
	
	public Vuelo(final String aerolinea, final String nroVuelo,
	    final String avion, final Date fechaSalida, final Date fechaArribo) {
	
		super();
		this.aerolinea = aerolinea;
		this.nroVuelo = nroVuelo;
		this.avion = avion;
		this.fechaSalida = fechaSalida;
		this.fechaArribo = fechaArribo;
	}
	
	public String getAerolinea() {
	
		return this.aerolinea;
	}
	
	public String getAvion() {
	
		return this.avion;
	}
	
	public Date getFechaArribo() {
	
		return this.fechaArribo;
	}
	
	public Date getFechaSalida() {
	
		return this.fechaSalida;
	}
	
	public String getNroVuelo() {
	
		return this.nroVuelo;
	}
	
	public void setAerolinea(final String aerolinea) {
	
		this.aerolinea = aerolinea;
	}
	
	public void setAvion(final String avion) {
	
		this.avion = avion;
	}
	
	public void setFechaArribo(final Date fechaArribo) {
	
		this.fechaArribo = fechaArribo;
	}
	
	public void setFechaSalida(final Date fechaSalida) {
	
		this.fechaSalida = fechaSalida;
	}
	
	public void setNroVuelo(final String nroVuelo) {
	
		this.nroVuelo = nroVuelo;
	}
	
	@Override
	public String toString() {
	
		return "Vuelo " + this.getNroVuelo() + " de " + this.getAerolinea() +
		    " - Desde: " + this.getFechaSalida() + " hasta " +
		    this.getFechaArribo();
	}
	
}
