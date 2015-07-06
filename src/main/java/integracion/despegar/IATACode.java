package integracion.despegar;

import com.google.common.base.Preconditions;

/********************* NO USAR POR AHORA ************/

public class IATACode {

	private String iataCode;

	private IATACode() {

	}

	private IATACode(final String iataCode) {

		IATACode.checkValid(iataCode);

		this.iataCode = iataCode;

	}

	public static String checkValid(final String iataCode) {

		Preconditions.checkNotNull(iataCode,
			"El codigo del aeropuerto no puede ser null");

		Preconditions.checkArgument(iataCode.toLowerCase()
			.matches("[a-z]{3}"),
			"Formato no válido de codigo de aeropuerto");

		return iataCode;

	}

	// jersey
	public static IATACode fromString(final String iataCode) {

		return new IATACode(iataCode);

	}

	// jersey
	public static IATACode valueOf(final String iataCode) {

		return new IATACode(iataCode);

	}

	@Override
	public String toString() {

		return this.iataCode.toUpperCase();

	}

}
