/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

import model.Recommendation;

/**
 *
 * @author Flavio L. Pietrolati
 */
public interface RecommendationDAO {

	// public List<Recommendation> getRecomendacionesPorId(List<Integer>
	// recomendaciones);

	public Recommendation getRecomendacionPorId(Integer id);

	public void saveRecommendation(Recommendation rec);

	public List<Recommendation> getRecomendacionesDeUsuarioPorId(
		String idUser);

	public void deleteRecommendation(int id);

	public List<Recommendation> getRecomendaciones();

}
