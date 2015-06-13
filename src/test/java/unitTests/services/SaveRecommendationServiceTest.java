/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.RecommendationAPI;
import config.SpringConfig;
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
public class SaveRecommendationServiceTest {

    @Autowired
    RecommendationAPI recServ;
    
    @Test
    public void saveRecommendationTest() {
        org.junit.Assert.assertEquals(1, recServ
                .getRecommendationsOfUser("10153253398579452").size());
        recServ.saveRecommendation(new Recommendation("10153253398579452", "123",
                "Luis Lopez", "Budapest", "Estambul", 2));
        org.junit.Assert.assertEquals(2, recServ
                .getRecommendationsOfUser("10153253398579452").size());
    }
}
