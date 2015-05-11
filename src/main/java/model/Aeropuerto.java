package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Aeropuerto implements Serializable {

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("pais")
    private String country;

    @JsonProperty("ciudad")
    private String city;

    public Aeropuerto() {

    }

    public Aeropuerto(String codigo, String pais, String ciudad) {
        this.code = codigo;
        this.country = pais;
        this.city = ciudad;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    
}
