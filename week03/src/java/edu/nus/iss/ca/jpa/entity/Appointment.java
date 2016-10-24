/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca.jpa.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author NayLA
 */

@Entity
@NamedQueries({
         @NamedQuery(name = "Appointment.findByEmail", query = "select a from Appointment a inner join a.people p WHERE p.email = :email")})
public class Appointment implements Serializable{
    
    private static final long serializableUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appt_id")
    private Integer appointmentId;
    
    @Basic 
    private String description;
    
    @Column(name = "appt_date")
    private Date appointmentDate;
    
    @JoinColumn(name="pid", referencedColumnName = "pid")
    @ManyToOne
    private People people;

    @Override
    public String toString() {
        return "Appointment{" + "appt_id=" + appointmentId + ", description=" + description + ", appt_date=" + appointmentDate + ", people=" + people + '}';
    }
    
    public Appointment(){
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
      
}
