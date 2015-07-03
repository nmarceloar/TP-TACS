package services;

import api.rest.views.Airport;

public interface AirportsService {

	public Airport findByCode(String iataCode3);

}
