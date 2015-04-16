/**
 * clase test simple para ir probando.. no tiene ningun valor. se accede con un
 * GET a http://localhost:8080/api/vuelos y un GET a
 * http://localhost:8080/api/vuelos/json
 */

package api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Vuelo;


@Path("/vuelos")
public class Vuelos {
	
	private static List<Vuelo> vuelosMock;
	
	static {
		
		vuelosMock = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			
			vuelosMock.add(new Vuelo("Vuelo" + i));
			
		}
		
	}
	
	@GET
	@Produces("text/plain")
	public String getVuelosAsString() {
	
		StringBuilder vuelos = new StringBuilder();
		
		for (Vuelo vuelo : vuelosMock) {
			
			vuelos.append(vuelo);
			
		}
		
		return vuelos.toString();
		
		
	}
	
}
