/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.resource;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.web.rest.task.GetAppointmentsTask;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @EJB 
    private PeopleBean peopleBean;
    
    @Resource(mappedName = "concurrent/myThreadPool")
    private ExecutorService executorService;
    //private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAppointment(@Suspended
    final AsyncResponse asyncResponse, @PathParam(value = "email")
    final String email) {
        
        GetAppointmentsTask appointmentsTask = new GetAppointmentsTask(peopleBean, email);
        appointmentsTask.setAsync(asyncResponse);
        executorService.execute(appointmentsTask);
    }
}
