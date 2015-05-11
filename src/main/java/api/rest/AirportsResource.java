
package api.rest;

import integracion.despegar.Airport;
import apis.AirportProvider;
import integracion.despegar.IATACode;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/airports")
public class AirportsResource {
	
	private final AirportProvider provider;
	
	@Inject
	public AirportsResource(final AirportProvider provider) {
	
		this.provider = provider;
		
	}
	
	@GET
	@Produces("application/json")
	public Airport getAirportByIATACode(@NotNull
	@QueryParam("code")
	final String iataCode) {
	
		try {
			
			IATACode.checkValid(iataCode);
			
		} catch (RuntimeException ex) {
			
			throw new BadRequestException(
			    "Error en el formato del codigo. ---> [code = ]" + iataCode);
			
		}
		
		return this.provider.findByIataCode(iataCode);
		
	}
	
}
