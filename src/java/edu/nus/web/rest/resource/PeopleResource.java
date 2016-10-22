/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.resource;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/people")
public class PeopleResource {

    @POST
    public Response post() {
        
        // TODO Implementation here
        return Response.ok("Inside people resource POST").build();
    }
     
    @GET
    public Response findPeople() {
        // TODO Implementation here
        return Response.ok("Inside people resource GET").build();
    }
}
