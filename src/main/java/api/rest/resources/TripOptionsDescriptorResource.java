package api.rest.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import services.TripOptionsDescriptorService;
import services.TripOptionsDescriptorServiceImpl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class JsonRequest {

	public String fromCity;
	public String toCity;
	public String startDate;
	public String endDate;
	public int offset;
	public int limit;

}

@Path("/trip-options")
@RequestScoped
public class TripOptionsDescriptorResource {

	private TripOptionsDescriptorService tods = new TripOptionsDescriptorServiceImpl();

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response findOptions(@NotNull JsonRequest despegarRequest) {

		// tener en cuenta el formato en la UI por el momento
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity(tods.findTripOptions(despegarRequest.fromCity,
					despegarRequest.toCity,
					fmt.parseDateTime(despegarRequest.startDate),
					fmt.parseDateTime(despegarRequest.endDate),
					despegarRequest.offset, despegarRequest.limit))
			.build();

	}
}
