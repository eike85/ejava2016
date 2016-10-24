/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author NayLA
 */
@Entity
@NamedQueries({
         @NamedQuery(name = "People.findByEmail", query = "Select p from People p where p.email = :email")})
public class People implements Serializable {

    private static final long serializableUID = 1L;
    @Id
    private String pid;
    @Basic
    private String name;
    private String email;
    @OneToMany(mappedBy = "people")
    private Collection<Appointment> appointment;

    public People() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Collection<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Collection<Appointment> appointment) {
        this.appointment = appointment;
    }

    public String getPeopleId() {
        return pid;
    }

    public void setPeopleId(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
