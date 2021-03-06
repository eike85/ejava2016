/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import epod.business.beans.DeliveryBean;
import epod.business.entity.Delivery;
import epod.business.entity.ProofOfDelivery;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import sun.misc.IOUtils;


/**
 *
 * @author swemon
 */

@RequestScoped
@Path("/upload")
public class UploadRest {
     
    @EJB
    private DeliveryBean deliveryBean;
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("image") InputStream uploadedInputStream,
        @FormDataParam("image") FormDataContentDisposition fileDetail,
        @FormDataParam("note") String note,
        @FormDataParam("podId") int podId,
        @FormDataParam("time") long time) throws IOException {
        
        ProofOfDelivery pod = new ProofOfDelivery();
        pod.setPodId(podId);
        pod.setNote(note);
        pod.setDeliveryDate(new Date(time));
        
        System.out.println(pod);
        
        try {
            byte[] buf = IOUtils.readFully(uploadedInputStream, -1, false);
            pod.setImage(buf);
            
            deliveryBean.updatePod(pod);
            
            // Upload to HQ
            new UploadRestClient().upload(fileDetail.getFileName(), podId, note, buf);
            return Response.ok().build();
        } catch (IOException ex) {
            Logger.getLogger(UploadRest.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } 
    }
}
