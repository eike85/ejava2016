/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.client;

import epod.business.ejb.DeliveryBean;
import epod.business.entity.ProofOfDelivery;
import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author swemon
 */
@Stateless
public class UploadRestClient implements Callable<Integer>{
    
    @EJB DeliveryBean deliveryBean;

    
    
    public void uploadWithRetry(String fileName, int podId, String note, byte[] buf) {
        
//        upload(fileName, podId, note, buf);
        try {
            
            
            // Check whether ack is received or not
            ProofOfDelivery pod = deliveryBean.findPod(podId);
            
            while (pod.getAckId() == 0) {
//                upload(fileName, podId, note, buf);
                Thread.sleep(30 * 1000); // 3 second
            }
            
            // if ack is not received, retry again
            System.out.println("Already got ack" + pod.getAckId());
            
        } catch (InterruptedException ex) {
            Logger.getLogger(UploadRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public Integer call() throws Exception {
        return 1;
    }
}
