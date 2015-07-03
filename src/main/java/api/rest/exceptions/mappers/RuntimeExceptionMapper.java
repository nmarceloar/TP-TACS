package api.rest.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements
	ExceptionMapper<RuntimeException> {

	@Override
	public Response toResponse(RuntimeException exception) {

		return Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity(exception.getMessage())
			.build();

	}

}
