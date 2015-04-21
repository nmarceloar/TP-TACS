/**
 * 
 */

package api;

import java.util.List;

import model.Viaje;

public interface ViajesService {
	
	public List<Viaje> findViajesForUser(long userID) throws DomainException;
	
}
