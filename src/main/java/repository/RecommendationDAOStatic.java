/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Recommendation;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Repository
public class RecommendationDAOStatic implements RecommendationDAO {

    private final List<Recommendation> listaRecomendaciones;

    public RecommendationDAOStatic() {
        listaRecomendaciones = Arrays.asList( new Recommendation(10153253398579452L, "Flavio Pietrolati", "Buenos Aires", "Roma",1),
                new Recommendation(10206028316763565L, "Martin De Ciervo","Amsterdam", "Bruselas",4));
    }

    public List<Recommendation> getListaRecomendaciones() {
        return listaRecomendaciones;
    }

//    @Override
//    public List<Recommendation> getRecomendacionesPorId(List<Integer> recomendaciones) {
//        List<Recommendation> recomend = new ArrayList<>();
//        for (Integer rec : recomendaciones){
//            for (Recommendation recom : getListaRecomendaciones()){
//                if (recom.getIdRecomendacion() == rec){
//                    recomend.add(recom);
//                }
//            }
//        }
//        return recomend;
//    }

    @Override
    public Recommendation getRecomendacionPorId(Integer id) {
        for (Recommendation r : getListaRecomendaciones()){
            if (r.getIdRecomendacion() == id){
                return r;
            }
        }
        return null;
    }

    @Override
    public void saveRecommendation(Recommendation rec) {
        listaRecomendaciones.add(rec);
    }

    @Override
    public List<Recommendation> getRecomendacionesDeUsuarioPorId(long idUser) {
        List<Recommendation> recomend = new ArrayList<>();
        for (Recommendation rec : getListaRecomendaciones()){
            if (rec.getIdUsuarioRecom() == idUser){
                recomend.add(rec);
            }
        }
        return recomend;
    }

}
