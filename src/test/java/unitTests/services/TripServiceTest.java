/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import model2.impl.OfyTrip;
import model2.impl.OfyUser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import services.OfyTripsService;
import services.impl.OfyTripsServiceImpl;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;

/**
 *
 * @author flpitu88
 */
public class TripServiceTest {

	private OfyUsersRepository userRepo;
	private OfyTripsRepository tripRepo;
	private OfyRecommendationsRepository recommendationRepo;

	private OfyTripsService trpSrv;

	@Before
	public void setUp() {

		userRepo = Mockito.mock(OfyUsersRepository.class);
		tripRepo = Mockito.mock(OfyTripsRepository.class);
		recommendationRepo = Mockito.mock(OfyRecommendationsRepository.class);

		trpSrv = new OfyTripsServiceImpl(userRepo,
			tripRepo,
			recommendationRepo);

		OfyUser user1 = OfyUser.createFrom(1L,
			"Ejemplo",
			"url",
			"mail@test.com");

		OfyUser user2 = OfyUser.createFrom(2L,
			"Ejemplo",
			"url",
			"mail@test.com");

		OfyUser user3 = OfyUser.createFrom(3L,
			"Ejemplo",
			"url",
			"mail@test.com");

		City ciudadDe = new City("BUE", "Buenos Aires", 100, 100);
		City ciudadHasta = new City("ROM", "Roma", 100, 100);
		PriceDetail precio = new PriceDetail("ARS", 150);
		Segment segmento = new Segment(null,
			null,
			null,
			null,
			null,
			null,
			null);
		TripDetails detalles = new TripDetails(ciudadDe,
			ciudadHasta,
			precio,
			Arrays.asList(segmento),
			Arrays.asList(segmento));

		final OfyTrip viaje = OfyTrip.createFrom(user1, detalles);

		final List<OfyTrip> lista1 = Arrays.asList(viaje);
		final List<OfyTrip> lista2 = Arrays.asList(viaje);
		final List<OfyTrip> lista3 = Arrays.asList(viaje);

		when(tripRepo.findById("1")).thenReturn(viaje);
		when(tripRepo.findAll()).thenReturn(lista1);
		when(userRepo.findById(1)).thenReturn(user1);
		when(userRepo.findById(2)).thenReturn(user2);
		when(userRepo.findById(3)).thenReturn(user3);
		when(tripRepo.findByOwner(user1)).thenReturn(lista1);
		when(tripRepo.findByOwner(user2)).thenReturn(lista2);
		when(tripRepo.findByOwner(user3)).thenReturn(lista3);

		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				System.out.println("Elimino el elemento");
				lista2.clear();
				return null;
			}
		}).when(tripRepo).removeAll();

		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				System.out.println("Elimino el elemento");
				lista3.clear();
				return null;
			}
		}).when(tripRepo).deleteById("1");

	}

	@After
	public void tearDown() {

		this.userRepo = null;
		this.tripRepo = null;
		this.recommendationRepo = null;

		this.trpSrv = null;

	}

	@Test
	@Ignore
	public void createTripTest() {
		// SIN POSIBILIDAD DE TESTEAR
	}

	@Test
	@Ignore
	public void findAcceptedByTargetTest() {
		// SIN POSIBILIDAD DE TESTEAR
	}

	@Test
	public void findAllTest() {

		List<OfyTrip> listado = trpSrv.findAll();

		Assert.assertEquals(1, listado.size());

		Assert.assertEquals("BUE", listado.get(0)
			.getTripDetails()
			.getFromCity()
			.getCode());

		Assert.assertEquals("ROM", listado.get(0)
			.getTripDetails()
			.getToCity()
			.getCode());
	}

	@Test
	public void findByOwnerTest() {

		List<OfyTrip> listado = trpSrv.findByOwner(1);
		Assert.assertEquals(1, listado.size());
		Assert.assertEquals("BUE", listado.get(0)
			.getTripDetails()
			.getFromCity()
			.getCode());

		Assert.assertEquals("ROM", listado.get(0)
			.getTripDetails()
			.getToCity()
			.getCode());
	}

	@Test
	public void removeAllTest() {
		Assert.assertEquals(1, tripRepo.findByOwner(userRepo.findById(2))
			.size());
		trpSrv.removeAll();
		Assert.assertTrue(tripRepo.findByOwner(userRepo.findById(2))
			.isEmpty());
	}

	@Test
	public void findByIdTest() {

		Assert.assertEquals("BUE", trpSrv.findById("1")
			.getTripDetails()
			.getFromCity()
			.getCode());

		Assert.assertEquals("ROM", trpSrv.findById("1")
			.getTripDetails()
			.getToCity()
			.getCode());
	}

	@Test
	public void deleteByIdTest() {

		Assert.assertEquals(1, tripRepo.findByOwner(userRepo.findById(3))
			.size());

		trpSrv.deleteById("1");

		Assert.assertTrue(tripRepo.findByOwner(userRepo.findById(3))
			.isEmpty());
	}

}
