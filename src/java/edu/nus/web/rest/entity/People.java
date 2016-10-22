/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.web.rest.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author NayLA
 */

@Entity
public class People implements Serializable{
    
    private static final long serializableUID = 1L;
    
    @Id
    private Integer pid;
    
    @Basic private String name;
    private String email;
    
    public People(){
        
        
    }
    
}
