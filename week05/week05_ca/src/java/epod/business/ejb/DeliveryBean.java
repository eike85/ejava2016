package epod.business.ejb;

import epod.business.entity.Delivery;
import epod.business.entity.ProofOfDelivery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void updatePod(ProofOfDelivery pod) {

        TypedQuery<ProofOfDelivery> createQuery = em.createQuery("Select p from pod p where p.podId = :podId", ProofOfDelivery.class);

//      ProofOfDelivery find = em.find(ProofOfDelivery.class, pod.getPodId());
        createQuery.setParameter("podId", pod.getPodId());
        ProofOfDelivery existingPod = createQuery.getSingleResult();

        existingPod.setNote(pod.getNote());
        existingPod.setImage(pod.getImage());
        existingPod.setDeliveryDate(pod.getDeliveryDate());

        em.persist(existingPod);
    }
    
    public ProofOfDelivery findPod(int podId) {
        ProofOfDelivery pod = em.find(ProofOfDelivery.class, podId);    
        return pod;
    }

    public void updateAckId(int podId, int ackId) {
        ProofOfDelivery pod = em.find(ProofOfDelivery.class, podId);
        
        if (null == pod) {
            System.out.println("Pod does not exist with id " + podId);
        } else {
            pod.setAckId(ackId);
            System.out.println("Saving ackid");
        }
//        TypedQuery<ProofOfDelivery> createQuery = em.createQuery("Select p from pod p where p.podId = :podId", ProofOfDelivery.class);
//
//        createQuery.setParameter("podId", podId);
//        ProofOfDelivery existingPod = createQuery.getSingleResult();
//        existingPod.setAckId(podId);
//        em.persist(existingPod);
    }

    public Delivery enterDeliveryDetails(String name, String ph, String Addr) {

        Delivery d = new Delivery();
        d.setName(name);
        d.setPhone(ph);
        d.setAddress(Addr);

        em.persist(d);

        return d;
    }

}
