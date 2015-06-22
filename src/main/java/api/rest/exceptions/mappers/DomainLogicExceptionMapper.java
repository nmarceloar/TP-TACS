


package api.rest.exceptions.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import api.rest.exceptions.DomainLogicException;

@Provider
public class BusinessLogicExceptionMapper implements
    ExceptionMapper<DomainLogicException> {
    
    @Override
    public Response toResponse(DomainLogicException exception) {
    
        return Response.status(Status.PRECONDITION_FAILED)
            .type(MediaType.APPLICATION_JSON)
            .entity(exception.getMessage())
            .build();
        
    }
    
}
