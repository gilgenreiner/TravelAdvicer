package service;

import java.io.IOException;
import java.sql.Timestamp;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bll.Besitzer;
import bll.Besuch;
import bll.Branche;
import dal.BesitzerDAL;
import dal.BesuchDAL;
import dal.BrancheDAL;

@Path("locationBesuchDetail")
public class BesuchDetail {
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response newBook(Besuch new_besuch) throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        System.out.println("======================NEW Besuch: ");

        new_besuch.generateId();
        new_besuch.setZeitpunkt(new Timestamp(System.currentTimeMillis()));
        try {
        	BesuchDAL.create(new_besuch);
        	response.status(Response.Status.CREATED);
            response.entity(new_besuch);
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
        }

        return response.build();
    }
}
