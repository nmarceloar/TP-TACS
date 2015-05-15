/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import java.util.List;
import model.Passenger;
import model.Recommendation;

/**
 *
 * @author Flavio L. Pietrolati
 */
public interface RecommendationAPI {
    
    public List<Recommendation> getRecommendationsOfUser(int id);
    
    public String getRecommendationToString(int id);
    
    public Recommendation getRecommendationById(int id);
    
    public void saveRecommendation(Recommendation rec);
    
    public void asignarPasajeroARecomendaciones(List<Recommendation> list, Integer pass);
    
    public void asignarPasajeroARecomendacion(Recommendation rec, Integer pass);
    
}
