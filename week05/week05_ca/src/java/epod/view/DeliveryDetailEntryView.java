package epod.view;

import epod.business.ejb.DeliveryBean;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author NayLA
 */

//@SessionScoped
@RequestScoped
@Named
public class DeliveryDetailEntryView implements  Serializable  {
    
    @Inject
    DeliveryBean  deliveryDetails;
    
    private String name;
    private String address;
    private String phone;
    
    public DeliveryBean getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryBean deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
  
    public String enterDetails(){
              
        deliveryDetails.enterDeliveryDetails(name,phone,address);

        return null;
    }
    
    
    public String deliveryDetailEntryPage(){
        
        return ("/deliveryDetails");
    }
      
}
