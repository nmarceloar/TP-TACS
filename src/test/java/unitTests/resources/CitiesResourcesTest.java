/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.resources;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import services.CitiesService;
import api.rest.resources.CitiesResource;
import api.rest.views.City;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class CitiesResourcesTest {

	@Mock
	private CitiesService citiesService;

	@InjectMocks
	private CitiesResource cityRes = new CitiesResource();

	@Before
	public void prepare() throws ServiceException, ValidationException {
		List<City> lista = Arrays.asList(new City("BUE",
			"Buenos Aires",
			100,
			100), new City("ROM", "Roma", 200, 200));
		when(citiesService.findByName("Bue")).thenReturn(lista);
	}

	@Test
	public void getByCityNameTest() {
		List<City> ciudades = cityRes.getByCityName("Bue");
		Assert.assertEquals("Buenos Aires", ciudades.get(0).getName());
		Assert.assertEquals("Roma", ciudades.get(1).getName());
	}

}
