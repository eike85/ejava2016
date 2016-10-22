/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.business.bean;

import edu.nus.web.rest.entity.People;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author swemon
 */
@Stateless
public class PeopleBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
   
    @PersistenceUnit EntityManagerFactory emf;
    
    public PeopleBean() {
        
    }
    
    public void save(People people) {
        EntityManager em = emf.createEntityManager();
        em.persist(people);
        em.close();
        
        System.out.println("Saving finished");
    }
}
