


package api.rest;

import services.Airport;

public interface AirportsService {
    
    public Airport findByCode(String iataCode3);
    
}
