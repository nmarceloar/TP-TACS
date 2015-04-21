
package api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/aeropuertos")
public class AeropuertosResource {
	
	private AeropuertosProvider aeropuertosProvider;
	
	public AeropuertosResource() {
	
		// refactorizar con inyeccion de dependencias
		this.aeropuertosProvider = new StaticAeropuertosProvider();
		
	}
	
	
	
	@GET
	@Produces("application/json")
	public List<Aeropuerto> findAeropuertosByCityName(
	    @QueryParam("cityName") String cityName) {
	
		return this.aeropuertosProvider
		                .findAeropuertosByCity(cityName);
		
	}
	
}
