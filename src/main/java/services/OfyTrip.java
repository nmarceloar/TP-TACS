


package services;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class OfyTrip {
    
    @Id
    private String id;
    
    @Index
    private long creationDate;
    
    @Load
    @Index
    private Ref<OfyUser> owner;
    
    private TripDetails tripDetails;
    
    private OfyTrip() {
    
    }
    
    public OfyTrip(OfyUser owner, TripDetails td) {
    
        this.owner = Ref.create(owner);
        this.tripDetails = td;
        
        this.id = this.buildId(owner, td);
        
        this.creationDate = System.currentTimeMillis();
        
    }
    
    public static OfyTrip createFrom(OfyUser owner,
        TripDetails td) {
    
        return new OfyTrip(owner, td);
    }
    
    private String buildId(OfyUser owner,
        TripDetails td) {
    
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
        
        String ownerId = String.valueOf(owner.getId());
        
        String fromCity = td.getFromCity()
            .getCode();
        
        String toCity = td.getToCity()
            .getCode();
        
        // lo mejor que podemos hacer es agregar el user id al id que entrega despegar.
        
        return ownerId + fromCity + toCity +
            dtf.print(tripDetails.getOutboundDate()
                .getTime()) + dtf.print(tripDetails.getOutboundDate()
                .getTime());
        
    }
    
    public String getId() {
    
        return this.id;
    }
    
    public OfyUser getOwner() {
    
        return this.owner.get();
    }
    
    @JsonSerialize(using = DateSerializer.class)
    public Date getCreationDate() {
    
        return new Date(this.creationDate);
    }
    
    public TripDetails getTripDetails() {
    
        return this.tripDetails;
        
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
    
    public boolean wasCreatedBy(OfyUser owner2) {
    
        return this.getOwner()
            .equals(owner2);
        
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
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
        OfyTrip other = (OfyTrip)obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
}
