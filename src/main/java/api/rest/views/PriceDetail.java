/**
 *
 */

package api.rest.views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceDetail {

	public String currency;

	public double total;

	private PriceDetail() {

	}

	@JsonCreator
	public PriceDetail(@JsonProperty("currency") String currency,
		@JsonProperty("total") double total) {

		this.currency = currency;
		this.total = total;
	}

	public String getCurrency() {

		return this.currency;
	}

	public double getTotal() {

		return this.total;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("PriceDetail [currency=");
		builder.append(this.currency);
		builder.append(", total=");
		builder.append(this.total);
		builder.append("]");
		return builder.toString();
	}

}
