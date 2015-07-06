package api.rest;

public class Airline {

	private String code;
	private String name;

	public Airline(String code, String callsign, String name,
		String country) {

		this.code = code;
		this.name = buildName(code, callsign, name, country);

	}

	private String buildName(String code, String callsign, String name,
		String country) {

		StringBuilder nameBuilder = new StringBuilder();

		nameBuilder.append(code);

		if (!callsign.isEmpty()) {
			nameBuilder.append(" - " + "(" + callsign + ")");
		}

		if (!name.isEmpty()) {
			nameBuilder.append(" - " + name);
		}

		if (!country.isEmpty()) {
			nameBuilder.append(" - " + country);
		}

		return nameBuilder.toString();
	}

	public String getCode() {

		return this.code;
	}

	public String getName() {

		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airline other = (Airline)obj;
		if (code == null) {

			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Airline [getName()=" + getName() + "]";
	}

}
