package model2.impl;

import java.util.Date;

import model2.Trip;
import model2.User;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import utils.DateSerializer;
import api.rest.views.TripDetails;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class OfyTrip implements Trip {

    public static Trip createFrom(OfyUser owner, TripDetails td) {

        return new OfyTrip(owner, td);
    }

    @Id
    private String id;

    @Index
    private long creationDate;

    @Load
    @Index
    private Ref<User> owner;

    private TripDetails tripDetails;

    public OfyTrip() {

    }

    public OfyTrip(User owner, TripDetails td) {

        this.owner = Ref.create(owner);
        this.tripDetails = td;

        this.id = this.buildId(owner, td);

        this.creationDate = System.currentTimeMillis();

    }

    private String buildId(User owner, TripDetails td) {

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");

        String ownerId = String.valueOf(owner.getId());

        String fromCity = td.getFromCity()
                .getCode();

        String toCity = td.getToCity()
                .getCode();

		// lo mejor que podemos hacer es agregar el user id al id que entrega
        // despegar.
        return ownerId + fromCity
                + toCity
                + dtf.print(this.tripDetails.getOutboundDate()
                        .getTime())
                + dtf.print(this.tripDetails.getOutboundDate()
                        .getTime());

    }

    @Override
    public void setTripDetails(TripDetails tripDetails) {
        this.tripDetails = tripDetails;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OfyTrip)) {
            return false;
        }
        OfyTrip other = (OfyTrip) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    @JsonSerialize(using = DateSerializer.class)
    public Date getCreationDate() {

        return new Date(this.creationDate);
    }

    @Override
    public String getId() {

        return this.id;
    }

    @Override
    public User getOwner() {

        return this.owner.get();
    }

    @Override
    public TripDetails getTripDetails() {

        return this.tripDetails;

    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("OfyTrip [getId()=");
        builder.append(this.getId());
        builder.append(", getOwner()=");
        builder.append(this.getOwner());
        builder.append(", getCreationDate()=");
        builder.append(this.getCreationDate());
        builder.append(", getTripDetails()=");
        builder.append(this.getTripDetails());
        builder.append(", hashCode()=");
        builder.append(this.hashCode());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean wasCreatedBy(User owner2) {

        return this.getOwner()
                .equals(owner2);

    }

}
