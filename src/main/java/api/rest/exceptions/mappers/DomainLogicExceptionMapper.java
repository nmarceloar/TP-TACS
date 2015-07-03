package api.rest.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import api.rest.exceptions.DomainLogicException;

@Provider
public class DomainLogicExceptionMapper implements
	ExceptionMapper<DomainLogicException> {

	@Override
	public Response toResponse(DomainLogicException ex) {

		return Response.status(Status.PRECONDITION_FAILED)
			.entity(ex.getMessage())
			.build();

	}

}
