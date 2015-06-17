


package services;

import integracion.despegar.TripOptions;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import apis.TripOptionsService;

@Named
@Singleton
public class TripOptionsServiceImpl implements TripOptionsService {
    
    private static final String ITINERARIES =
        "https://api.despegar.com/v3/flights/itineraries";
    
    private Client despegarClient = DespegarClient.getInstance();
    
    @Override
    public TripOptions findTripOptions(String fromCity,
        String toCity,
        DateTime startDate,
        DateTime endDate,
        int offset,
        int limit) {
    
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        
        final WebTarget target = this.despegarClient.target(ITINERARIES)
            .queryParam("site", "ar")
            .queryParam("from", fromCity)
            .queryParam("to", toCity)
            .queryParam("departure_date", fmt.print(startDate))
            .queryParam("return_date", fmt.print(endDate))
            .queryParam("adults", "1")
            .queryParam("offset", offset)
            .queryParam("limit", limit);
        
        return target.request(MediaType.APPLICATION_JSON)
            .get(TripOptions.class);
        
    }
    
}
