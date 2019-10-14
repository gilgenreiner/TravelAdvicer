package service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import bll.Branche;
import bll.Location;
import dal.BrancheDAL;
import dal.LocationDAL;

@Path("locationDetail")
public class LocationDetail {

	@GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") String id) {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            response.entity(new Gson().toJson(LocationDAL.getById(id)));
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
    public Response newBook(Location new_loc) throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        System.out.println("======================NEW Location: ");
        
        /*
         * Pflichfelder
         * Besitzer mit gültiger UUID
         * 
         */
        new_loc.generateUUID();
        
        System.out.println("ID: " + new_loc.getId());
        System.out.println("Besitzer: " + new_loc.getBesitzer());
        try {
        	if(new_loc.getBesitzer() == null)
        		throw new Exception("Location braucht einen Besitzer");
        	LocationDAL.create(new_loc);
        	response.status(Response.Status.CREATED);
            response.entity(LocationDAL.getById(new_loc.getId()));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }

    @POST
    @Path("test")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response test(Location new_loc) throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);

        new_loc.generateUUID();
        
        System.out.println("ID: " + new_loc.getId());
        System.out.println("Besitzer: " + new_loc.getBesitzer());

        try {
        	if(new_loc.getBesitzer() == null || new_loc.getBesitzer().getId() == null)
        		throw new Exception("Location braucht einen Besitzer mit gültiger UUID");
        	response.status(Response.Status.CREATED);
            response.entity(new_loc);
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }
    
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBook(Location new_loc) throws IOException {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        
        try {                    	
        	LocationDAL.update(new_loc.getId(), new_loc);
        	response.status(Response.Status.OK);
            response.entity(LocationDAL.getById(new_loc.getId()));
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
        	LocationDAL.delete(delete_id);
        	response.status(Response.Status.NO_CONTENT);
            response.entity("Location deleted");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }
}
