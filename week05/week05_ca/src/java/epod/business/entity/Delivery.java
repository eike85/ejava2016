/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epod.business.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author swemon
 */
@Entity
public class Delivery implements Serializable {
    
    @Id
    @Column(name = "pkg_id")
    private int pkgId;
    
    private String name;
    
    private String address;
    
    private String phone;
    
    @Column(name = "create_Date")
    private Date createDate;
    
    @OneToOne(mappedBy = "delivery")
    private ProofOfDelivery proofOfDelivery;
    
    @Override
    public String toString() {
        return "Delivery{" + "pkgId=" + pkgId + ", name=" + name + ", address=" + address + ", phone=" + phone + ", createDate=" + createDate + ", proofOfDelivery=" + proofOfDelivery + '}';
    }

    public ProofOfDelivery getProofOfDelivery() {
        return proofOfDelivery;
    }

    public void setProofOfDelivery(ProofOfDelivery proofOfDelivery) {
        this.proofOfDelivery = proofOfDelivery;
    }

    public int getPkgId() {
        return pkgId;
    }

    public void setPkgId(int pkgId) {
        this.pkgId = pkgId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
