/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ValidationException;
import junit.framework.Assert;
import model2.Recommendation;
import model2.Trip;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import repository.RecommendationsRepository;
import repository.TripsRepository;
import repository.UsersRepository;
import services.TripsService;
import services.impl.OfyTripService;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    @InjectMocks
    private TripsService trpSrv = new OfyTripService();

    @Mock
    private UsersRepository userRepo;

    @Mock
    private TripsRepository tripRepo;

    @Mock
    private RecommendationsRepository recommendationRepo;

    @Before
    public void prepare() throws ServiceException, ValidationException {
        City ciudadDe = new City("BUE", "Buenos Aires", 100, 100);
        City ciudadHasta = new City("ROM", "Roma", 100, 100);
        PriceDetail precio = new PriceDetail("ARS", 150);
        Segment segmento = new Segment(null, null, null, null, null, null, null);
        TripDetails detalles = new TripDetails(ciudadDe, ciudadHasta, precio,
                Arrays.asList(segmento), Arrays.asList(segmento));

        final OfyTrip viaje = new OfyTrip();
        viaje.setTripDetails(detalles);

        List<OfyTrip> lista = Arrays.asList(viaje);
        final List<OfyTrip> lista2 = new ArrayList<>();
        lista2.add(viaje);
        final List<OfyTrip> lista3 = new ArrayList<>();
        lista3.add(viaje);

        OfyUser user = new OfyUser(1, "Ejemplo", "url", "mail@test.com");
        OfyUser user2 = new OfyUser(2, "Ejemplo", "url", "mail@test.com");
        OfyUser user3 = new OfyUser(3, "Ejemplo", "url", "mail@test.com");

        when(tripRepo.findById("1")).thenReturn(viaje);
        when(tripRepo.findAll()).thenReturn(lista);
        when(userRepo.findById(1)).thenReturn(user);
        when(userRepo.findById(2)).thenReturn(user2);
        when(userRepo.findById(3)).thenReturn(user3);
        when(tripRepo.findByOwner(user)).thenReturn(lista);
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
        Assert.assertEquals("BUE", listado.get(0).getTripDetails()
                .getFromCity().getCode());
        Assert.assertEquals("ROM", listado.get(0).getTripDetails()
                .getToCity().getCode());
    }

    @Test
    public void findByOwnerTest() {
        List<OfyTrip> listado = trpSrv.findByOwner(1);
        Assert.assertEquals(1, listado.size());
        Assert.assertEquals("BUE", listado.get(0).getTripDetails()
                .getFromCity().getCode());
        Assert.assertEquals("ROM", listado.get(0).getTripDetails()
                .getToCity().getCode());
    }

    @Test
    public void removeAllTest() {
        Assert.assertEquals(1, tripRepo
                .findByOwner(userRepo.findById(2)).size());
        trpSrv.removeAll();
        Assert.assertTrue(tripRepo
                .findByOwner(userRepo.findById(2)).isEmpty());
    }

    @Test
    public void findByIdTest() {
        Assert.assertEquals("BUE", trpSrv.findById("1")
                .getTripDetails().getFromCity().getCode());
        Assert.assertEquals("ROM", trpSrv.findById("1")
                .getTripDetails().getToCity().getCode());
    }

    @Test
    public void deleteByIdTest() {
        Assert.assertEquals(1, tripRepo
                .findByOwner(userRepo.findById(3)).size());
        trpSrv.deleteById("1");
        Assert.assertTrue(tripRepo
                .findByOwner(userRepo.findById(3)).isEmpty());
    }

}
