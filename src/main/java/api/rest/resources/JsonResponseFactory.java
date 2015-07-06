package api.rest.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

class JsonResponseFactory {

	public static Response okJsonFrom(Object entity) {

		return Response.ok()
			.type(MediaType.APPLICATION_JSON)
			.entity(entity)
			.build();
	}

	public static Response errorJsonFrom(String message) {

		return Response.serverError()
			.type(MediaType.APPLICATION_JSON)
			.entity(message)
			.build();
	}

}