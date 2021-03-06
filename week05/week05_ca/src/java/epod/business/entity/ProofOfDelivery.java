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
@Entity(name = "pod")
public class ProofOfDelivery {
    
    @Id
    @Column(name = "pod_Id")
    private int podId;
    
    private String note;
    
    private byte[] image;
    
    @Column(name = "delivery_date")
    private Date deliveryDate;
    
    @Column(name = "ack_id")
    private int ackId;
    
    @OneToOne
    @JoinColumn(name = "pkg_Id", referencedColumnName = "pkg_Id")
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
    
    @Override
    public String toString() {
        return "ProofOfDelivery{" + "podId=" + podId + ", note=" + note + ", image=" + image + ", deliveryDate=" + deliveryDate + ", ackId=" + ackId + '}';
    }
 
}
