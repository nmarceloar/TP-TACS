/**
 * 
 */

package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StaticAeropuertosProvider implements AeropuertosProvider {
	
	private static Aeropuertos aeropuertos;
	
	static {
		
		final InputStream is =
		    StaticAeropuertosProvider.class.getClassLoader()
		        .getResourceAsStream("aeropuertos.final.json");
		
		final ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			StaticAeropuertosProvider.aeropuertos =
			    mapper.readValue(is, Aeropuertos.class);
			
		} catch (JsonParseException ex) {
			
			ex.printStackTrace();
			
		} catch (JsonMappingException ex) {
			
			ex.printStackTrace();
			
		} catch (IOException ex) {
			
			ex.printStackTrace();
			
		}
		
		try {
			
			is.close();
			
		} catch (IOException ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	@Override
	public List<Aeropuerto> findAeropuertosByCity(final String cityName) {
	
		final List<Aeropuerto> byName = new ArrayList<Aeropuerto>();
		
		if (cityName.isEmpty()) {
			
			// ojo con esto -- pueden ser muchos
			// restringir a busquedas de 3 o 4 caracteres como minimo
			return StaticAeropuertosProvider.aeropuertos.getAeropuertos();
			
		}
		
		for (final Aeropuerto aeropuerto : StaticAeropuertosProvider.aeropuertos.getAeropuertos()) {
			
			if (aeropuerto.cityNameLike(cityName)) {
				
				byName.add(aeropuerto);
				
			}
			
		}
		
		return byName;
		
	}
	
	public List<Aeropuerto> getAll() {
	
		return StaticAeropuertosProvider.aeropuertos.getAeropuertos();
		
	}
	
	/**
	 * @return
	 */
	public boolean hasAeropuertos() {
	
		return (this.getAll().size() > 0);
	}
	
}
