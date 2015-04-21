
package api;

import integracion.despegar.Despegar;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Path("/viajes")
public class ViajesResource {
	
	private ViajesProvider proveedorDeViajes;
	
	public ViajesResource() {
	
		this.proveedorDeViajes = new Despegar();
	}
	
	@GET
	@Produces("application/json")
	public List<OpcionDeViaje> findOpcionesDeViaje(
	    @QueryParam("aeroOrigen") String aeroOrigen, // iata code refactor
	    @QueryParam("aeroDestino") String aeroDestino, // iata code refactor
	    @QueryParam("fechaIda") String fechaIda, // refactor a date time
	    @QueryParam("fechaVuelta") String fechaVuelta // refactor a datetime
	    ) {
	
		// tener en cuenta el formato en la UI por el momento
		DateTimeFormatter fmt = DateTimeFormat
		                .forPattern("dd/MM/yyyy");
		
		return this.proveedorDeViajes
		                .findOpcionesDeViaje(
		                    aeroOrigen,
		                    aeroDestino,
		                    fmt.parseDateTime(fechaIda),
		                    fmt.parseDateTime(fechaVuelta));
		
	}
	
}
