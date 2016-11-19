package service.endPoints;

import epod.business.ejb.DeliveryBean;
import epod.business.entity.ProofOfDelivery;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import sun.misc.IOUtils;


/**
 *
 * @author swemon
 */

@RequestScoped
@Path("/upload")
public class UploadEndPoint implements Callable<Integer> {
     
    @EJB
    private DeliveryBean deliveryBean;
    
    UploadData uploadData;
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("image") InputStream uploadedInputStream,
        @FormDataParam("image") FormDataContentDisposition fileDetail,
        @FormDataParam("note") String note,
        @FormDataParam("podId") int podId) throws IOException {
        
        ProofOfDelivery pod = new ProofOfDelivery();
        pod.setPodId(podId);
        pod.setNote(note);
        pod.setDeliveryDate(new Date(System.currentTimeMillis()));
        
        System.out.println(pod);
        
        try {
            byte[] buf = IOUtils.readFully(uploadedInputStream, -1, false);
            pod.setImage(buf);
            
            deliveryBean.updatePod(pod);
            
            uploadData = new UploadData();
            uploadData.setFileName(fileDetail.getFileName());
            uploadData.setPodId(podId);
            uploadData.setNote(note);
            uploadData.setBuf(buf);
            
            ExecutorService threadPool = Executors.newSingleThreadExecutor();
            Future<Integer> handle = threadPool.submit(this);
            
            Integer ackId = handle.get();
            System.out.println("Ack id" + ackId);
            
            return Response.ok().build();
        } catch (IOException ex) {
            Logger.getLogger(UploadEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (InterruptedException | ExecutionException ex) { 
            Logger.getLogger(UploadEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void upload(String fileName, int podId, String note, byte[] buf) {
             Client client = ClientBuilder.newBuilder()
				.register(MultiPartFeature.class)
				.build();

            MultiPart part = new MultiPart();

            FormDataBodyPart imgPart = new FormDataBodyPart("image", 
            new ByteArrayInputStream(buf),                            
                            MediaType.APPLICATION_OCTET_STREAM_TYPE);
            
            imgPart.setContentDisposition(
                            FormDataContentDisposition.name("image")
                            .fileName(fileName).build());

            MultiPart formData = new FormDataMultiPart()
                            .field("teamId", "1b059004", MediaType.TEXT_PLAIN_TYPE)
                            .field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
                            .field("callback", "http://10.10.3.184/ca3/callback", MediaType.TEXT_PLAIN_TYPE)
                            .field("note", note, MediaType.TEXT_PLAIN_TYPE)
                            .field("time", Long.toString(System.currentTimeMillis()), MediaType.TEXT_PLAIN_TYPE)
                            .bodyPart(imgPart);
            formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);


            //part.bodyPart(new FileDataBodyPart("image", 
                            //new File("/home/cmlee/Pictures/ca3.png")));

            WebTarget target = client.target("http://10.10.0.48:8080/epod/upload");
            Invocation.Builder inv = target.request();

            System.out.println(">>> part: " + formData);

            Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));

            System.out.println(">> call resp:" + callResp.getStatus());
    }

    @Override
    public Integer call() throws Exception {
        
        ProofOfDelivery pod = null;
        do {
            // upload(uploadData.getFileName(), uploadData.getPodId(), uploadData.getNote(), uploadData.getBuf());

            Thread.sleep(30 * 1000);
            System.out.println("Waiting 30 sec...");

            // Check whether ack is received or not
            pod = deliveryBean.findPod(uploadData.getPodId());
            
            // Just for testing randomly generate 0 to 4
            Random rand = new Random();
            int x = rand.nextInt(5);
            if (x == 3) {
                pod.setAckId(100);
            }
            
            System.out.println("ack id: " + pod.getAckId());
        } while(pod != null && pod.getAckId() == 0);
        
        System.out.println("done");
        return pod.getAckId();
    }
}

class UploadData {
    private String fileName;
    private int podId;
    private String note;
    private byte[] buf;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPodId() {
        return podId;
    }

    public void setPodId(int podId) {
        this.podId = podId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getBuf() {
        return buf;
    }

    public void setBuf(byte[] buf) {
        this.buf = buf;
    }

}
