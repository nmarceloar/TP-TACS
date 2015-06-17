


package api.rest;

public class Airline {
    
    private String code;
    
    private String name;
    
    public Airline(String code, String callsign, String name, String country) {
    
        this.code = code;
        this.name = buildName(code, callsign, name, country);
        
    }
    
    private String buildName(String code,
        String callsign,
        String name,
        String country) {
    
        StringBuilder nameBuilder = new StringBuilder();
        
        nameBuilder.append(code);
        
        if (!callsign.isEmpty()) {
            nameBuilder.append(" - " + "(" + callsign + ")");
        }
        
        nameBuilder.append(" - " + name);
        
        if (!country.isEmpty()) {
            nameBuilder.append(" - " + country);
        }
        
        return nameBuilder.toString();
    }
    
    public String getCode() {
    
        return this.code;
    }
    
    public void setCode(String code) {
    
        this.code = code;
    }
    
    public String getName() {
    
        return this.name;
    }
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    @Override
    public String toString() {
    
        return name;
    }
    
}
