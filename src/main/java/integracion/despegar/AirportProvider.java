
package integracion.despegar;

import java.util.List;

public interface AirportProvider {
	
	public List<Airport> findByIataCode(final List<String> iataCodes)
	    throws Exception;
	
}
