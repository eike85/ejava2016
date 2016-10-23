/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.resource;

import edu.nus.business.bean.PeopleBean;
import edu.nus.web.rest.entity.Appointment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    
    @Path("{pid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("pid") String pid) {
        Collection<Appointment> appointments = peopleBean.findAllAppointments(pid);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Appointment appointment : appointments) {
            JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
                    createObjectBuilder.add("appointmentId", appointment.getAppt_id())
                    .add("dateTime",appointment.getAppt_date().getTime())
                    .add("description", appointment.getDescription())
                    .add("personId", appointment.getPeople().getPid());
                    
            arrayBuilder.add(createObjectBuilder);
        }
        return Response.ok(arrayBuilder.build()).build();
    }
    
}
