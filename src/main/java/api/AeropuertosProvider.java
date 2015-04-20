
package api;

import java.util.List;

public interface AeropuertosProvider {
	
	public List<Aeropuerto> findAeropuertosByCity(
	    String cityName);
	
}
