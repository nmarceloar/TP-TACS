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
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Assert;
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
import services.RecommendationsService;
import services.impl.OfyRecommendationsService;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class RecommendationsServiceTest {

    @InjectMocks
    private RecommendationsService recSrv = new OfyRecommendationsService();

    @Mock
    private UsersRepository userRepo;

    @Mock
    private TripsRepository tripRepo;

    @Mock
    private RecommendationsRepository recommendationRepository;

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

        OfyUser user1 = new OfyUser(1, "Ejemplo1", "url1", "mail1@test.com");
        OfyUser user2 = new OfyUser(2, "Ejemplo2", "url2", "mail2@test.com");
        OfyUser user3 = new OfyUser(3, "Ejemplo3", "url3", "mail3@test.com");
        
        List<OfyRecommendation> lista = new ArrayList<>();
        
        when(recommendationRepository.findAll()).thenReturn(lista);
        when(userRepo.findById(1)).thenReturn(user1);
        when(userRepo.findById(2)).thenReturn(user2);
        when(userRepo.findById(3)).thenReturn(user3);
    }

    @Test
    public void createRecommendationTest() {

    }

    @Test
    public void findAllTest() {
        List<OfyRecommendation> lista = recSrv.findAll();
        Assert.assertEquals(3, lista.size());
        Assert.assertEquals(userRepo.findById(1),lista.get(0).getOwner());
        Assert.assertEquals(userRepo.findById(2),lista.get(1).getOwner());
        Assert.assertEquals(userRepo.findById(3),lista.get(2).getOwner());
    }

    @Test
    public void findByIdTest() {

    }

    @Test
    public void findByOwnerAndStatusTest() {

    }

    @Test
    public void findByTargetAndStatusTest() {

    }

    @Test
    public void pathRecommendationTest() {

    }

}
