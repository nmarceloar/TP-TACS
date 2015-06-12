/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import config.SpringConfig;
import java.util.List;
import junit.framework.Assert;
import model.Recommendation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author flpitu88
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class RecommendationsServiceTest {

    @Autowired
    RecommendationAPI recServ;

    @Test
    public void getRecommendationsOfUserTest() {
        List<Recommendation> lista = recServ.getRecommendationsOfUser("10153253398579452");
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(1, lista.get(0).getIdRecomendacion());
        Assert.assertEquals("10153253398579452", lista.get(0).getIdUsuarioRecom());
        Assert.assertEquals("10206028316763565", lista.get(0).getIdUserFromRecom());
        Assert.assertEquals("Buenos Aires", lista.get(0).getCiudadOrig());
        Assert.assertEquals("Roma", lista.get(0).getCiudadDest());
    }

    @Test
    public void getRecommendatieonToStringTest() {
        Assert.assertEquals("Martin De Ciervo te recomienda viajar de Buenos Aires a Roma",
                recServ.getRecommendationToString(1));
    }

    @Test
    public void getRecommendationByIdTest() {
        Assert.assertEquals(1, recServ.getRecommendationById(1).getIdRecomendacion());
    }

    @Test
    public void saveRecommendationTest() {

    }

    @Test
    public void asignarPasajeroARecomendacionesTest() {

    }

    @Test
    public void asignarPasajeroARecomendacionTest() {

    }

    @Test
    public void instanceAndSaveRecommendationTest() {

    }

    @Test
    public void assignStateRecommendationTest() {

    }

}
