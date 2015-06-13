/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import config.SpringConfig;
import java.util.List;
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
        org.junit.Assert.assertEquals(1, lista.size());
        org.junit.Assert.assertEquals(1, lista.get(0).getIdRecomendacion());
        org.junit.Assert.assertEquals("10153253398579452", lista.get(0).getIdUsuarioRecom());
        org.junit.Assert.assertEquals("10206028316763565", lista.get(0).getIdUserFromRecom());
        org.junit.Assert.assertEquals("Buenos Aires", lista.get(0).getCiudadOrig());
        org.junit.Assert.assertEquals("Roma", lista.get(0).getCiudadDest());
    }

    @Test
    public void getRecommendatieonToStringTest() {
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
        recServ.asignarPasajeroARecomendacion(rec, "10153253398579452");
        org.junit.Assert.assertEquals("Flavio Pietrolati", rec.getNombreYAp());
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

}
