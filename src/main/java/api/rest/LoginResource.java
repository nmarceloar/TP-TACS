


package api.rest;

import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import services.OfyUser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Path("/login")
public class LoginResource {
    
    private static final Logger LOG =
        Logger.getLogger(LoginResource.class.getCanonicalName());
    
    private OfyUserService ofyUserService = OfyUserService.getInstance();
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@Context HttpServletRequest request,
        @Context HttpServletResponse response,
        @FormParam("token") String token) {
    
        LOG.info("token parametro = " + token);
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            
            return Response.status(Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(
                    "Ok.Ya habia sesion. No se creo una nueva.\n"
                        + "Mas adelante este mensaje se reemplaza por un codigo 204")
                .build();
            
        }
        
        final TokenInfo tokenInfo = Facebook.tokenInfo(token);
        
        if (!tokenInfo.isValid()) {
            
            return Response.status(Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity("El token no es valido")
                .build();
            
        }
        
        synchronized (session = request.getSession(true)) {
            
            session.setAttribute(SessionUtils.TOKEN, token);
            session.setAttribute(SessionUtils.EXPIRATION_DATE,
                tokenInfo.getExpirationDate());
            session.setAttribute(SessionUtils.USER_ID, tokenInfo.getUserId());
            
        }
        
        LOG.info("NUEVA SESION " + session.getId());
        LOG.info("---->" + "empieza: " +
            DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
                .print(DateTime.now()));
        LOG.info("---->" + "termina: " +
            DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
                .print(tokenInfo.getExpirationDate()));
        
        if (ofyUserService.exists(tokenInfo.getUserId())) {
            
            return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity("Inicio de sesion Ok. No se creo un usuario nuevo.")
                .build();
            
        }
        
        return Response.status(Response.Status.OK)
            .type(MediaType.APPLICATION_JSON)
            .entity(
                new JsonResponse(
                    200,
                    "Inicio de sesion OK. Se creo un nuevo usuario",
                    this.ofyUserService.createUser(Facebook.getUserDetails(token))))
            .build();
        
        // me estaria faltando un caso mas, despues lo termino.
        
    }
    
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    class JsonResponse {
        
        // simplemente una "vista" o "contenedor" de respuesta. No representa
        // nada a nivel dominio. Es un DTO.
        
        private int status;
        
        private String message;
        
        private Object entity;
        
        public JsonResponse(int status, String message, Object entity) {
        
            this.status = status;
            this.message = message;
            this.entity = entity;
            
        }
        
    }
    
}
