
package integracion.despegar;

import java.util.List;

public interface CityProvider {
	
	public List<City> findByName(String name);
	
}
