/**
 * clase test simple para ir probando.. no tiene ningun valor. se accede con un
 * GET a http://localhost:8080/api/hello/test
 */

package api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWordTest {
	
	@GET
	@Path("/test")
	public String testMethod() {
	
		return "this is a test";
	}
}
