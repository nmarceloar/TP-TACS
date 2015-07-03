package api.rest;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenInfo {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Data {

		private final long app_id;

		private final String application;

		private final long expires_at;

		private final boolean is_valid;

		private final List<String> scopes;

		private final long user_id;

		@JsonCreator
		public Data(@JsonProperty("app_id") final long app_id,
			@JsonProperty("application") final String application,
			@JsonProperty("expires_at") final long expires_at,
			@JsonProperty("is_valid") final boolean is_valid,
			@JsonProperty("scopes") final List<String> scopes,
			@JsonProperty("user_id") final long user_id) {

			this.app_id = app_id;
			this.application = application;
			this.expires_at = expires_at;
			this.is_valid = is_valid;
			this.scopes = scopes;
			this.user_id = user_id;

		}

		public long getApp_id() {

			return this.app_id;

		}

		public String getApplication() {

			return this.application;
		}

		public long getExpires_at() {

			return this.expires_at;
		}

		public List<String> getScopes() {

			return this.scopes;
		}

		public long getUser_id() {

			return this.user_id;
		}

		public boolean isIs_valid() {

			return this.is_valid;
		}

	}

	private final Data data;

	@JsonCreator
	public TokenInfo(@JsonProperty("data") final Data data) {

		this.data = data;
	}

	public Data getData() {

		return this.data;
	}

	public DateTime getExpirationDate() {

		return new DateTime(Long.valueOf(this.data.getExpires_at() * 1000));
	}

	public long getUserId() {

		return this.data.getUser_id();
	}

	public boolean isValid() {

		return this.data.isIs_valid();
	}

}
