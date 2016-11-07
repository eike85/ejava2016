/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;

import ejava.week04.bean.NoteBean;
import ejava.week04.entity.Notes;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author NayLA
 */

@SessionScoped
@Named
public class PostedNotesView implements Serializable {
  
    @Inject 
    NoteBean noteBean;
    
    private static final long serialVersionUID = 1L;
      
    public Collection<Notes> getPostedNotes() {
         Collection<Notes> noteList = noteBean.findAllNotes();
             
       //setNoteList(noteList);/* Important to set data after retrieving from DB. */
        
        if (noteList.size() > 0) {
            System.out.println("Database connection successful ");
            System.out.println("Notes retrieved... ");
        }

        for(Notes note: noteList){
            
            System.out.println(note.getTitle()+"\r\n");
            System.out.println(note.getPostedDateTime()+"\r\n");
            System.out.println(note.getUsers().getUserid()+"\r\n");
            System.out.println(note.getCategory()+"\r\n");
            System.out.println(note.getContent()+"\r\n");
        }

        return noteList;
                     
    }
    
}
