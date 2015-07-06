/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.resources;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import model2.Trip;
import model2.impl.OfyTrip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import services.OfyTripsService;
import api.rest.resources.FriendsTripsResource;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class FriendsTripsTest {

	@Mock
	private OfyTripsService tripsService;

	@InjectMocks
	private FriendsTripsResource frdRes = new FriendsTripsResource();

	@Before
	public void prepare() {

		List<? extends Trip> lista = new ArrayList<OfyTrip>();

		when(tripsService.findByOwner(1)).thenReturn(null);

	}

	@Test
	public void findTripsByOwnerTest() {

	}

}
