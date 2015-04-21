
package integracion.geobytes;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.CiudadesProvider;

public class GeobytesCiudadesProvider implements CiudadesProvider {
	
	private final static String TARGET =
	    "http://gd.geobytes.com/AutoCompleteCity";
	
	private Client restClient;
	
	public GeobytesCiudadesProvider() {
	
		this.restClient = ClientBuilder.newClient();
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see api.CiudadesProvider#findCiudadesByName(java.lang.String)
	 */
	@Override
	public String findCiudadesByName(String cityName) {
	
		String ciudades = "[]";
		
		WebTarget webTarget =
		    restClient.target(TARGET).queryParam("q", cityName);
		
		Invocation.Builder invocationBuilder =
		    webTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.get();
		
		if (response.getStatus() == 200) {
			
			ciudades = response.readEntity(String.class);
			
		}
		
		return ciudades;
		
	}
	
}
