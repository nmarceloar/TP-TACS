/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.validation.ValidationException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import services.AirlinesService;
import services.AirportsService;
import services.TripOptionsDescriptorService;
import services.TripOptionsService;
import services.impl.TripOptionsDescriptorServiceImpl;
import api.rest.Airline;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class TripOptionsDescriptorServiceTest {

	@Mock
	private TripOptionsService tripOptionsService;

	@Mock
	private AirlinesService airlinesService;

	@Mock
	private AirportsService airportsService;

	private TripOptionsDescriptorService trpOpSrv = new TripOptionsDescriptorServiceImpl(tripOptionsService,
		airlinesService,
		airportsService);

	@Before
	public void prepare() throws ServiceException, ValidationException {
		Airline aerolinea = new Airline("AA",
			" ",
			"Prueba Aero",
			"Argentina");
		when(airlinesService.findByCode(any(String.class))).thenReturn(aerolinea);
	}

	@Test
	@Ignore
	public void findTripOptionsTest() {
		trpOpSrv.findTripOptions("BUE",
			"ROM",
			DateTime.now(),
			DateTime.now(),
			0,
			5);
		Assert.assertTrue(true);
	}

}
