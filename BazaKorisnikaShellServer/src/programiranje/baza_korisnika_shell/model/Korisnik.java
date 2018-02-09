/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.model;

import java.io.Serializable;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;

/**
 * Lokalni model podataka korisnika u odnosu na projekat
 * @author Mikec
 */
public class Korisnik implements Serializable, Comparable{
    private AutorizacioniPodaci podaci; 
    /**
     * Konstruktor
     * @param ap autorizacioni podaci
     */
    public Korisnik(AutorizacioniPodaci ap){
        podaci = ap; 
    }
    /**
     * Dobijanje autorizacije
     * @return autorizacioni podaci 
     */
    public AutorizacioniPodaci getAutorizacija(){
        return podaci; 
    }
    /**
     * Dobijanje samo autentifikacionih podataka 
     * @return autentifikacija
     */
    public AutentifikacioniPodaci getAutentifikacija(){
        return podaci.getAutentifikacija(); 
    }
    /**
     * Dobijanje deskripcionih podataka 
     * @return opisni podaci
     */
    public OpisniPodaci getDeskripcija(){
        return podaci.getOpis(); 
    }
    
    /**
     * @return hes kod stringizacije autentifikacije 
     */
    @Override 
    public int hashCode(){
        return getAutentifikacija().getUsername().intern().hashCode();
    }
    
    /**
     * Stringizacija korisnika
     * @return string korisnika
     */
    @Override
    public String toString(){
        return podaci.toString(); 
    }
    
    /**
     * Jednacenje korisnika po autorizaciji
     * @param o instanca tipa korisnik
     * @return rezultat jednacenja 
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Korisnik){
            Korisnik k = (Korisnik) o;
            return podaci.equals(k.podaci);
        }
        return false;
    }
    
    /**
     * Poredjenje korisnika po autorizaciji 
     * @param o instanca tipa korisnik
     * @return rezultat poredjenja
     */
    @Override
    public int compareTo(Object o) {
        Korisnik k = (Korisnik) o;
        String uname1 = getAutentifikacija().getUsername().intern();
        String uname2 = k.getAutentifikacija().getUsername().intern();
        return uname1.compareTo(uname2);
    }
}
