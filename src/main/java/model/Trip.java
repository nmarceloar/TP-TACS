package model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Trip implements Serializable {
    
    private static int contadorId = 1;

    @JsonProperty("idPassenger")
    private int idPassenger;

    @JsonProperty("itinerary")
    private List<Segment> itinerary;

    private int idTrip;

    public Trip() {
        idTrip = contadorId++;
    }

    public Trip(int idPassenger, List<Segment> itinerary) {
        this.idPassenger = idPassenger;
        this.itinerary = itinerary;
        idTrip = contadorId++;
    }

    

    public int getIdPassenger() {
        return idPassenger;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setViajante(int viajante) {
        this.idPassenger = viajante;
    }

    public List<Segment> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<Segment> itinerary) {
        this.itinerary = itinerary;
    }

    public String getTripDepartureDate() {
        return getItinerary()
                .get(0).getDepartureDatetime();
    }

    public String getTripArrivalDate() {
        return getItinerary()
                .get(itinerary.size() - 1).getArrivalDatetime();
    }

    public void addSegment(Segment tray) {
        getItinerary().add(tray);
    }

}
