/**
 * 
 */



package api.rest;

import static services.OfyService.ofy;

import java.util.List;

import services.OfyUser;
import services.OfyUserRepository;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.Work;

// en realidad habria que implementar un AOP para poder separar bien el
// servicio del repositorio

public class OfyUserService {
    
    private OfyUserRepository userRepo;
    
    private final static OfyUserService INSTANCE = new OfyUserService();
    
    public final static OfyUserService getInstance() {
    
        return INSTANCE;
        
    }
    
    private OfyUserService() {
    
        this.userRepo = OfyUserRepository.getInstance();
        
    }
    
    public OfyUser createUser(final long id,
        final String name,
        final String facebookLink,
        final String email) {
    
        return ofy().transact(new Work<OfyUser>() {
            
            @Override
            public OfyUser run() {
            
                if (!userRepo.exists(id)) {
                    
                    return userRepo.add(new OfyUser(id, name, facebookLink,
                        email));
                    
                }
                
                throw new DomainLogicException(
                    "Ya existe un usuario con ese id");
                
            }
        });
        
    }
    
    public List<OfyUser> findAll() {
    
        return this.userRepo.findAll();
        
    }
    
    public boolean exists(long userId) {
    
        return this.userRepo.exists(userId);
        
    }
    
    /**
     * @param userDetails
     */
    public OfyUser createUser(final UserDetails userDetails) {
    
        return ofy().transact(new Work<OfyUser>() {
            
            @Override
            public OfyUser run() {
            
                if (!userRepo.exists(userDetails.getId())) {
                    
                    return userRepo.add(new OfyUser(userDetails));
                    
                }
                
                throw new DomainLogicException(
                    "Ya existe un usuario con ese id");
                
            }
        });
        
    }
    
    public OfyUser findById(Long userId) {
    
        return this.userRepo.findById(userId);
        
    }
    
}
