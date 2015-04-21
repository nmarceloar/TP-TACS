/**
 * 
 */

package api;

import java.util.List;

import org.joda.time.DateTime;

public interface ViajesProvider {
	
	// agregar excepciones --> refactor
	public List<OpcionDeViaje> findOpcionesDeViaje(
	    String aeroOrigen,
	    String aeroDestino,
	    DateTime fechaIda,
	    DateTime fechaVuelta);
	
}
