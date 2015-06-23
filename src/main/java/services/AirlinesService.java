/**
 *
 */

package services;

import java.util.List;

import api.rest.Airline;

public interface AirlinesService {

	public Airline findByCode(String twoLetterIataCode);

	public List<Airline> findAll();

}
