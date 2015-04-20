/**
 * 
 */

package api;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author nmarcelo.ar
 *
 */
public class StaticAeropuertosProvider implements AeropuertosProvider {
	
	private static Aeropuertos aeropuertos;
	
	static {
		
		final ObjectMapper mapper = new ObjectMapper();
		
		final URL mockData;
		final InputStream is;
		try {
			
			mockData =
			    new URL(
			        "https://raw.githubusercontent.com/nmarceloar/mockdata/master/aeropuertos.final.json");
			
			is = mockData.openStream();
			StaticAeropuertosProvider.aeropuertos = mapper
			                .readValue(is, Aeropuertos.class);
			
			is.close();
			
		} catch (final Exception ex) {
			
			Logger.getLogger("StaticAeropuertosProvider").info(
			    "Fallo durante la init estatica" + ex
			                    .getMessage());
			
		}
		
	}
	
	@Override
	public List<Aeropuerto> findAeropuertosByCity(final String cityName) {
	
		final List<Aeropuerto> byName = new ArrayList<Aeropuerto>();
		
		if (cityName.isEmpty()) {
			
			// ojo con esto -- pueden ser muchos
			// restringir a busquedas de 3 o 4 caracteres como minimo
			return StaticAeropuertosProvider.aeropuertos
			                .getAeropuertos();
			
		}
		
		for (final Aeropuerto aeropuerto : StaticAeropuertosProvider.aeropuertos
		                .getAeropuertos()) {
			
			if (aeropuerto.cityNameLike(cityName)) {
				
				byName.add(aeropuerto);
				
			}
			
		}
		
		return byName;
		
	}
	
	public List<Aeropuerto> getAll() {
	
		return StaticAeropuertosProvider.aeropuertos
		                .getAeropuertos();
		
	}
	
	/**
	 * @return
	 */
	public boolean hasAeropuertos() {
	
		return (this.getAll().size() > 0);
	}
	
}
