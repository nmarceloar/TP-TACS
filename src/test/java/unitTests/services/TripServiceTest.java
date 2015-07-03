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
import java.util.Arrays;
import javax.validation.ValidationException;
import junit.framework.Assert;
import model2.Trip;
import model2.impl.OfyTrip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
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

        Trip viaje = new OfyTrip();
        viaje.setTripDetails(detalles);
        when(tripRepo.findById("1")).thenReturn(viaje);
    }

    @Test
    public void createTripTest() {

    }

    @Test
    public void findAcceptedByTargetTest() {

    }

    @Test
    public void findAllTest() {

    }

    @Test
    public void findByOwnerTest() {

    }

    @Test
    public void removeAllTest() {

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

    }

}
