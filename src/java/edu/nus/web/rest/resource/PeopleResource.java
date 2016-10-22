/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.resource;

import edu.nus.business.bean.PeopleBean;
import edu.nus.web.rest.entity.People;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
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
    
    @EJB 
    private PeopleBean peopleBean;

    @POST
    public Response post(@FormParam("") String name, @FormParam("") String email) {
        
        // TODO Implementation here
        System.out.print(">>> name " + name);
        System.out.print(">>> email " + email);
        
        People people = new People();
        people.setName(name);
        people.setEmail(email);
        
        peopleBean.save(people);
        
        return Response.ok("Inside people resource POST").build();
    }
     
    @GET
    public Response findPeople() {
        // TODO Implementation here
        return Response.ok("Inside people resource GET").build();
    }
}
