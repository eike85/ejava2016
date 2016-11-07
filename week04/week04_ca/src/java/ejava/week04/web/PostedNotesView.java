/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;

import ejava.week04.bean.NoteBean;
import ejava.week04.entity.Notes;
import ejava.week04.entity.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
    
    private int noteid;
    private String title;
    private String category;
    private String content;
    private String userid;

    private Collection<Notes> noteList;
    

    public NoteBean getNoteBean() {
        return noteBean;
    }

    public void setNoteBean(NoteBean noteBean) {
        this.noteBean = noteBean;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Collection<Notes> getNoteList() {
        return noteList;
    }

    public void setNoteList(Collection<Notes> noteList) {
        this.noteList = noteList;
    }
    
      
    public String getPostedNotes() {

           
        Collection<Notes> noteList = noteBean.findAllNotes();
             
        setNoteList(noteList);/* Important to set data after retrieving from DB. */
        
        if (noteList.size() > 0) {
            System.out.println("Database connection successful ");
            System.out.println("Notes retrieved... ");
        }

        for(Notes note: noteList){
            
            System.out.println(note.getTitle()+"\r\n");
            System.out.println(note.getPostedDateTime()+"\r\n");
            System.out.println(note.getUserid()+"\r\n");
            System.out.println(note.getCategory()+"\r\n");
            System.out.println(note.getContent()+"\r\n");
        }

        return ("/secure/postedNotes.xhtml");
                     
    }
    
}
