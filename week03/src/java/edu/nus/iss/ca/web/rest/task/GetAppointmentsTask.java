/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.task;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.jpa.entity.Appointment;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
public class GetAppointmentsTask implements Runnable {

    private PeopleBean peopleBean;
    private String email;
    private AsyncResponse async;
    
    public void setAsync(AsyncResponse async) {
        this.async = async;
    }

    public GetAppointmentsTask(PeopleBean peopleBean, String email) {
        this.peopleBean = peopleBean;
        this.email = email;
    }
    
    @Override
    public void run() {
        Response resp = getAppointments();
        async.resume(resp);
    }
    
    private Response getAppointments() {
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
