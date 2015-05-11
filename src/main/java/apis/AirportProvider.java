
package apis;

import integracion.despegar.Airport;

public interface AirportProvider {
	
	public Airport findByIataCode(final String Code);
	
}
