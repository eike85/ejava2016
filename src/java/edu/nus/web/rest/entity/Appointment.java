/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author NayLA
 */

@Entity
@NamedQueries({
	@NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a")
        , @NamedQuery(name = "Appointment.findByAppointmentId", query = "SELECT a FROM Appointment a WHERE a.appt_id = :appt_id")
	, @NamedQuery(name = "Appointment.findByDescription", query = "SELECT d FROM Appointment d WHERE d.description = :description")
        , @NamedQuery(name = "Appointment.findByDate", query = "SELECT dt FROM Appointment dt WHERE dt.description = :description")
        , @NamedQuery(name = "Appointment.findByPeopleId", query = "SELECT appt_pid FROM Appointment appt_pid WHERE appt_pid.pid = :pid")})
public class Appointment implements Serializable{
    
    private static final long serializableUID = 1L;
    
    @Id
    private Integer appt_id;
    
    @Basic private String description;
    private Date appt_date;
    private Integer pid;
    
    public Appointment(){
        
        
    }

    public Integer getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(Integer appt_id) {
        this.appt_id = appt_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppt_date() {
        return appt_date;
    }

    public void setAppt_date(Date appt_date) {
        this.appt_date = appt_date;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }   
      
}
