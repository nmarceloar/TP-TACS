
package api.rest;

import integracion.despegar.Airport;
import integracion.despegar.AirportProvider;
import integracion.despegar.IATACode;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

@Path("/airports")
public class AirportsResource {
	
	private final AirportProvider provider;
	
	@Inject
	public AirportsResource(final AirportProvider provider) {
	
		this.provider = provider;
		
	}
	
	@GET
	@Produces("application/json")
	public
	    List<Airport>
	    getAirportByIATACode(
	        @NotNull @QueryParam("code") @Size(min = 1) final List<String> iataCodes)
	        throws Exception {
	
		if (iataCodes == null || iataCodes.isEmpty()) {
			
			throw new BadRequestException("Error en el formato de la consulta");
			
		}
		
		return this.provider.findByIataCode(iataCodes);
		
	}
	
}
