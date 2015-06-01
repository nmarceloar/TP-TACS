/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import apis.RecommendationAPI;
import integracion.facebook.RecommendationBeanFB;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Recommendation;

/**
 *
 * @author Flavio L. Pietrolati
 */
@Path("/recommendations")
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationResource {
    
    private final RecommendationAPI srvRecom;
    
    @Inject
    public RecommendationResource(RecommendationAPI recsrv){
        this.srvRecom = recsrv;
    }
    
    @GET
    @Path("{userId}")
    @Produces("application/json")
    public List<Recommendation> getRecommendationsByUser(@PathParam("userId") String id){
        // Salvo la opcion de que devuelva una lista vacia
        if (srvRecom.getRecommendationsOfUser(Long.parseLong(id)) == null){
            return new ArrayList<>();
        }
        return srvRecom.getRecommendationsOfUser(Long.parseLong(id));
    }
    
    @GET
    @Path("one/{idRec}")
    @Produces("application/json")
    public List<Recommendation> getRecommendationById(@PathParam("idRec") String id){
        return srvRecom.getRecommendationsOfUser(Integer.parseInt(id));
    }
    
    @POST
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
//    public Response assignRecommendation(Recommendation rec) {
//        srvRecom.saveRecommendation(rec);
//        String result = "Recomendacion asignada correctamente";
//        return Response.status(201)
//                .entity(result).build();
//    }
    /**
     * Modifico el post de recomendacion para que el nombre completo y ciudad
     * de partida y destino se completen en el servidor y no en la vista
     */
    public Response assignRecommendation(RecommendationBeanFB rec, @PathParam("userId") String id){
        srvRecom.instanceAndSaveRecommendation(rec, Long.parseLong(id));
        String result = "Recomendacion asignada correctamente";
        return Response.status(201)
                .entity(result).build();
    }
    
}
