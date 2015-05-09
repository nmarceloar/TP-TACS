
package api.rest;

import integracion.despegar.Airport;
import integracion.despegar.AirportProvider;
import integracion.despegar.IATACode;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
	
		return this.provider.findByIataCode(IATACode.checkValid(iataCode));
		
	}
	
}
