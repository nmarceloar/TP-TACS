


package apis;

import java.util.List;

import services.City;

public interface CitiesService {
    
    public List<City> findByName(final String name);
    
}
