/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epod.business.beans;

import epod.business.entity.Delivery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 *
 * @author swemon
 */
@Stateless
public class DeliveryBean {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Delivery> getAllDelivery() {
        
        TypedQuery<Delivery> query = em.createQuery("Select d from Delivery d", Delivery.class);
        List<Delivery> resultList = query.getResultList();
        
        for (Delivery delivery : resultList) {
            System.out.println(delivery);
        }
        return resultList;
    }
    
}
