
package api.rest;

import integracion.despegar.TripOption;
import apis.TripOptionsProvider;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
	public List<TripOption> findTripOptions(@NotNull
	@QueryParam("fromCity")
	final String fromCity, @NotNull
	@QueryParam("toCity")
	final String toCity, @NotNull
	@QueryParam("startDate")
	final String startDate, @NotNull
	@QueryParam("endDate")
	final String endDate) throws Exception {
	
		// tener en cuenta el formato en la UI por el momento
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		return this.provider.findTripOptions(fromCity, toCity,
		    fmt.parseDateTime(startDate), fmt.parseDateTime(endDate));
		
	}
	
}
