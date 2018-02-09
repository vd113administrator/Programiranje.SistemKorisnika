/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika.cert.alg.UpravljanjeSiframa;
import programiranje.baza_korisnika.cert.util.SaltManager;

/**
 * Model podataka autentifikacionih podataka <br>
 * Posjeduje i sredstva za upravljanje siframa
 * @author Mikec
 */
public class AutentifikacioniPodaci implements Serializable{
    private String username;
    private String password; 
    private String hpassword; 
    private String salt; 
    
    private SaltManager saltManager;
    private UpravljanjeSiframa upravljanjeSiframa; 
    
    /**
     * Konstruktor za autentifikacione podatke
     * @param uname korisnicko ime
     * @param password sifra
     */
    public AutentifikacioniPodaci(String uname, String password){
        this.username = uname; 
        this.password = password; 
    } 
    
    /**
     * Konstruktor za autentifikacione podatke
     * @param uname korisnicko ime za bazu
     * @param hpassword sifra za bazu
     * @param sm menadzer generisanja saltova
     * @param us menadzer za upravljanje siframa
     */
    public AutentifikacioniPodaci(String uname, String hpassword, SaltManager sm, UpravljanjeSiframa us){
        this.username = uname; 
        this.hpassword = hpassword; 
        this.salt = sm.generateSalt();
        sm.add(uname, salt);
        this.saltManager = sm; 
        this.upravljanjeSiframa = us; 
    } 
    
    /**
     * Konstruktor za upravljanje sifraama
     * @param uname korisnicko ime
     * @param hpassword sifra baze
     * @param salt salt kod
     * @param sm menadzer generisanja saltova
     * @param us menadzer za upravljanje siframa 
     */
    public AutentifikacioniPodaci(String uname, String hpassword, String salt, SaltManager sm, UpravljanjeSiframa us){
        this.username = uname; 
        this.hpassword = hpassword; 
        this.salt = salt;
        sm.add(uname, salt);
        this.saltManager = sm; 
        this.upravljanjeSiframa = us; 
    } 
    
    /**
     * Dobijanje menadzera za generisanje saltova
     * @return menadzer za saltove 
     */
    public SaltManager getSaltManager(){
        return saltManager; 
    }
    
    /**
     * Dobijanje menadzera za upravljanje siframa
     * @return upravljanje siframa
     */
    public UpravljanjeSiframa getUpravljanjeSiframa(){
        return upravljanjeSiframa; 
    }
    
    /**
     * Modovi za civanje sifre zavisno od upotrbr su otvoreni i hes mod <br>
     * Ovdije se prelazi i obiljezava prelazak na hes mod
     * @param sm menadzer za saltove
     * @param us menadzer za sifre
     */
    public void gotoHashMode(SaltManager sm, UpravljanjeSiframa us) {
        try {
            if(hashMode()) return;
            saltManager = sm;
            upravljanjeSiframa = us;
            salt = saltManager.generateSalt();
            saltManager.add(username, salt);
            hpassword = us.toHashString(password, salt); 
            password = null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AutentifikacioniPodaci.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AutentifikacioniPodaci.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Dobijanje salta za sifre
     * @return salt
     */
    public String getSalt(){
        return salt; 
    }
    
    /**
     * Dobijanje korisnickog imena korisnika
     * @return korisnicko ime korisnika
     */
    public String getUsername(){
        return username; 
    }
    
    /**
     * Dobijanje hesa za sifru 
     * @return hes sifre
     */
    public String getHashPassword(){
        return hpassword;
    }
    
    /**
     * Dobijanje sifre
     * @return sifra
     */
    public String getPassword(){
        return password; 
    }
    
    /**
     * Provjera da li je otvoreni mod
     * @return otvoreni mod
     */
    public boolean plainMode(){
        return password != null; 
    }
    
    /**
     * Provjera da li je hes mod
     * @return hes mod
     */
    public boolean hashMode(){
        return hpassword != null; 
    }
    
    /**
     * Provjera jednakosti po korisnickom imenu
     * @param o obijekat autentifikacije
     * @return rezultat 
     */
    @Override 
    public boolean equals(Object o){
        if(o instanceof AutentifikacioniPodaci){
            AutentifikacioniPodaci a = (AutentifikacioniPodaci) o;
            return username.equals(a.username);
        }
        return false; 
    }
    
    /**
     * Provjera sifre
     * @param password provjeravana sifra
     * @return rezultat
     */
    public boolean valid(String password){
        try{
            if(!hashMode())
                return this.password.equals(password);
            else {
                return upravljanjeSiframa.checkHash(password, salt, hpassword);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AutentifikacioniPodaci.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AutentifikacioniPodaci.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Provjera jednakosti svih podataka
     * @param ap instanca autentifikacionih podataka za provjeru 
     * @return  rezultat
     */
    public boolean fullequals(AutentifikacioniPodaci ap){
        if(!equals(ap)) return false; 
        if(ap.plainMode()) return valid(ap.password);
        if(plainMode()) return ap.valid(password);
        return hpassword.equals(ap.hpassword); 
    }
    
    /**
     * Provjera odgovaranja po korisnickom imenu i sifri 
     * @param uname korisnicko ime 
     * @param pass sifra 
     * @throws InvalidAutentificationException 
     */
    public void odgovara(String uname, String pass) throws InvalidAutentificationException{
        if(!fullequals(new AutentifikacioniPodaci(uname,pass))) throw new InvalidAutentificationException();
    } 
    
    /**
     * Provjera po korisnickom imenu i sifri
     * @param ap instanca autentifikacije
     * @throws InvalidAutentificationException 
     */
    public void odgovara(AutentifikacioniPodaci ap) throws InvalidAutentificationException{
        if(!fullequals(ap)) throw new InvalidAutentificationException();
    } 
    
    /**
     * Pretvorba u string 
     * @return string sa korisnickim imenom 
     */
    @Override 
    public String toString(){
        return username; 
    }
    
    public AutentifikacioniPodaci setHashManagment(SaltManager sm, UpravljanjeSiframa us){
        this.saltManager = sm; 
        this.upravljanjeSiframa = us;
        return this; 
    }
    
    public AutentifikacioniPodaci setSalt(String salt){
        this.salt = salt;
        saltManager.add(username,salt);
        return this; 
    }
    
    public AutentifikacioniPodaci gotoHashModeLocal() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        hpassword = upravljanjeSiframa.toHashString(password, salt); 
        password = null;
        return this; 
    }
}
