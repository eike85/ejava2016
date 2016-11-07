/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

/**
 *
 * @author swemon
 */
@Embeddable
public class GroupId implements Serializable {
    
    
    private String userId;
    
    private String groupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

   
}
