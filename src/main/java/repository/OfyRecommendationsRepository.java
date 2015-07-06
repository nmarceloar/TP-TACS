package repository;

import java.util.List;

import model2.Recommendation;
import model2.impl.OfyRecommendation;
import model2.impl.OfyUser;

public interface OfyRecommendationsRepository {

	public OfyRecommendation add(OfyRecommendation recommendation);

	public boolean exists(String id);

	public List<OfyRecommendation> findAll();

	public OfyRecommendation findById(String id);

	public List<OfyRecommendation> findByOwner(OfyUser owner);

	public List<OfyRecommendation> findByOwnerAndStatus(OfyUser owner,
		OfyRecommendation.Status status);

	public List<OfyRecommendation> findByTargetAndStatus(OfyUser target,
		Recommendation.Status status);

	public void removeAll();

}