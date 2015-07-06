package integracion.despegar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paging {

	private int offset;
	private int limit;
	private int total;

	public Paging() {

	}

	public int getOffset() {

		return this.offset;
	}

	public int getTotal() {

		return this.total;
	}

	public void setOffset(int offset) {

		this.offset = offset;
	}

	public void setTotal(int total) {

		this.total = total;
	}

	public int getLimit() {

		return this.limit;
	}

	public void setLimit(int limit) {

		this.limit = limit;
	}

}
