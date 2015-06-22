/**
 *
 */

package services;

import api.rest.Airline;

public interface AirlinesService {

	public Airline findByCode(String twoLetterIataCode);

}
