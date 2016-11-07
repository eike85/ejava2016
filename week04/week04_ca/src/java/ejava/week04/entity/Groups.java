/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author swemon
 */
@Entity
public class Groups implements Serializable {

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    
    @EmbeddedId
    private GroupId combinedKey;
    
    // one to one relationship with Users/userid
    @ManyToOne
    private Users users;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.combinedKey.getUserId());
        hash = 29 * hash + Objects.hashCode(this.combinedKey.getGroupId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupId other = (GroupId) obj;
        if (!Objects.equals(this.combinedKey.getUserId(), other.getUserId())) {
            return false;
        }
        if (!Objects.equals(this.combinedKey.getGroupId(), other.getGroupId())) {
            return false;
        }
        return true;
    }

    public GroupId getCombinedKey() {
        return combinedKey;
    }

    public void setCombinedKey(GroupId combinedKey) {
        this.combinedKey = combinedKey;
    }
    
}
