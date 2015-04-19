/**
 * 
 */

package api;

import java.util.List;

/**
 * @author nmarcelo.ar
 *
 */
public interface AeropuertosProvider {
	
	public List<Aeropuerto> findAeropuertosByCity(
	    String cityName);
	
}
