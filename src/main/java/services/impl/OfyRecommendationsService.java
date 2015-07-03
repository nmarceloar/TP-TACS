package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.Recommendation;
import model2.Trip;
import model2.User;
import model2.impl.OfyRecommendation;
import repository.RecommendationsRepository;
import repository.TripsRepository;
import repository.UsersRepository;
import services.RecommendationsService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyRecommendationsService implements RecommendationsService {

	private UsersRepository userRepo;
	private TripsRepository tripRepo;
	private RecommendationsRepository recommendationRepository;

	@Inject
	public OfyRecommendationsService(UsersRepository userRepo,
		TripsRepository tripRepo,
		RecommendationsRepository recommendationRepository) {

		this.userRepo = userRepo;
		this.tripRepo = tripRepo;
		this.recommendationRepository = recommendationRepository;

	}

	@Override
	public Recommendation createRecommendation(final long ownerId,
		final long targetId, final String tripId) {

		if (ownerId == targetId) {

			throw new DomainLogicException("No se puede crear una recomendacion para uno mismo");

		}

		final Trip trip = this.tripRepo.findById(tripId);
		final User owner = this.userRepo.findById(ownerId);

		if (!trip.wasCreatedBy(owner)) {

			throw new DomainLogicException("Solo los propieratarios de un viaje pueden recommendar el mismo");

		}

		final User target = this.userRepo.findById(targetId);

		return OfyService.ofy()
			.transact(new Work<Recommendation>() {

				@Override
				public Recommendation run() {

					Recommendation recommendation = new OfyRecommendation(owner,
						target,
						trip);

					if (!OfyRecommendationsService.this.recommendationRepository.exists(recommendation.getId())) {

						return OfyRecommendationsService.this.recommendationRepository.add(recommendation);

					}

					throw new DomainLogicException("Ya existe una recomendacion de " + ownerId
						+ " para "
						+ targetId
						+ " que referencia al viaje "
						+ tripId);

				}

			});

	}

	@Override
	public List<? extends Recommendation> findAll() {

		return this.recommendationRepository.findAll();

	}

	@Override
	public Recommendation findById(final String recId) {

		return this.recommendationRepository.findById(recId);

	}

	@Override
	public List<? extends Recommendation> findByOwner(final long ownerId) {

		return this.recommendationRepository.findByOwner(this.userRepo.findById(ownerId));

	}

	@Override
	public List<? extends Recommendation> findByOwnerAndStatus(
		final long ownerId, final OfyRecommendation.Status status) {

		return this.recommendationRepository.findByOwnerAndStatus(this.userRepo.findById(ownerId),
			status);
	}

	@Override
	public List<? extends Recommendation> findByTargetAndStatus(
		final long targetId, final OfyRecommendation.Status status) {

		return this.recommendationRepository.findByTargetAndStatus(this.userRepo.findById(targetId),
			status);
	}

	@Override
	public Recommendation
		patchRecommendation(final long userId, final String recommendationId,
			final OfyRecommendation.Status newStatus) {

		return OfyService.ofy()
			.transact(new Work<Recommendation>() {

				@Override
				public Recommendation run() {

					Recommendation recommendation = OfyRecommendationsService.this.recommendationRepository.findById(recommendationId);

					if (!recommendation.wasCreatedFor(OfyRecommendationsService.this.userRepo.findById(userId))) {

						throw new DomainLogicException("Solo el destinatario de una recomendacion puede modificar la misma.");

					}

					return OfyRecommendationsService.this.recommendationRepository.add(recommendation.markAs(newStatus));

				}
			});

		// aca habria que notificar a facebook para no distribuir la logica
		// entre el front y el server

	}

	@Override
	public void removeAll() {
		this.recommendationRepository.removeAll();
	}
}
