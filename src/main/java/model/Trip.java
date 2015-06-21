package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trip implements Serializable {

    private static int contadorId = 1;

    @JsonProperty("idPassenger")
    private String idPassenger;

    @JsonProperty("fromCity")
    private String fromCity;

    @JsonProperty("toCity")
    private String toCity;

    @JsonProperty("price")
    private String price;

    @JsonProperty("itinerary")
    private List<Segment> itinerary;

    @JsonProperty("idTrip")
    private int idTrip;

    public String getFromCity() {

        return fromCity;
    }

    public void setFromCity(String fromCity) {

        this.fromCity = fromCity;
    }

    public String getToCity() {

        return toCity;
    }

    public void setToCity(String toCity) {

        this.toCity = toCity;
    }

    public Trip() {

        idTrip = contadorId++;
    }

    public Trip(String idPassenger, String fromCity, String toCity,
            String price, List<Segment> itinerary) {

        this.idPassenger = idPassenger;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.price = price;
        this.itinerary = itinerary;
        idTrip = contadorId++;
    }

    public Trip(String idPassenger, String fromCity, String toCity,
            String price, List<Segment> itinerary, int idTrip) {
        this.idPassenger = idPassenger;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.price = price;
        this.itinerary = itinerary;
        this.idTrip = idTrip;
    }

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public void setIdPassenger(String idPassenger) {

        this.idPassenger = idPassenger;
    }

    public String getIdPassenger() {

        return idPassenger;
    }

    public int getIdTrip() {

        return idTrip;
    }

    public void setIdTrip(int idTrip) {

        this.idTrip = idTrip;
    }

    public void setViajante(String viajante) {

        this.idPassenger = viajante;
    }

    public List<Segment> getItinerary() {

        return itinerary;
    }

    public void setItinerary(List<Segment> itinerary) {

        this.itinerary = itinerary;
    }

    public String getTripDepartureDate() {

        return getItinerary().get(0)
                .getDepartureDatetime();
    }

    public String getTripArrivalDate() {

        return getItinerary().get(itinerary.size() - 1)
                .getArrivalDatetime();
    }

    public void addSegment(Segment tray) {

        getItinerary().add(tray);
    }

}
