package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import services.OfyRecommendationsService;
import api.rest.exceptions.DomainLogicException;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyRecommendationsServiceImpl implements
	OfyRecommendationsService {

	private OfyUsersRepository userRepo;
	private OfyTripsRepository tripRepo;
	private OfyRecommendationsRepository recommendationRepository;

	@Inject
	public OfyRecommendationsServiceImpl(
		final OfyUsersRepository userRepo,
		final OfyTripsRepository tripRepo,
		final OfyRecommendationsRepository recommendationRepository) {

		this.userRepo = userRepo;
		this.tripRepo = tripRepo;
		this.recommendationRepository = recommendationRepository;

	}

	@Override
	public OfyRecommendation createRecommendation(final long ownerId,
		final long targetId, final String tripId) {

		if (ownerId == targetId) {

			throw new DomainLogicException("No se puede crear una recomendacion para uno mismo");

		}

		final OfyTrip trip = this.tripRepo.findById(tripId);
		final OfyUser owner = this.userRepo.findById(ownerId);

		if (!trip.wasCreatedBy(owner)) {

			throw new DomainLogicException("Solo los propieratarios de un viaje pueden recommendar el mismo");

		}

		final OfyUser target = this.userRepo.findById(targetId);

		final OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
			target,
			trip);

		if (!recommendationRepository.exists(recommendation.getId())) {

			return recommendationRepository.add(recommendation);

		}

		throw new DomainLogicException("Ya existe una recomendacion de " + ownerId
			+ " para "
			+ targetId
			+ " que referencia al viaje "
			+ tripId);

	}

	@Override
	public List<OfyRecommendation> findAll() {

		return this.recommendationRepository.findAll();

	}

	@Override
	public OfyRecommendation findById(final String recId) {

		return this.recommendationRepository.findById(recId);

	}

	@Override
	public List<OfyRecommendation> findByOwner(final long ownerId) {

		return this.recommendationRepository.findByOwner(this.userRepo.findById(ownerId));

	}

	@Override
	public List<OfyRecommendation> findByOwnerAndStatus(
		final long ownerId, final OfyRecommendation.Status status) {

		return this.recommendationRepository.findByOwnerAndStatus(this.userRepo.findById(ownerId),
			status);
	}

	@Override
	public List<OfyRecommendation> findByTargetAndStatus(
		final long targetId, final OfyRecommendation.Status status) {

		return this.recommendationRepository.findByTargetAndStatus(this.userRepo.findById(targetId),
			status);

	}

	@Override
	public OfyRecommendation patchRecommendation(final long userId,
		final String recommendationId,
		final OfyRecommendation.Status newStatus) {

		final OfyRecommendation recommendation = recommendationRepository.findById(recommendationId);

		if (!recommendation.wasCreatedFor(userRepo.findById(userId))) {

			throw new DomainLogicException("Solo el destinatario de una recomendacion puede modificar la misma.");

		}

		return recommendationRepository.add(recommendation.markAs(newStatus));

		// aca habria que notificar a facebook para no distribuir la logica
		// entre el front y el server
	}

	@Override
	public void removeAll() {

		this.recommendationRepository.removeAll();

	}
}
