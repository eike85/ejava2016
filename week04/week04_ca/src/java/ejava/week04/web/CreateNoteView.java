/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.web;

import ejava.week04.bean.NoteBean;
import ejava.week04.entity.Notes;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author swemon
 */

@Named
@RequestScoped
public class CreateNoteView {
    
    @Inject
    private NoteBean noteBean;
    
    private String title;
    private String content;
    private String category;

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String createNewNote() {
        
        System.out.println("In create method");
        System.out.println(title);
        System.out.println(category);
        System.out.println(content);
        
        Notes note = new Notes();
        note.setTitle(title);
        note.setCategory(category);
        note.setContent(content);
        noteBean.createNote(note);
        return "/secure/menu";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
