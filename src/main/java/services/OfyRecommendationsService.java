


package services;

import static services.OfyService.ofy;

import java.util.List;

import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;


// en realidad habria que implementar un AOP para poder separar bien el 
// servicio del repositorio

public class OfyRecommendationsService {
    
    private OfyUserRepository userRepo;
    
    private OfyTripRepository tripRepo;
    
    private OfyRecommendationRepository ofyRecommendationRepository;
    
    private OfyRecommendationsService() {
    
        this.userRepo = OfyUserRepository.getInstance();
        this.tripRepo = OfyTripRepository.getInstance();
        this.ofyRecommendationRepository = OfyRecommendationRepository.getInstance();
        
    }
    
    public OfyRecommendation createRecommendation(final long ownerId,
        final long targetId,
        final String tripId) {
    
        if (ownerId == targetId) {
            
            throw new DomainLogicException(
                "No se puede crear una recomendacion para uno mismo");
            
        }
        
        final OfyTrip trip = this.tripRepo.findById(tripId);
        final OfyUser owner = this.userRepo.findById(ownerId);
        
        if (!trip.wasCreatedBy(owner)) {
            
            throw new DomainLogicException(
                "Solo los propieratarios de un viaje pueden recommendar el mismo");
            
        }
        
        final OfyUser target = this.userRepo.findById(targetId);
        
        return ofy().transact(new Work<OfyRecommendation>() {
            
            @Override
            public OfyRecommendation run() {
            
                OfyRecommendation recommendation =
                    new OfyRecommendation(owner, target, trip);
                
                if (!ofyRecommendationRepository.exists(recommendation.getId())) {
                    
                    return ofyRecommendationRepository.add(recommendation);
                    
                }
                
                throw new DomainLogicException(
                    "Ya existe una recomendacion de " + ownerId + " para " +
                        targetId + " que referencia al viaje " + tripId);
                
            }
            
        });
        
    }
    
    public OfyRecommendation patchRecommendation(final long userId,
        final String recommendationId,
        final OfyRecommendation.Status newStatus) {
    
        return ofy().transact(new Work<OfyRecommendation>() {
            
            @Override
            public OfyRecommendation run() {
            
                OfyRecommendation recommendation =
                    ofyRecommendationRepository.findById(recommendationId);
                
                if (!recommendation.wasCreatedFor(userRepo.findById(userId))) {
                    
                    throw new DomainLogicException(
                        "Solo el destinatario de una recomendacion puede modificar la misma.");
                    
                }
                
                return ofyRecommendationRepository.add(recommendation.markAs(newStatus));
                
            }
        });
        
        // aca habria que notificar a facebook para no distribuir la logica entre el front y el server
        
    }
    
    public OfyRecommendation findById(final String recId) {
    
        return this.ofyRecommendationRepository.findById(recId);
        
    }
    
    public List<OfyRecommendation> findByTargetAndStatus(final long targetId,
        final OfyRecommendation.Status status) {
    
        return this.ofyRecommendationRepository.findByTargetAndStatus(
            this.userRepo.findById(targetId), status);
    }
    
    public List<OfyRecommendation> findByOwnerAndStatus(final long ownerId,
        final OfyRecommendation.Status status) {
    
        return this.ofyRecommendationRepository.findByOwnerAndStatus(
            this.userRepo.findById(ownerId), status);
    }
    
    private final static OfyRecommendationsService INSTANCE =
        new OfyRecommendationsService();
    
    public static final OfyRecommendationsService getInstance() {
    
        return INSTANCE;
        
    }
    
    public List<OfyRecommendation> findByOwner(final long ownerId) {
    
        return this.ofyRecommendationRepository.findByOwner(this.userRepo.findById(ownerId));
    }
    
    public List<OfyRecommendation> findAll() {
    
        return this.ofyRecommendationRepository.findAll();
    }
}
