/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.week04.util;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author swemon
 */
public class Utils {
    
     /*
     * Returns a hexadecimal encoded SHA-256 hash for the input String.

     * @param data

     * @return

     */
    public static String getSHA256Hash(String data) {
        String result = null;
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     *
     * to a hexadecimal string. Note that this generates hexadecimal in upper
     * case.
     *
     * @param hash
     *
     * @return
     *
     */
    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
}
