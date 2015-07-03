package services.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import services.CitiesService;
import services.DespegarClient;
import api.rest.views.City;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

public class CitiesServiceImpl implements CitiesService {

	private static final String AUTOCOMPLETE = "https://api.despegar.com/v3/autocomplete";
	private static final int MAX_CITIES = 10;

	private Client despegarClient;
	private LoadingCache<String, List<City>> cities;

	public CitiesServiceImpl() {

		despegarClient = DespegarClient.getInstance();

		cities = CacheBuilder.newBuilder()
			.maximumSize(20)
			.build(new CacheLoader<String, List<City>>() {

				@Override
				public List<City> load(String key) throws Exception {

					return CitiesServiceImpl.this.doFind(key);

				}
			});

	}

	private List<City> doFind(String name) {

		return Lists.transform(this.despegarClient.target(CitiesServiceImpl.AUTOCOMPLETE)
			.queryParam("query", name)
			.queryParam("locale", "es_AR")
			.queryParam("city_result", CitiesServiceImpl.MAX_CITIES)
			.request(MediaType.APPLICATION_JSON)
			.get()
			.readEntity(new GenericType<List<integracion.despegar.City>>() {
			}),
			new Function<integracion.despegar.City, City>() {

				@Override
				public City apply(integracion.despegar.City input) {

					return new City(input.code,
						input.description,
						input.geolocation.latitude,
						input.geolocation.longitude);

				}
			});

	}

	@Override
	public List<City> findByName(String name) {

		try {

			return this.cities.get(name);

		} catch (ExecutionException ex) {

			throw new RuntimeException("Error en el servidor.\n" + ex.getMessage());

		}

	}

}
