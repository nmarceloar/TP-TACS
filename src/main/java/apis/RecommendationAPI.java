/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import java.util.List;
import model.Recommendation;

/**
 *
 * @author Flavio L. Pietrolati
 */
public interface RecommendationAPI {
    
    public List<Recommendation> getRecomendacionesDeUsario(int id);
    
    public String getRecommendationToString(int id);
}
