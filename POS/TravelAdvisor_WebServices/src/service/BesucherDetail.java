package service;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bll.Besitzer;
import bll.Besucher;
import dal.BesitzerDAL;
import dal.BesucherDAL;

@Path("besucherDetail")
public class BesucherDetail {
	@POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response newBesitzer(Besucher new_bes) throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        
        try {
        	BesucherDAL.create(new_bes);
        	response.status(Response.Status.CREATED);
            response.entity(new_bes);
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteArticle(@PathParam("id") String delete_id)throws IOException {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);

        try {
        	BesucherDAL.delete(delete_id);
        	response.status(Response.Status.NO_CONTENT);
            response.entity("Branche deleted");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }
    
    
    @OPTIONS
    public Response preflight() {
    	Response.ResponseBuilder response = Response.status(Response.Status.OK);

        return response.build();
    }
}
