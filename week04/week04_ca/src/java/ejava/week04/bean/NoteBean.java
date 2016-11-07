/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import ejava.week04.entity.Notes;
import ejava.week04.entity.Users;
import ejava.week04.web.CreateNoteView;
import ejava.week04.web.UserSession;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
public class NoteBean {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Inject
    private UserSession userSession;
    
    public Collection<Users> findAllUsers() {

        // TODO remove on final submit
        // just to check whether connection is successful or not
        EntityManager em = emf.createEntityManager();
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u", Users.class);

        List<Users> resultList = query.getResultList();
        for (Users users : resultList) {
            System.out.println("User>>" + users);
        }
        
        em.close();
        
        return resultList;
    }
        
    public void createNote(Notes note){
        EntityManager em = emf.createEntityManager();
        System.out.println("Login User Name" + userSession.getUsername());
        
        String userName = userSession.getUsername();
        Users user = findUserById(userName);
        note.setUsers(user);
        java.util.Date date = new java.util.Date();
        note.setPostedDateTime(new java.sql.Date(date.getTime()));
        em.persist(note);
        
        em.close();
        System.out.println("New note created! ");
    }
    
    public Users findUserById(String userId) {
        
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u where u.userid= :userid", Users.class);
        query.setParameter("userid", userId);
        List<Users> userList = query.getResultList();
        
        Users user = null;
        if (userList.size() > 0) {
            user =  userList.get(0);
        }
        em.close();
        return user;
    }
        
    public Collection<Notes> findAllNotes() {
        
        EntityManager em = emf.createEntityManager();
        TypedQuery<Notes> query = em.createQuery("SELECT n FROM Notes n", Notes.class);
        
        List<Notes> noteList = query.getResultList();
        
        for (Notes notes : noteList) {
            System.out.println("Notes >> " + notes);
        }
        
        em.close();
        
        return noteList;
    }
}
