/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import epod.business.beans.DeliveryBean;
import java.io.Serializable;
import java.sql.Date;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author NayLA
 */

@SessionScoped
//@RequestScoped
@Named
public class DeliveryDetailEntryView implements  Serializable  {
    
    @Inject
    DeliveryBean  deliveryDetails;

    private int pkg_id;
    private String name;
    private String address;
    private String phone;
    //private Date   createDate;
    
    public DeliveryBean getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryBean deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public int getPkg_id() {
        return pkg_id;
    }

    public void setPkg_id(int pkg_id) {
        this.pkg_id = pkg_id;
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

//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
  
  
    public String enterDetails(){
              
        deliveryDetails.enterDeliveryDetails(pkg_id,name,phone,address);

        return null;
    }
    
    
    public String deliveryDetailEntryPage(){
        
        return ("/deliveryDetails");
    }
      
}
