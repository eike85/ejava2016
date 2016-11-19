/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.endPoints;

import epod.business.ejb.DeliveryBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */
@RequestScoped
@Path("/callback")
public class CallbackEndPoint {
    @EJB
    private DeliveryBean deliveryBean;
    
    @GET
    public Response updateAckId(@QueryParam("podId") int podId, @QueryParam("ackId") int ackId) {
    
        System.out.println(">>PodId " + podId);
        System.out.println(">>AckId " + ackId);
        
        deliveryBean.updateAckId(podId, ackId);
        return Response.ok().build();
    }
}
