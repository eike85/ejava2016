/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.web.rest.task;

import edu.nus.iss.ca.ejb.bean.PeopleBean;
import edu.nus.iss.ca.exception.ApplicationCustomException;
import edu.nus.iss.ca.jpa.entity.People;
import javax.ws.rs.container.AsyncResponse;

import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
public class CreatePersonTask implements Runnable {
    
    private String name;
    private String email;
    private PeopleBean peopleBean;
    
    private AsyncResponse async;

    public CreatePersonTask(String name, String email, PeopleBean peopleBean) {
        this.name = name;
        this.email = email;
        this.peopleBean = peopleBean;
    }
    
    public void setAsync(AsyncResponse async) {
        this.async = async;
    }

    @Override
    public void run() {
        Response resp = createPerson();
        async.resume(resp);
    }
    
    private Response createPerson() {
        System.out.print(">>> name " + name);
        System.out.print(">>> email " + email);
        
        People people = new People();
        people.setName(name);
        people.setEmail(email);
        try {
            peopleBean.save(people);
            return Response.status(Response.Status.CREATED).build();
        } catch (ApplicationCustomException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
