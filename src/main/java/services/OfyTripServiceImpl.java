package services;

import java.util.List;

import services.OfyRecommendation.Status;
import api.rest.AcceptedOfyTrip;
import api.rest.exceptions.DomainLogicException;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyTripServiceImpl implements OfyTripService {

	private final static OfyTripServiceImpl INSTANCE = new OfyTripServiceImpl();

	public static OfyTripServiceImpl getInstance() {

		return OfyTripServiceImpl.INSTANCE;

	}

	private OfyUserRepository userRepo;

	private OfyTripRepository tripRepo;

	private OfyRecommendationRepository recommendationRepo;

	private OfyTripServiceImpl() {

		this.userRepo = OfyUserRepository.getInstance();
		this.tripRepo = OfyTripRepository.getInstance();
		this.recommendationRepo = OfyRecommendationRepository.getInstance();

	}

	public OfyTripServiceImpl(OfyUserRepository userRepo,
			OfyTripRepository tripRepo) {

		this.userRepo = OfyUserRepository.getInstance();
		this.tripRepo = OfyTripRepository.getInstance();

	}

	@Override
	public OfyTrip createTrip(final long userId, final TripDetails tripDetails) {

		return OfyService.ofy()
			.transact(new Work<OfyTrip>() {

				@Override
				public OfyTrip run() {

					OfyTrip trip = new OfyTrip(
							OfyTripServiceImpl.this.userRepo.findById(userId),
							tripDetails);

					if (!OfyTripServiceImpl.this.tripRepo.exists(trip.getId())) {

						return OfyTripServiceImpl.this.tripRepo.add(trip);

					}

					throw new DomainLogicException(
							"Ya existe un viaje con el id asociado");

				}

			});

	}

	@Override
	public List<AcceptedOfyTrip> findAcceptedByTarget(final long userId) {

		// entre los aceptados(recomendaciones), filtra por target user

		// esto despues lo podemos cambiar por una nueva entidad para no tener
		// que delegar y crear una vista

		List<AcceptedOfyTrip> trips = Lists.transform(
				this.recommendationRepo.findByTargetAndStatus(
						this.userRepo.findById(userId), Status.ACCEPTED),
						new Function<OfyRecommendation, AcceptedOfyTrip>() {

					@Override
					public AcceptedOfyTrip apply(
							OfyRecommendation acceptedRecommendation) {

						return new AcceptedOfyTrip(
								acceptedRecommendation.getTrip(),
								acceptedRecommendation);

					}
				});

		return trips;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.TripService#findAll()
	 */
	@Override
	public List<OfyTrip> findAll() {

		return this.tripRepo.findAll();

	}

	@Override
	public List<OfyTrip> findByOwner(final long ownerId) {

		return this.tripRepo.findByOwner(this.userRepo.findById(ownerId));

	}

	public void removeAll() {
		this.tripRepo.removeAll();
	}
}
