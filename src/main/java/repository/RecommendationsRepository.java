package repository;

import java.util.List;

import model2.Recommendation;
import model2.User;
import model2.impl.OfyRecommendation;

public interface RecommendationsRepository {

	public Recommendation add(Recommendation recommendation);

	public boolean exists(String id);

	public List<? extends Recommendation> findAll();

	public Recommendation findById(String id);

	public List<? extends Recommendation> findByOwner(User owner);

	public List<? extends Recommendation> findByOwnerAndStatus(User owner,
		OfyRecommendation.Status status);

	public List<? extends Recommendation> findByTargetAndStatus(User target,
		Recommendation.Status status);

	public void removeAll();

}