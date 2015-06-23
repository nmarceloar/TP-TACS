package api.rest.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import services.TripOptionsDescriptorService;
import services.TripOptionsDescriptorServiceImpl;

@Path("/search/trip-options")
@RequestScoped
public class TripOptionsDescriptorResource {

	private TripOptionsDescriptorService tods = TripOptionsDescriptorServiceImpl.getInstance();

	@GET
	public Response findOptions(
			@NotNull @QueryParam("fromCity") final String fromCity,
			@NotNull @QueryParam("toCity") final String toCity,
			@NotNull @QueryParam("startDate") final String startDate,
			@NotNull @QueryParam("endDate") final String endDate,
			@NotNull @QueryParam("offset") @DefaultValue("0") final Integer offset,
			@NotNull @QueryParam("limit") @DefaultValue("1") final Integer limit) {

		// tener en cuenta el formato en la UI por el momento
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity(tods.findTripOptions(fromCity, toCity,
					fmt.parseDateTime(startDate), fmt.parseDateTime(endDate),
					offset, limit))
			.build();

	}
}
