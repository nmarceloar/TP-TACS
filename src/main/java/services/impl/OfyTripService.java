package services.impl;

import java.util.List;

import javax.inject.Inject;

import model2.Recommendation;
import model2.Recommendation.Status;
import model2.Trip;
import model2.impl.OfyTrip;
import repository.RecommendationsRepository;
import repository.TripsRepository;
import repository.UsersRepository;
import services.TripsService;
import api.rest.AcceptedOfyTrip;
import api.rest.AcceptedTrip;
import api.rest.exceptions.DomainLogicException;
import api.rest.views.TripDetails;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio
public class OfyTripService implements TripsService {

    private UsersRepository userRepo;
    private TripsRepository tripRepo;
    private RecommendationsRepository recommendationRepo;

    @Inject
    public OfyTripService(UsersRepository userRepo, TripsRepository tripRepo,
            RecommendationsRepository recommendationRepo) {

        this.userRepo = userRepo;
        this.tripRepo = tripRepo;
        this.recommendationRepo = recommendationRepo;

    }

    public OfyTripService() {
    }

    @Override
    public Trip createTrip(final long userId, final TripDetails tripDetails) {

        return OfyService.ofy()
                .transact(new Work<Trip>() {

                    @Override
                    public Trip run() {

                        OfyTrip trip = new OfyTrip(userRepo.findById(userId),
                                tripDetails);

                        if (!tripRepo.exists(trip.getId())) {

                            return tripRepo.add(trip);

                        }

                        throw new DomainLogicException("Ya existe un viaje con el id asociado");

                    }

                });

    }

    @Override
    public List<? extends AcceptedTrip> findAcceptedByTarget(final long userId) {

		// entre los aceptados(recomendaciones), filtra por target user
        // esto despues lo podemos cambiar por una nueva entidad para no tener
        // que delegar y crear una vista
        List<AcceptedTrip> trips = Lists.transform(
                recommendationRepo.findByTargetAndStatus(this.userRepo.findById(userId), Status.ACCEPTED),
                new Function<Recommendation, AcceptedTrip>() {

                    @Override
                    public AcceptedTrip
                    apply(Recommendation acceptedRecommendation) {

                        return new AcceptedOfyTrip(acceptedRecommendation.getTrip(),
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

    @Override
    public void removeAll() {
        this.tripRepo.removeAll();
    }

    @Override
    public Trip findById(String id) {

        return this.tripRepo.findById(id);

    }

    @Override
    public void deleteById(String tripId) {

        this.tripRepo.deleteById(tripId);

    }

}
