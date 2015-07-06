package api.rest.views;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.RandomStringUtils.*;

public class Airport {

	private String code;
	private String name;
	private double latitude;
	private double longitude;

	private Airport() {

	}

	@JsonCreator
	public Airport(@JsonProperty("code") String code,
		@JsonProperty("name") String name,
		@JsonProperty("latitude") double latitude,
		@JsonProperty("longitude") double longitude) {

		this.code = code;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;

	}

	public String getCode() {

		return this.code;
	}

	public double getLatitude() {

		return this.latitude;
	}

	public double getLongitude() {

		return this.longitude;
	}

	public String getName() {

		return this.name;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Airport [code=");
		builder.append(this.code);
		builder.append(", name=");
		builder.append(this.name);
		builder.append(", latitude=");
		builder.append(this.latitude);
		builder.append(", longitude=");
		builder.append(this.longitude);
		builder.append("]");
		return builder.toString();
	}

}
