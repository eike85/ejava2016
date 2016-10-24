/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.task;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.jpa.entity.People;
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
public class FindPeopleTask implements Runnable {
    
    private String email;
    private PeopleBean peopleBean;
    
    private AsyncResponse async;

    public FindPeopleTask(String email, PeopleBean peopleBean) {
        this.email = email;
        this.peopleBean = peopleBean;
    }
    
    public void setAsync(AsyncResponse async) {
        this.async = async;
    }

    @Override
    public void run() {
        Response resp = findPeople();
        async.resume(resp);
    }
    
     private Response findPeople() {
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
