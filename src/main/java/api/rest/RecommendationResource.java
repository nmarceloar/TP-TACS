/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.rest;

import apis.RecommendationAPI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Recommendation> getRecomendacionesDelUsuario(@PathParam("userId") String id){
        return srvRecom.getRecommendationsOfUser(Integer.parseInt(id));
    }
    
}
