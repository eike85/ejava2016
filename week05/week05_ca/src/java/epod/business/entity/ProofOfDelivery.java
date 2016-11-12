/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epod.business.entity;


import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author swemon
 */
@Entity
public class ProofOfDelivery {
    
    @Id
    @Column(name = "pod_Id")
    private int podId;
    
    @Column(name = "pkg_id")
    private int pkgId;
    
    private String note;
    
    private String image;
    
    @Column(name = "delivery_date")
    private Date deliveryDate;
    
    @Column(name = "ack_id")
    private int ackId;
    
    @OneToOne
    @JoinColumn(name = "pkgId", referencedColumnName = "pkgId")
    private Delivery delivery;

    public int getPodId() {
        return podId;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setPodId(int podId) {
        this.podId = podId;
    }

    public int getPkgId() {
        return pkgId;
    }

    public void setPkgId(int pkgId) {
        this.pkgId = pkgId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getAckId() {
        return ackId;
    }

    public void setAckId(int ackId) {
        this.ackId = ackId;
    }
    
 
}
