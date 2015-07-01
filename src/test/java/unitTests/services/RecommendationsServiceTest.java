/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import integracion.facebook.RecommendationBeanFB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ValidationException;
import model.Passenger;
import model.Recommendation;
import model.Segment;
import model.Trip;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import repository.PassengerDAO;
import repository.RecommendationDAO;
import repository.TripsDAO;
import services.PersistenceService;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
public class RecommendationsServiceTest {

    @InjectMocks
    private RecommendationAPI recServ = new PersistenceService();

    @Mock
    private RecommendationDAO rDao;

    @Mock
    private PassengerDAO pDao;

    @Mock
    private TripsDAO tDao;

    @Before
    public void prepare() throws ServiceException, ValidationException {

        final List<Recommendation> listaRec = new ArrayList<>();
        Recommendation rec1 = new Recommendation(1, "10153253398579452", "10206028316763565", "Martin De Ciervo", "Buenos Aires", "Roma", 1);
        Recommendation rec2 = new Recommendation(2, "10206028316763565", "10153253398579452", "Flavio Pietrolati", "Amsterdam", "Bruselas", 4);
        Recommendation rec3 = new Recommendation(3, "10206727743494683", "10153253398579452", "Claudio Yuri", "Buenos Aires", "Salta", 2);

        Passenger pMartin = new Passenger("10206028316763565", "Martin", "De Ciervo", "11", new ArrayList());
        Passenger pFlavio = new Passenger("10153253398579452", "Flavio", "Pietrolati", "22", new ArrayList());

        Segment seg1 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "LAN", "A01");

        Trip viaje1 = new Trip("10153253398579452", "Buenos Aires", "San Pablo", "4500 ARS", Arrays.asList(seg1));

        // Configuracion del mock
        when(rDao.getRecomendacionPorId(1)).thenReturn(rec1);
        when(rDao.getRecomendacionesDeUsuarioPorId("10153253398579452"))
                .thenReturn(Arrays.asList(rec1));
        when(pDao.getPasajeroById("10206028316763565")).thenReturn(pMartin);
        when(tDao.searchTripById(1)).thenReturn(viaje1);
        when(rDao.getRecomendaciones()).thenReturn(listaRec);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Recommendation rec = (Recommendation) args[0];
                listaRec.add(rec);
                return null;
            }
        }).when(rDao).saveRecommendation(any(Recommendation.class));

    }

    @Test
    public void getRecommendationsOfUserTest() {
        List<Recommendation> lista = recServ.getRecommendationsOfUser("10153253398579452");
        org.junit.Assert.assertEquals(1, lista.size());
        org.junit.Assert.assertEquals(1, lista.get(0).getIdRecomendacion());
    }

    @Ignore
    @Test
    public void getRecommendationToStringTest() {
        org.junit.Assert.assertEquals("Martin De Ciervo te recomienda viajar de Buenos Aires a Roma",
                recServ.getRecommendationToString(1));
    }

    @Test
    public void getRecommendationByIdTest() {
        org.junit.Assert.assertEquals(1, recServ.getRecommendationById(1).getIdRecomendacion());
    }

    @Test
    public void asignarPasajeroARecomendacionTest() {
        Recommendation rec = new Recommendation("10153253398579452", "User2",
                null, "Sao Paulo", "Viena", 1);
        org.junit.Assert.assertNull(rec.getNombreYAp());
        recServ.asignarPasajeroARecomendacion(rec, "10206028316763565");
        org.junit.Assert.assertEquals("Martin De Ciervo", rec.getNombreYAp());
    }

    @Test
    public void assignStateRecommendationTest() {
        Recommendation rec = recServ.getRecommendationById(1);
        org.junit.Assert.assertEquals(0, rec.getEstado());
        recServ.assignStateRecommendation(1, "acp");
        org.junit.Assert.assertEquals(1, rec.getEstado());
        recServ.assignStateRecommendation(1, "rej");
        org.junit.Assert.assertEquals(-1, rec.getEstado());
    }

    @Test
    public void saveRecommendationTest() {
        Assert.assertEquals(0, recServ.getRecommendations().size());
        recServ.saveRecommendation(new Recommendation(4, "10153253398579452", "123",
                "Luis Lopez", "Budapest", "Estambul", 2));
        Assert.assertEquals(1, recServ.getRecommendations().size());
    }

    @Ignore
    @Test
    public void instanceAndSaveRecommendationTest() {
        int tam = recServ.getRecommendationsOfUser("10153253398579452").size();
        RecommendationBeanFB recB = new RecommendationBeanFB("10206028316763565", 1);
        recServ.instanceAndSaveRecommendation(recB, "10153253398579452");
        org.junit.Assert.assertEquals(tam + 1,
                recServ.getRecommendationsOfUser("10153253398579452").size());
    }
    
}
