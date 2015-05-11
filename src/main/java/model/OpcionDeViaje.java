
package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpcionDeViaje {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("outbound_choices")
	private List<Itinerario> outboundChoices =
	    new ArrayList<>();
	
	@JsonProperty("inbound_choices")
	private List<Itinerario> inboundChoices =
	    new ArrayList<>();
	
	@JsonProperty("price_detail")
	private PriceDetail priceDetail;
	
	/**
	 * 
	 * @return The id
	 */
	@JsonProperty("id")
	public String getId() {
	
		return this.id;
	}
	
	/**
	 * 
	 * @return The inboundChoices
	 */
	@JsonProperty("inbound_choices")
	public List<Itinerario> getInboundChoices() {
	
		return this.inboundChoices;
	}
	
	/**
	 * 
	 * @return The outboundChoices
	 */
	@JsonProperty("outbound_choices")
	public List<Itinerario> getOutboundChoices() {
	
		return this.outboundChoices;
	}
	
	/**
	 * 
	 * @return The priceDetail
	 */
	@JsonProperty("price_detail")
	public PriceDetail getPriceDetail() {
	
		return this.priceDetail;
	}
	
	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("id")
	public void setId(final String id) {
	
		this.id = id;
	}
	
	/**
	 * 
	 * @param inboundChoices
	 *            The inbound_choices
	 */
	@JsonProperty("inbound_choices")
	public void setInboundChoices(final List<Itinerario> itinerarios) {
	
		this.inboundChoices = itinerarios;
	}
	
	/**
	 * 
	 * @param outboundChoices
	 *            The outbound_choices
	 */
	@JsonProperty("outbound_choices")
	public void setOutboundChoices(final List<Itinerario> outboundChoices) {
	
		this.outboundChoices = outboundChoices;
	}
	
	/**
	 * 
	 * @param priceDetail
	 *            The price_detail
	 */
	@JsonProperty("price_detail")
	public void setPriceDetail(final PriceDetail priceDetail) {
	
		this.priceDetail = priceDetail;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder
		                .reflectionToString(this);
	}
	
}
