


package api.rest;

import integracion.despegar.IATACode;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.Airport;
import services.DespegarClient;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Named
@Singleton
public class AirportsServiceImpl implements AirportsService {
    
    private static final String AUTOCOMPLETE =
        "https://api.despegar.com/v3/autocomplete";
    
    private Client despegarClient = DespegarClient.getInstance();
    
    private LoadingCache<String, Airport> cache = CacheBuilder.newBuilder()
        .maximumSize(50)
        .build(new CacheLoader<String, Airport>() {
            
            public Airport load(String code) throws Exception {
            
                return findws(code);
                
            }
        });
    
    @Override
    public Airport findByCode(final String iataCode) {
    
        try {
            
            return cache.get(iataCode);
            
        } catch (ExecutionException ex) {
            
            throw new RuntimeException("Error en el servidor.\n" +
                ex.getMessage());
            
        }
        
    }
    
    private Airport findws(final String iataCode) {
    
        IATACode.checkValid(iataCode);
        
        Response response = this.despegarClient.target(AUTOCOMPLETE)
            .queryParam("query", iataCode)
            .queryParam("locale", "es_AR")
            .queryParam("airport_result", "10")
            .request(MediaType.APPLICATION_JSON)
            .get();
        
        if ((response.getStatus() == 200) && response.hasEntity()) {
            
            for (integracion.despegar.Airport airport : response.readEntity(new GenericType<List<integracion.despegar.Airport>>() {})) {
                
                if (airport.code.equals(iataCode)) {
                    
                    return new Airport(airport.code, airport.description,
                        airport.geolocation.latitude,
                        airport.geolocation.longitude);
                    
                }
                
            }
            
        }
        
        throw new RuntimeException("Error en la busqueda de aeropuertos");
    }
    
}
