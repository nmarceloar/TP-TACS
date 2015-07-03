package services;

import java.util.List;

import model2.Recommendation;

public interface RecommendationsService {

	public Recommendation createRecommendation(long ownerId, long targetId,
		String tripId);

	public List<? extends Recommendation> findAll();

	public Recommendation findById(String recId);

	public List<? extends Recommendation> findByOwner(long ownerId);

	public List<? extends Recommendation> findByOwnerAndStatus(long ownerId,
		Recommendation.Status status);

	public List<? extends Recommendation> findByTargetAndStatus(long targetId,
		Recommendation.Status status);

	public Recommendation patchRecommendation(long userId,
		String recommendationId, Recommendation.Status newStatus);

	public void removeAll();

}