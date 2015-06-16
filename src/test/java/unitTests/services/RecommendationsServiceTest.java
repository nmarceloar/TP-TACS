/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ValidationException;
import model.Recommendation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import repository.RecommendationDAO;
import services.PersistenceService;

/**
 *
 * @author flpitu88
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
//@ContextConfiguration(classes = SpringConfig.class)

public class RecommendationsServiceTest {

//    @Autowired
//    RecommendationAPI recServ;
    @InjectMocks
    private RecommendationAPI recServ = new PersistenceService();

    @Mock
    private RecommendationDAO dao;

    @Before
    public void prepare() throws ServiceException, ValidationException {
        Recommendation rec1 = new Recommendation("10153253398579452",
                "10206028316763565", "Martin De Ciervo",
                "Groenlandia", "Butan", 1);
        List<Recommendation> listaRecomendaciones = new ArrayList<>();
        listaRecomendaciones.add(rec1);

        // getTodosLosUsuarios
        when(dao.getRecomendacionPorId(1)).thenReturn(listaRecomendaciones.get(0));
        when(dao.getRecomendacionesDeUsuarioPorId("10153253398579452"))
                .thenReturn(listaRecomendaciones);
    }

    @Test
    public void getRecommendationsOfUserTest() {
        List<Recommendation> lista = recServ.getRecommendationsOfUser("10153253398579452");
//        org.junit.Assert.assertEquals(2, lista.size());
//        org.junit.Assert.assertEquals(1, lista.get(0).getIdRecomendacion());
//        org.junit.Assert.assertEquals("10153253398579452", lista.get(0).getIdUsuarioRecom());
//        org.junit.Assert.assertEquals("10206028316763565", lista.get(0).getIdUserFromRecom());
//        org.junit.Assert.assertEquals("Buenos Aires", lista.get(0).getCiudadOrig());
//        org.junit.Assert.assertEquals("Roma", lista.get(0).getCiudadDest());
        Assert.assertEquals(1, lista.size());
    }

    @Ignore
    @Test
    public void getRecommendatieonToStringTest() {
        org.junit.Assert.assertEquals("Martin De Ciervo te recomienda viajar de Buenos Aires a Roma",
                recServ.getRecommendationToString(1));
    }

    @Ignore
    @Test
    public void getRecommendationByIdTest() {
        org.junit.Assert.assertEquals(1, recServ.getRecommendationById(1).getIdRecomendacion());
    }

    @Ignore
    @Test
    public void asignarPasajeroARecomendacionTest() {
        Recommendation rec = new Recommendation("10153253398579452", "User2",
                null, "Sao Paulo", "Viena", 1);
        org.junit.Assert.assertNull(rec.getNombreYAp());
        recServ.asignarPasajeroARecomendacion(rec, "10153253398579452");
        org.junit.Assert.assertEquals("Flavio Pietrolati", rec.getNombreYAp());
    }

    @Ignore
    @Test
    public void assignStateRecommendationTest() {
        Recommendation rec = recServ.getRecommendationById(1);
        org.junit.Assert.assertEquals(0, rec.getEstado());
        recServ.assignStateRecommendation(1, "acp");
        org.junit.Assert.assertEquals(1, rec.getEstado());
        recServ.assignStateRecommendation(1, "rej");
        org.junit.Assert.assertEquals(-1, rec.getEstado());
    }

}
