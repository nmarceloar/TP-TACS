/**
 * 
 */



package api.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Named;
import javax.inject.Singleton;

import com.opencsv.CSVReader;

@Named
@Singleton
public class AirlinesServiceImpl implements AirlinesService {
    
    private final ConcurrentMap<String, Airline> airlines;
    
    public AirlinesServiceImpl() {
    
        this.airlines = new ConcurrentHashMap<String, Airline>(900);
        this.fillMap();
        
    }
    
    private void fillMap() {
    
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
            .getClassLoader()
            .getResourceAsStream("airlines.csv")));
        
        String[] line;
        
        try {
            
            while ((line = reader.readNext()) != null) {
                
                if (line[3].matches("[A-Z]{2}")) {
                    
                    this.airlines.put(line[3], new Airline(line[3], line[5],
                        line[1], line[6]));
                    
                }
                
            }
            
        } catch (IOException ex) {
            
            throw new RuntimeException(ex);
            
        } finally {
            
            try {
                
                reader.close();
                
            } catch (IOException ex) {
                
                throw new RuntimeException(ex);
                
            }
            
        }
        
    }
    
    @Override
    public Airline findByCode(final String twoLetterIataCode) {
    
        return this.airlines.get(twoLetterIataCode);
        
    }
    
}
