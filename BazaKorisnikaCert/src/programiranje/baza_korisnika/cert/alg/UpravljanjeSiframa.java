/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.alg;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * U instanci ove klase upravlja se algoritmima za hash i salt sifre. <br><br>
 * Polja: <br> 
 *  - algoritam za hesiranje (SHA-384 podrazumjevano)<br>
 *  - kodovanje sifre (UTF-8 podrazumjevano)<br>
 *  - ponavljanje <br>
 * @author Mikec
 */
public class UpravljanjeSiframa {
    private String algorithm = "SHA-384";
    private String encoding = "UTF-8";
    private int hashRepeat = 1000; 
    
    /**
     * Podrazumjevani konstruktor
     */
    public UpravljanjeSiframa(){
    }
    
    /**
     * Konstruktor kojim se zadaje algoritam hesiranja <br>
     * @param algorithm naziv algoritma hesiranja (def. SHA-384)
     */
    public UpravljanjeSiframa(String algorithm){
        this.algorithm = algorithm; 
    }
    
    /**
     * Konstruktor kojim se zadaje algoritam i kodovanje <br>
     * @param algorithm naziv algoritma hesiranja (def. SHA-384)
     * @param encoding kodovanje (def. UTF-8)
     */
    public UpravljanjeSiframa(String algorithm,String encoding){
        this.algorithm = algorithm; 
        this.encoding = encoding; 
    }
    
    /**
     * Konstruktor kojim se zadaje naziv algoritma, kodovanje i broj ponavljanja <br>
     * @param algorithm naziv algoritma hesiranja (def. SHA-384)
     * @param encoding kodovanje (def. UTF-8)
     * @param hashRepeat ponavljanje (def. 1000)
     */
    public UpravljanjeSiframa(String algorithm, String encoding, int hashRepeat){
        this.algorithm = algorithm; 
        this.encoding = encoding; 
        this.hashRepeat = hashRepeat; 
    }
    
    /**
     * Jednostruki hashsalt sifre.
     * @param password sifra
     * @param salt salt 
     * @return hash sifre i salta u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public  byte[] getOneHash(String password, String salt) throws 
            NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset(); 
        digest.update(salt.getBytes(encoding)); 
        return digest.digest(password.getBytes(encoding));
    }
    
    /**
     * Jednostruki hash sifre ili kombinacije sifre i salta 
     * @param by podaci o sifri ili sifri i saltu u bajtoovima
     * @return hash sifre u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getOneHash(byte [] by) throws 
            NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();  
        return digest.digest(by);
    }
    
    /**
     * Visestruki hash
     * @param password sifra 
     * @param salt salt za sifru 
     * @return hash sifre i salta 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getHash(String password, String salt) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        byte[] b = getOneHash(password, salt);
        for(int i=0; i<hashRepeat; i++) b = getOneHash(b);
        return b; 
    }
    
    /**
     * String za hash
     * @param password sifra
     * @param salt salt
     * @return string za hesh dobijen {@link getHash}
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String toHashString(String password, String salt) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return new String(Base64.getEncoder().encode(getHash(password,salt)));
    }
    
    /**
     * Provjera za hash i salt
     * @param password sifra
     * @param salt salt
     * @param hash hes
     * @return rezultat provjere
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public boolean checkHash(String password, String salt, String hash) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return toHashString(password,salt).equals(hash);
    }
}
