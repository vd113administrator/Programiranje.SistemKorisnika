/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.alatke;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

/**
 *
 * @author Mikec
 */
public final class Base64Swapper {
    private Base64Swapper(){}
    
    public static String encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    public static String encode(Serializable srz) throws IOException{
        return encode(SRZSerializer.marshall(srz));
    }
    
    public static String encode(String srz) throws IOException{
        return encode(SRZSerializer.marshall(srz));
    }
    
    public static byte[] decodeToBytes(String base64){
        return Base64.getDecoder().decode(base64);
    }
    
    public static String decodeToString(String base64){
        return new String(decodeToBytes(base64));
    }
    
    public static Serializable decodeToSerializable(String base64) throws IOException, ClassNotFoundException{
        return SRZSerializer.unmarshal(decodeToBytes(base64)); 
    }
}
