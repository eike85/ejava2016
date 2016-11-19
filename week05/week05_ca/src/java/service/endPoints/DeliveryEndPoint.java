package service.endPoints;

import epod.business.ejb.DeliveryBean;
import epod.business.entity.Delivery;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author swemon
 */

@RequestScoped
@Path("/api/items")
public class DeliveryEndPoint {
     
    @EJB
    private DeliveryBean deliveryBean;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveryDetails() {
        
        List<Delivery> allDelivery = deliveryBean.getAllDelivery();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Delivery delivery : allDelivery) {
            JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
                    createObjectBuilder.add("teamId", "1b059004")
                    .add("podId",delivery.getProofOfDelivery().getPodId())
                    .add("address", delivery.getAddress())
                    .add("name", delivery.getName())
                    .add("phone", delivery.getPhone());
                    
            arrayBuilder.add(createObjectBuilder);
        }
        return Response.ok(arrayBuilder.build()).build();
    }
}
