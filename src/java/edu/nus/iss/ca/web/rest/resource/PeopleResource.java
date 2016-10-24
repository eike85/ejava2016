/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.resource;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.jpa.entity.People;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import edu.nus.iss.ca.exception.ApplicationCustomException;
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
        executorService.submit(new Runnable() {
            public void run() {
                asyncResponse.resume(doCreate(name, email));
            }
        });
    }

    private Response doCreate(@FormParam("name") String name, @FormParam("email") String email) {
        System.out.print(">>> name " + name);
        System.out.print(">>> email " + email);
        
        People people = new People();
        people.setName(name);
        people.setEmail(email);
        try {
            peopleBean.save(people);
            return Response.status(Status.CREATED).build();
        } catch (ApplicationCustomException ex) {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void findPeople(@Suspended
    final AsyncResponse asyncResponse, @QueryParam(value = "email")
    final String email) {
        executorService.submit(new Runnable() {
            public void run() {
                asyncResponse.resume(doFindPeople(email));
            }
        });
    }

    private Response doFindPeople(@QueryParam("email") String email) {
        Collection<People> people = peopleBean.findWithEmail(email);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (People person : people) {
            JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
                    createObjectBuilder.add("id", person.getPeopleId())
                    .add("name",person.getName())
                    .add("email", person.getEmail());
                    
            arrayBuilder.add(createObjectBuilder);
        }
        return Response.ok(arrayBuilder.build()).build();
    }
}
