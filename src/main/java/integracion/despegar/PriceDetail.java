package integracion.despegar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDetail {

	private String currency;

	private double total;

	public String getCurrency() {

		return this.currency;
	}

	public double getTotal() {

		return this.total;
	}

	public void setCurrency(final String currency) {

		this.currency = currency;
	}

	public void setTotal(final double total) {

		this.total = total;
	}

}
