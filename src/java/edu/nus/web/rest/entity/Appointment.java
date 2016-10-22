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

/**
 *
 * @author NayLA
 */

@Entity
public class Appointment implements Serializable{
    
    private static final long serializableUID = 1L;
    
    @Id
    private Integer appt_id;
    
    @Basic private String description;
    private Date appt_date;
    private Integer pid;
    
    public Appointment(){
        
        
    }
      
}
