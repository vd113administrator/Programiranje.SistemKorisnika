/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.tech;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import programiranje.baza_korisnika.cert.alg.AsimetricniAlgoritam;
import programiranje.baza_korisnika.cert.alg.SimetricniAlgoritam;
import programiranje.baza_korisnika.cert.pki.X509Manager;

/**
 * Klasa cije su instance mehanizam digitalne enevelope <br><br>
 * Polja: <br>
 *   outa - asimetricni algoritam za digitalnu envelopu <br> 
 *   ina - simetricni algoritam za digitalnu envelopu <br>
 *   ablok - velicina bloka simetricnog algoritma (def. 1024)
 * @author Mikec
 */
public class TackaDigitalneEnvelope implements Serializable{
    private AsimetricniAlgoritam outa;
    private SimetricniAlgoritam ina;
    private int ablok = 1024; 
    
    /**
     * Podrazumjevani konstruktor
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public TackaDigitalneEnvelope() throws NoSuchAlgorithmException, NoSuchPaddingException{
        outa = new AsimetricniAlgoritam(); 
        ina = new SimetricniAlgoritam();
    }
    
    /**
     * Konstruktor duzine bloka
     * @param b duzina bloka
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public TackaDigitalneEnvelope(int b) throws NoSuchAlgorithmException, NoSuchPaddingException{
        outa = new AsimetricniAlgoritam(); 
        ina = new SimetricniAlgoritam();
        ablok = b; 
    }
    
    /**
     * Konstruktor algoritama 
     * @param a obijekat asimetricnog algoritma
     * @param s obijekat simetricnog algoritma 
     */
    public TackaDigitalneEnvelope(AsimetricniAlgoritam a, SimetricniAlgoritam s){
        outa = a; 
        ina = s;
    }
    
    /**
     * Konstruktor algoritama i bloka
     * @param a obijekat asimetricnog algoritma
     * @param s obijekat simetricnog algoritma
     * @param b blok simetricnog algoritma
     */
    public TackaDigitalneEnvelope(AsimetricniAlgoritam a, SimetricniAlgoritam s, int b){
        outa = a; 
        ina = s;
        ablok = b; 
    }
    
    /**
     * Dobijanje asimetricnog algoritma
     * @return asimetricni algoritam
     */
    public AsimetricniAlgoritam getAsimetricniAlgoritam(){
        return outa; 
    }
    
    /**
     * Dobijanje simetricnog algoritma 
     * @return simetricni algoritam
     */
    public SimetricniAlgoritam getSimetricniAlgoritam(){
        return ina; 
    }
    
    /**
     * Postavljanje simetricnog algoritma
     * @param s obijekat simetricnog algoritma
     */
    public void setSimetricniAlgoritam(SimetricniAlgoritam s){
        ina = s;
    }
    
    /**
     * Dobijanje javnog kljuca (asimetricni algoritam)
     * @return javni kljuc
     */
    public PublicKey getJavniKljuc(){
        return outa.getPublicKey();
    }
    
    /**
     * Dobijanje tajnog kljuca (asimetricni algoritam)
     * @return tajni kljuc
     */
    public PrivateKey getTajniKljuc(){
        return outa.getPrivateKey();
    }
    
    /**
     * Dobijanje sesijskog kljuca (simetricni algoritam)
     * @return sesijski kljuc
     */
    public SecretKey getSesijskiKljuc(){
        return ina.getSecretKey();
    }
    
    /**
     * Dobijanje komunikacijskog kljuca
     * @return komunikacijski kljuc
     */
    public PublicKey getKomunikacijskiKljuc(){
        return outa.getCommunicationKey(); 
    }
    
    /**
     * Postavljanje sesijskog kljuca 
     * @param key novi sesijski kljuc
     */
    public void setSesijskiKljuc(SecretKey key){
        ina.setSecretKey(key);
    }
    
    /**
     * Postavljanje komunikacijskog kljuca
     * @param key novi komunikcijski kljuc
     */
    public void setKomunikacijaskiKljuc(PublicKey key){
        outa.setCommunicationKey(key);
    }
    
    /**
     * Inicijalizovanje asimetricnog algoritma
     * @throws NoSuchAlgorithmException 
     */
    public void inicijalizacijaAsimetricnogAlgoritma() throws NoSuchAlgorithmException{
        outa.initKeyPairGenerator(ablok);
        outa.generateKeysInPair();
    }
    
    /**
     * Inicijalizacija simetricnog algoritma
     * @throws NoSuchAlgorithmException 
     */
    public void inicijalizacijaSimetricnogAlgoritma() throws NoSuchAlgorithmException{
        ina.generateSecretKey();
    }
    
    public void prihvatenjaSertifikata(X509Manager cmg){
        outa.applayCertificate(cmg);
    }
    
    public void prihvatenjaKomunikacijeSaCertServerom(X509Manager cmg){
        outa.applyCommunicationWithCertServer(cmg);
    }
}
