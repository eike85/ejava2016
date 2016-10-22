/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.resource;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @Path("{aid}")
    public Response getAppointment(@PathParam("aid") Integer id) {
        // TODO implementation
        return Response.ok("Inside AppointmentResource").build();
    }
    
}
