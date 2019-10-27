package service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import bll.Aktion;
import bll.Location;
import dal.LocationDAL;
import dal.PraemienDAL;

@Path("praemienDetail")
public class PraemienDetail {
	@GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") String id) {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            response.entity(new Gson().toJson(PraemienDAL.getById(id)));
        } catch (Exception e) {
            response.status(Response.Status.NOT_FOUND);
            response.entity("[ERROR] " + e.getMessage());
        }
        System.out.println("======================webservice GET called");
        return response.build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response newBook(Aktion new_akt) throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        System.out.println("======================NEW Prämie: ");
        
        new_akt.generateUUID();
        /*
         * Prämie braucht übergeordnete Location
         */
        try {
        	PraemienDAL.create(new_akt);
        	response.status(Response.Status.CREATED);
            response.entity(PraemienDAL.getById(new_akt.getId()));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }

    
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBook(Aktion upd_akt) throws IOException {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        
        try {                    	
        	

        	PraemienDAL.update(upd_akt.getId(), upd_akt);
        	response.status(Response.Status.OK);
            response.entity(PraemienDAL.getById(upd_akt.getId()));
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
        	PraemienDAL.delete(delete_id);
        	response.status(Response.Status.NO_CONTENT);
            response.entity("Prämie deleted");
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
