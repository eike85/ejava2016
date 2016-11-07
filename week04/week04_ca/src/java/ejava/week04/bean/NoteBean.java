/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import ejava.week04.entity.Notes;
import ejava.week04.entity.Users;
import ejava.week04.web.CreateNoteView;
import java.sql.Date;
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
    
    //private CreateNoteView newNote;
    //private int noteId;  
    //private String title;    
    //private String category;    
    //private String content;  
    //private String userid;
    //private Date posted_datetime;
      
    
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
        
    public void createNote(){
        

        //noteId = newNote.getNoteId();
        //title = newNote.getTitle();
        //category = newNote.getCategory();    
        //content = newNote.getContent();  
        //userid = newNote.getUserid();
        //posted_datetime = newNote.getPosted_datetime();
     
        EntityManager em = emf.createEntityManager();
        //TypedQuery<Notes> query = em.createQuery("INSERT INTO NOTES (note_id,title,category,content,userid,posted_datetime) "
        //        + "VALUES (noteId,title,category,content,userid,posted_datetime)", Notes.class);
        
        
        System.out.println("New note created! ");
        

        
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
