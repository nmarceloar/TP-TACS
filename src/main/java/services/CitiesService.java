package services;

import java.util.List;

import api.rest.views.City;

public interface CitiesService {

	public List<City> findByName(final String name);

}
