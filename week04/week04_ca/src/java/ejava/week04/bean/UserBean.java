/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.bean;

import ejava.week04.entity.GroupId;
import ejava.week04.exception.UserExistedException;
import ejava.week04.entity.Groups;
import ejava.week04.entity.Users;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
public class UserBean {
    @PersistenceUnit
    private EntityManagerFactory emf;

    public void createUser(Users user) throws UserExistedException, NoSuchAlgorithmException {
        
        Users existingUser = null;
        existingUser = findUserById(user.getUserid());
        
        if (existingUser != null) {
            System.out.println("User is already existed.");
            throw new UserExistedException();
        }
        EntityManager em = emf.createEntityManager();
        
        // hash password
        
        
        Groups groups = new Groups();
        groups.setUsers(user);
        
        GroupId groupId = new GroupId();
        groupId.setGroupId("USER_ROLE");
        groupId.setUserId(user.getUserid());
        groups.setCombinedKey(groupId);

        em.persist(groups);
        em.persist(user);
        em.close();
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
}
