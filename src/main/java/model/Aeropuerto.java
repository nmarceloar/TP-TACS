package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Aeropuerto implements Serializable {

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("pais")
    private String pais;

    @JsonProperty("ciudad")
    private String ciudad;

    public Aeropuerto() {

    }

    public Aeropuerto(String codigo, String pais, String ciudad) {
        this.codigo = codigo;
        this.pais = pais;
        this.ciudad = ciudad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
