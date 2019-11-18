package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import dal.BrancheDAL;
import dal.RezensionenDAL;

@Path("rezensionenList")
public class RezensionenList {
	@Context
    private UriInfo context;
	
	public RezensionenList() {
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
    	System.out.println("======================webservice GET called");
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            response.entity(new Gson().toJson(RezensionenDAL.getAll()));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }
        System.out.println("======================webservice GET called");
        return response.build();
    }
}
