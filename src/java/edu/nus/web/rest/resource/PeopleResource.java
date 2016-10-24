/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.resource;

import edu.nus.business.bean.PeopleBean;
import edu.nus.web.rest.entity.People;
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


/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/people")
public class PeopleResource {
  
    @EJB 
    private PeopleBean peopleBean;

    @POST
    public Response post(@FormParam("name") String name, @FormParam("email") String email) {
       
        System.out.print(">>> name " + name);
        System.out.print(">>> email " + email);
        
        People people = new People();
        people.setName(name);
        people.setEmail(email);
        
        peopleBean.save(people);
        
        return Response.ok("Inside people resource POST").build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPeople(@QueryParam("email") String email) {
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
