/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.resource;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.jpa.entity.Appointment;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @EJB 
    private PeopleBean peopleBean;
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAppointment(@Suspended
    final AsyncResponse asyncResponse, @PathParam(value = "email")
    final String email) {
        executorService.submit(new Runnable() {
            public void run() {
                asyncResponse.resume(doGetAppointment(email));
            }
        });
    }

    private Response doGetAppointment(@PathParam("email") String email) {
        Collection<Appointment> appointments = peopleBean.findAllAppointments(email);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Appointment appointment : appointments) {
            JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
                    createObjectBuilder.add("appointmentId", appointment.getAppointmentId())
                    .add("dateTime",appointment.getAppointmentDate().getTime())
                    .add("description", appointment.getDescription())
                    .add("personId", appointment.getPeople().getPid());
                    
            arrayBuilder.add(createObjectBuilder);
        }
        return Response.ok(arrayBuilder.build()).build();
    }
    
}
