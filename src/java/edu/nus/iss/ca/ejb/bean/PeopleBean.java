/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.ejb.bean;

import edu.nus.iss.ca.exception.ApplicationCustomException;
import edu.nus.iss.ca.jpa.entity.Appointment;
import edu.nus.iss.ca.jpa.entity.People;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.ejb.ApplicationException;
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
    @PersistenceUnit
    EntityManagerFactory emf;

    public PeopleBean() {
    }

    public void save(People people) throws ApplicationCustomException {
        
        EntityManager em = emf.createEntityManager();
        
        // Duplicate email check
        TypedQuery<People> query = em.createNamedQuery("People.findByEmail", People.class);
        query.setParameter("email", people.getEmail());
        List<People> resultList = query.getResultList();
        
        if ( resultList.size() > 0 ) {
            throw new ApplicationCustomException("Email is already used.");
        } else {
            people.setPeopleId(generatePeopleId());
        
            em.persist(people);
            System.out.println("Saving finished");
        }
        em.close();
    }

    public Collection<Appointment> findAllAppointments(String email) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Appointment> query = em.createNamedQuery("Appointment.findByEmail", Appointment.class);

        query.setParameter("email", email);
        List<Appointment> resultList = query.getResultList();
        for (Appointment appointment : resultList) {
            System.out.println(appointment);
        }
        return resultList;
    }

    public Collection<People> findWithEmail(String email) {

        EntityManager em = emf.createEntityManager();
        TypedQuery<People> query = em.createNamedQuery("People.findByEmail", People.class);

        query.setParameter("email", email);
        List<People> resultList = query.getResultList();
        for (People person : resultList) {
            System.out.println("Person>>" + person);
        }
        return resultList;
    }

    private String generatePeopleId() {
        // Create people id
        return  UUID.randomUUID().toString().substring(0, 8);
    }
}
