
package api.rest;


import integracion.despegar.TripOption;
import apis.TripOptionsProvider;
import integracion.despegar.TripOptions;


import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Path("/trip-options")
public class TripOptionsResource {
	
	private final TripOptionsProvider provider;
	
	@Inject
	public TripOptionsResource(final TripOptionsProvider provider) {
	
		this.provider = provider;
	}
	
	@GET
	@Produces("application/json")
	public
	    TripOptions
	    findTripOptions(
	        @NotNull @QueryParam("fromCity") final String fromCity,
	        @NotNull @QueryParam("toCity") final String toCity,
	        @NotNull @QueryParam("startDate") final String startDate,
	        @NotNull @QueryParam("endDate") final String endDate,
	        @NotNull @DefaultValue(value = "0") @QueryParam("offset") final int offset,
	        @NotNull @DefaultValue(value = "1") @QueryParam("limit") final int limit)
	        throws Exception {
	
		// tener en cuenta el formato en la UI por el momento
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		return this.provider.findTripOptions(fromCity, toCity,
		    fmt.parseDateTime(startDate), fmt.parseDateTime(endDate), offset,
		    limit);
		
	}
}
