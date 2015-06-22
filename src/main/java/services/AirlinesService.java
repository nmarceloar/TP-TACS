/**
 * 
 */



package api.rest;


public interface AirlinesService {
    
    public Airline findByCode(String twoLetterIataCode);
    
}
