/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import config.SpringConfig;
import integracion.facebook.RecommendationBeanFB;
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
public class InstanceAndSaveRecommendationServiceTest {
    
    @Autowired
    RecommendationAPI recServ;
    
    @Test
    public void instanceAndSaveRecommendationTest() {
        int tam = recServ.getRecommendationsOfUser("10153253398579452").size();
        RecommendationBeanFB recB = new RecommendationBeanFB("10206028316763565", 1);
        recServ.instanceAndSaveRecommendation(recB, "10153253398579452");
        org.junit.Assert.assertEquals(tam + 1,
                recServ.getRecommendationsOfUser("10153253398579452").size());
    }
    
}
