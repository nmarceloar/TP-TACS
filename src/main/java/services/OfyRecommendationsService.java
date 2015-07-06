package services;

import java.util.List;

import model2.Recommendation;
import model2.impl.OfyRecommendation;

public interface OfyRecommendationsService {

	public OfyRecommendation createRecommendation(long ownerId,
		long targetId, String tripId);

	public List<OfyRecommendation> findAll();

	public OfyRecommendation findById(String recId);

	public List<OfyRecommendation> findByOwner(long ownerId);

	public List<OfyRecommendation> findByOwnerAndStatus(long ownerId,
		Recommendation.Status status);

	public List<OfyRecommendation> findByTargetAndStatus(long targetId,
		Recommendation.Status status);

	public OfyRecommendation patchRecommendation(long userId,
		String recommendationId, Recommendation.Status newStatus);

	public void removeAll();

}