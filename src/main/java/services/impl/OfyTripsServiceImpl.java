package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.Recommendation.Status;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import services.OfyTripsService;
import api.rest.AcceptedOfyTrip;
import api.rest.exceptions.DomainLogicException;
import api.rest.views.TripDetails;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyTripsServiceImpl implements OfyTripsService {

	private final OfyUsersRepository userRepo;
	private final OfyTripsRepository tripRepo;
	private final OfyRecommendationsRepository recommendationRepo;

	@Inject
	public OfyTripsServiceImpl(final OfyUsersRepository userRepo,
		final OfyTripsRepository tripRepo,
		final OfyRecommendationsRepository recommendationRepo) {

		this.userRepo = userRepo;
		this.tripRepo = tripRepo;
		this.recommendationRepo = recommendationRepo;

	}

	@Override
	public OfyTrip createTrip(final long userId,
		final TripDetails tripDetails) {

		final OfyTrip trip = OfyTrip.createFrom(userRepo.findById(userId),
			tripDetails);

		if (!tripRepo.exists(trip.getId())) {

			return tripRepo.add(trip);

		}

		throw new DomainLogicException("Ya existe un viaje con el id asociado");

	}

	@Override
	public void deleteById(final String tripId) {

		this.tripRepo.deleteById(tripId);

	}

	@Override
	public List<AcceptedOfyTrip> findAcceptedByTarget(final long userId) {

		return Lists.transform(this.recommendationRepo.findByTargetAndStatus(this.userRepo.findById(userId),
			Status.ACCEPTED),
			new Function<OfyRecommendation, AcceptedOfyTrip>() {

				@Override
				public AcceptedOfyTrip apply(
					final OfyRecommendation acceptedRecommendation) {

					return new AcceptedOfyTrip(acceptedRecommendation);

				}
			});

	}

	@Override
	public List<OfyTrip> findAll() {

		return this.tripRepo.findAll();

	}

	@Override
	public OfyTrip findById(final String id) {

		return this.tripRepo.findById(id);

	}

	@Override
	public List<OfyTrip> findByOwner(final long ownerId) {

		return this.tripRepo.findByOwner(this.userRepo.findById(ownerId));

	}

	@Override
	public void removeAll() {

		this.tripRepo.removeAll();

	}

}
