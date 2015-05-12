
package apis;

import integracion.despegar.City;
import java.util.List;

public interface CityProvider {
	
	public List<City> findByName(String name);
	
}
