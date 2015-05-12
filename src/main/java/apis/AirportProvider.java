package apis;

import integracion.despegar.Airport;
import java.util.List;

public interface AirportProvider {

    public List<Airport> findByIataCode(final List<String> iataCodes)
            throws Exception;

}
