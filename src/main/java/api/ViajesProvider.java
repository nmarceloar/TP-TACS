/**
 * 
 */

package api;

import java.util.List;

import org.joda.time.DateTime;

/**
 * @author nmarcelo.ar
 *
 */
public interface ViajesProvider {
	
	
	//agregar excepciones --> refactor 
	public List<OpcionDeViaje> findOpcionesDeViaje(
	    String cityFrom,
	    String cityTo,
	    DateTime fechaIda,
	    DateTime fechaVuelta);
	
}
