/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.entity;


import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author swemon
 */
@Entity
public class Notes {
    
    @Column(name = "note_id")
    @Id
    private int noteId;
    
    private String title;
    
    private String category;
    
    private String content;
    
    @ManyToOne
    @JoinColumn(name ="userid", referencedColumnName="userid")
    private Users users;
    
    @Column(name = "posted_DateTime")
    private Date postedDateTime;
     
    public Notes(){
    }
    
    public int getNoteId() {
        return noteId;
    }
    public void setNoteId(int noteId) {
        this.noteId = noteId;
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
    
    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getPostedDateTime() {
        return postedDateTime;
    }
    public void setPostedDateTime(Date postedDateTime) {
        this.postedDateTime = postedDateTime;
    }
}
