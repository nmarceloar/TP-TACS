


package services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class City {
    
    private String code;
    
    private String name;
    
    private double latitude;
    
    private double longitude;
    
    private City() {
    
    }
    
    @JsonCreator
    public City(@JsonProperty("code") String code,
        @JsonProperty("name") String name,
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude) {
    
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }

    
    public String getCode() {
    
        return this.code;
    }

    
    public String getName() {
    
        return this.name;
    }

    
    public double getLatitude() {
    
        return this.latitude;
    }

    
    public double getLongitude() {
    
        return this.longitude;
    }
    
    
    
}
