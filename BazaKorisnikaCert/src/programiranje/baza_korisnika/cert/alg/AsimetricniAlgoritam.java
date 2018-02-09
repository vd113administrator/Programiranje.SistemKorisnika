/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.alg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.pki.CertificateManager;
import programiranje.baza_korisnika.cert.pki.X509Manager;

/**
 * 
 * <b>Metode za izvodjenje asimetricnih algoritma</b>
 * Moze se koristiti bilo koji algoritam.<br>
 * Postoje polja <br>
 *  - cipher <br>
 *  - algorithm <br>
 *  - private key <br>
 *  - public key <br>
 *  - communication key <br><br>
 *  Uloge ovih polja su redom : <br>
 *     - upravljanje sifrovanjem <br>
 *     - asimetricni algoritam koji se koristi <br>
 *     - privatni kljuc koji u valsnistvu objekta algoritma: <br><br>
 *       ZA PREDAJNIKA I PRIJEMNIKA ZAVISNO OD TOGA KAKO SE KORISTI <br>
 *     - javni kljuc u vlasnistvu obijekta algoritma <br>
 *     - javni kljuc druge strane u komunikaciji. <br><br>
 * @author Mikec
 */
public class AsimetricniAlgoritam implements Serializable{
    private transient Cipher cipher; 
    private String algorithm = "RSA"; 
    private PrivateKey privateKey; 
    private PublicKey publicKey; 
    private PublicKey communicationKey; 
    
    private KeyPair keyPair; 
    private transient KeyPairGenerator keyPairGenerator;
    
    /**
     * Konstruktor koji podrazumijeva RSA algoritam <br>
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     */
    public AsimetricniAlgoritam() throws NoSuchAlgorithmException, NoSuchPaddingException{
        this.cipher = Cipher.getInstance(algorithm);
    }
    
    /**
     * Konstruktor koji podrazumijeva zadati algoritam <br>
     * @param alg naziv algoritma 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public AsimetricniAlgoritam(String alg) throws NoSuchAlgorithmException, NoSuchPaddingException{
        this.algorithm = alg; 
        this.cipher = Cipher.getInstance(algorithm);
    }
    
    /**
     * Dobijanje privatnog kljuca <br>
     * @return privatni kljuc
     */
    public PrivateKey getPrivateKey(){
        return privateKey; 
    }
    
    /**
     * Dobijanje javnog kljuca <br>
     * @return javni kljuc
     */
    public PublicKey getPublicKey(){
        return publicKey;
    }
    
    /**
     * Dobijanje komunikacijskog javnog kljuca <br>
     * @return komunikcaijski kljuc
     */
    public PublicKey getCommunicationKey(){
        return communicationKey; 
    }
    
    /**
     * Metoda inicijalizacija generatora kljuceva za datu instancu objekata algoritma. <br>
     * @param keylength duzina kljuca
     * @throws NoSuchAlgorithmException
     */
    public void initKeyPairGenerator(int keylength) throws NoSuchAlgorithmException{
        this.keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        this.keyPairGenerator.initialize(keylength);
    }
    
    /**
     * Kreiranje novog generatora parova kljuceva <br>
     * Pogodno za slucaj da se koristi novi generator <br>
     * @param keylength duzina kljuca
     * @param algorithm naziv algoritma
     * @return novi generator
     * @throws NoSuchAlgorithmException 
     */
    public static KeyPairGenerator newKeyPairGenerator(int keylength, String algorithm) throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(keylength);
        return kpg;
    }
    
    /**
     * Postavljanje generatora parova kljuceva <br>
     * Pogodno kad je u pitanju jedan par kljuceva <br>
     * @param kpg generator 
     */
    public void setKeyPairGenerator(KeyPairGenerator kpg){
        this.keyPairGenerator = kpg;
    }
    
    /**
     * Generisanje kljuca od strane objekta generatora <br>
     */
    public void generateKeysInPair(){
        this.keyPair = this.keyPairGenerator.generateKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
        this.communicationKey = this.publicKey; 
    }
    
    /**
     * Enkriptovanje podataka za objekat kod POSILJAOCA samo za primaoca <br>
     * @param input otvoreni sadrzaj poruke 
     * @return enkriptovan sadrzaj poruke 
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encryptDataForSingle(byte[] input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        this.cipher.init(Cipher.ENCRYPT_MODE,communicationKey);
        return cipher.doFinal(input);
    }
    
    /**
     * Dekriptovanje poruke poslate sa metodom {@link encryptDataForSingle}<br>
     * @param input enkriptovan sadrzaj poruke
     * @return otvoreni sadrzaj poruke
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public byte[] decryptDataForSingle(byte[] input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        this.cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher.doFinal(input);
    }
    
    /**
     * Enkriptovanje podataka za prema grupi korisnika <br>
     * @param input otvoreni sadrzaj poruke
     * @return enkriptovan sadrzaj poruke
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encryptDataForGroup(byte[] input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        this.cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        return cipher.doFinal(input);
    }
    
    /**
     * Dekriptovanje podataka {@link encryptDataForGroup} <br>
     * @param input enkriptovan sadrzaj poruke
     * @return dekriptovan sadrzaj poruke
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public byte[] decryptDataForGroup(byte[] input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        this.cipher.init(Cipher.DECRYPT_MODE,communicationKey);
        return cipher.doFinal(input);
    }
    
    /**
     * Postavljanje komunikacijskog kljuca <br>
     * @param communicationKey komunikacijski kljuc
     */
    public void setCommunicationKey(PublicKey communicationKey){
        this.communicationKey = communicationKey; 
    }
    
    public void setCommunicationKey(X509Certificate cert){
        this.communicationKey = cert.getPublicKey(); 
    }
    
    public void setCommunicationKey(CertificateManager certmgr){
        this.communicationKey  = certmgr.getCert().getPublicKey(); 
    }
    
    
    /**
     * Postavljanje javnog kljuca
     * @param publicKey javni kljuc
     */
    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey; 
    }
    
    public void setPublicKey(X509Certificate cert){
        this.publicKey = cert.getPublicKey(); 
    }
    
    public void setPublicKey(CertificateManager certmgr){
        this.publicKey  = certmgr.getCert().getPublicKey(); 
    }
    
    
    public void applayCertificate(X509Manager cmg){
        this.publicKey = cmg.getPublicKey(); 
        this.privateKey = cmg.getPrivateKey(); 
    }
    
    public void applyCommunicationWithCertServer(X509Manager cmg){
        this.publicKey = cmg.getPublicKey(); 
        this.privateKey = cmg.getPrivateKey();
        this.communicationKey = cmg.getPublicKeyOfSignature();
    }
    
    protected void setPrivateKey(PrivateKey privateKey){
        this.privateKey = privateKey; 
    }
}
