package model;

import java.util.Date;

public class Flight {

	private String airline;

	private String flightNumber;

	private String aircraft;

	private Date departureDate;

	private Date arrivalDate;

	public Flight() {

	}

	public Flight(String aerolinea, String nroVuelo, String avion,
			Date fechaSalida, Date fechaArribo) {

		super();
		this.airline = aerolinea;
		this.flightNumber = nroVuelo;
		this.aircraft = avion;
		this.departureDate = fechaSalida;
		this.arrivalDate = fechaArribo;
	}

	public String getAirline() {

		return airline;
	}

	public void setAirline(String airline) {

		this.airline = airline;
	}

	public String getFlightNumber() {

		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {

		this.flightNumber = flightNumber;
	}

	public String getAircraft() {

		return aircraft;
	}

	public void setAircraft(String aircraft) {

		this.aircraft = aircraft;
	}

	public Date getDepartureDate() {

		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {

		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {

		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {

		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {

		return "Vuelo " + getFlightNumber() + " de " + getAirline()
				+ " - Desde: " + getDepartureDate() + " hasta "
				+ getArrivalDate();
	}

}
