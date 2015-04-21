
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
	
	public Vuelo(String aerolinea, String nroVuelo, String avion,
	    Date fechaSalida, Date fechaArribo) {
	
		super();
		this.aerolinea = aerolinea;
		this.nroVuelo = nroVuelo;
		this.avion = avion;
		this.fechaSalida = fechaSalida;
		this.fechaArribo = fechaArribo;
	}
	
	public String getAerolinea() {
	
		return aerolinea;
	}
	
	public void setAerolinea(String aerolinea) {
	
		this.aerolinea = aerolinea;
	}
	
	public String getNroVuelo() {
	
		return nroVuelo;
	}
	
	public void setNroVuelo(String nroVuelo) {
	
		this.nroVuelo = nroVuelo;
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
	
	@Override
	public String toString() {
	
		return "Vuelo " + getNroVuelo() + " de " + getAerolinea()
		    + " - Desde: " + getFechaSalida() + " hasta "
		    + getFechaArribo();
	}
	
}
