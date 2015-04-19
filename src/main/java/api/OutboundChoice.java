
package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "choice",
    "duration",
    "segments"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundChoice {

    @JsonProperty("choice")
    private Integer choice;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("segments")
    private List<Segment> segments = new ArrayList<Segment>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The choice
     */
    @JsonProperty("choice")
    public Integer getChoice() {
        return choice;
    }

    /**
     * 
     * @param choice
     *     The choice
     */
    @JsonProperty("choice")
    public void setChoice(Integer choice) {
        this.choice = choice;
    }

    /**
     * 
     * @return
     *     The duration
     */
    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The segments
     */
    @JsonProperty("segments")
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * 
     * @param segments
     *     The segments
     */
    @JsonProperty("segments")
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(choice).append(duration).append(segments).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OutboundChoice) == false) {
            return false;
        }
        OutboundChoice rhs = ((OutboundChoice) other);
        return new EqualsBuilder().append(choice, rhs.choice).append(duration, rhs.duration).append(segments, rhs.segments).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
