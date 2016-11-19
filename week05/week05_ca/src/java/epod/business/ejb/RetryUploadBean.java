/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epod.business.ejb;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author swemon
 */
@Stateless
public class RetryUploadBean {
    
    @Schedule(second="30")
    public void execute() {
        
    }
}
