/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.resource;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import edu.nus.iss.ca.web.rest.task.CreatePersonTask;
import edu.nus.iss.ca.web.rest.task.FindPeopleTask;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;


/**
 *
 * @author swemon
 * 
 */
@RequestScoped
@Path("/people")
public class PeopleResource {
  
    @EJB 
    private PeopleBean peopleBean;
    
    @Resource(mappedName = "concurrent/myThreadPool")
    private ExecutorService executorService;

    @POST
    public void create(@Suspended
    final AsyncResponse asyncResponse, @FormParam(value = "name")
    final String name, @FormParam(value = "email")
    final String email) {
        CreatePersonTask createPeopleTask = new CreatePersonTask(name, email, peopleBean);
        createPeopleTask.setAsync(asyncResponse);
        executorService.execute(createPeopleTask);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void findPeople(@Suspended
    final AsyncResponse asyncResponse, @QueryParam(value = "email")
    final String email) {
        FindPeopleTask findPeopleTask = new FindPeopleTask(email, peopleBean);
        findPeopleTask.setAsync(asyncResponse);
        executorService.execute(findPeopleTask);
    }
}
