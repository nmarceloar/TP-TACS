
package model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDetail {
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("total")
	private Integer total;
	
	/**
	 * 
	 * @return The currency
	 */
	@JsonProperty("currency")
	public String getCurrency() {
	
		return currency;
	}
	
	/**
	 * 
	 * @param currency
	 *            The currency
	 */
	@JsonProperty("currency")
	public void setCurrency(String currency) {
	
		this.currency = currency;
	}
	
	/**
	 * 
	 * @return The total
	 */
	@JsonProperty("total")
	public Integer getTotal() {
	
		return total;
	}
	
	/**
	 * 
	 * @param total
	 *            The total
	 */
	@JsonProperty("total")
	public void setTotal(Integer total) {
	
		this.total = total;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder
		                .reflectionToString(this);
	}
	
}
