package api.rest.views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Airline {

	private String code;

	private String name;

	private Airline() {

	}

	@JsonCreator
	public Airline(@JsonProperty("code") String code,
		@JsonProperty("name") String name) {

		this.code = code;
		this.name = name;
	}

	public String getCode() {

		return this.code;
	}

	public String getName() {

		return this.name;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Airline [code=");
		builder.append(this.code);
		builder.append(", name=");
		builder.append(this.name);
		builder.append("]");
		return builder.toString();
	}

}
