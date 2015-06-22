package integracion.despegar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripOptions {

	private List<TripOption> items;

	private Paging paging;

	public TripOptions() {

		this.items = new ArrayList<TripOption>();

	}

	public List<TripOption> getItems() {

		return this.items;
	}

	public void setItems(final List<TripOption> items) {

		this.items = items;

	}

	public Paging getPaging() {

		return this.paging;
	}

	public void setPaging(Paging paging) {

		this.paging = paging;
	}

	@JsonIgnore(value = true)
	public Set<String> getAiportsCodes() {

		Set<String> codes = Sets.newHashSet();

		for (TripOption to : this.items) {

			codes.addAll(to.getAirportsCodes());

		}

		return codes;

	}

	@JsonIgnore(value = true)
	public Set<String> getAirlinesCodes() {

		Set<String> codes = Sets.newHashSet();

		for (TripOption to : this.items) {

			codes.addAll(to.getAirlinesCodes());

		}

		return codes;

	}

}
