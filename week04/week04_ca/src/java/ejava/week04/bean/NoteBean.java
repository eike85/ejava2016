/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import ejava.week04.entity.Notes;
import ejava.week04.entity.Users;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 *
 * @author swemon
 */
@Stateless
public class NoteBean {
    
@PersistenceUnit
    private EntityManagerFactory emf;
        
    
        public Collection<Users> findAllUsers() {

            // TODO remove on final submit
            // just to check whether connection is successful or not
        EntityManager em = emf.createEntityManager();
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u", Users.class);
        
        List<Users> resultList = query.getResultList();
        for (Users users : resultList) {
            System.out.println("User>>" + users);
        }
        return resultList;
    }
}
