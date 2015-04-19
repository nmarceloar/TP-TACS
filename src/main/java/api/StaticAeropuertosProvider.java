/**
 * 
 */

package api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author nmarcelo.ar
 *
 */
public class StaticAeropuertosProvider implements AeropuertosProvider {
	
	private static Aeropuertos aeropuertos = null;
	
	static {
		
		final ObjectMapper mapper = new ObjectMapper();
		
		try {
			aeropuertos =
			    mapper.readValue(new File("C:\\aeropuertos.final.json"),
			        Aeropuertos.class);
		} catch (JsonParseException ex) {
			ex.printStackTrace();
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
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
			return aeropuertos.getAeropuertos();
			
		}
		
		for (final Aeropuerto aeropuerto : aeropuertos.getAeropuertos()) {
			
			if (aeropuerto.cityNameLike(cityName)) {
				
				byName.add(aeropuerto);
				
			}
			
		}
		
		return byName;
		
	}
	
	public List<Aeropuerto> getAll() {
	
		return aeropuertos.getAeropuertos();
		
	}
	
	/**
	 * @return
	 */
	public boolean hasAeropuertos() {
	
		return (this.getAll().size() > 0);
	}
	
}
