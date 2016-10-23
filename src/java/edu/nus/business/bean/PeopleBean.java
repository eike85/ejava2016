/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.business.bean;

import edu.nus.web.rest.entity.Appointment;
import edu.nus.web.rest.entity.People;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

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
        
        String uuid = UUID.randomUUID().toString().substring(0,8);
        people.setPeopleId(uuid);
        
        EntityManager em = emf.createEntityManager();
        em.persist(people);
        em.close();
        
        System.out.println("Saving finished");
    }
    public Collection<Appointment> findAllAppointments(String pid) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Appointment> query = em.createNamedQuery("Appointment.findByPeopleId", Appointment.class);
        
        query.setParameter("pid", pid);
        List<Appointment> resultList = query.getResultList();
        for (Appointment appointment : resultList) {
            System.out.println(appointment);
        }
        return resultList;
    }
}
