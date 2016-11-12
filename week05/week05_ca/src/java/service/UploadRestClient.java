/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.ByteArrayInputStream;
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
public class UploadRestClient {

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
                            .field("callback", "http://10.10.3.184/epod/callback", MediaType.TEXT_PLAIN_TYPE)
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
}
