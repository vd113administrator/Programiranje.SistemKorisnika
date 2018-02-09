/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.data;

import java.io.Serializable;

/**
 * Autentifikacioni i opisni podaci - podaci za autorizaciju
 * @author Mikec
 */
public final class AutorizacioniPodaci implements Serializable{
    private OpisniPodaci opis;
    private AutentifikacioniPodaci autentifikacija; 
    
    /**
     * Konstruktor 
     * @param o opisni podaci
     * @param a autentifikacioni podaci
     */
    public AutorizacioniPodaci(OpisniPodaci o, AutentifikacioniPodaci a){
        opis = o; 
        autentifikacija = a; 
    }
    
    /**
     * Dobijanje opisa
     * @return opisni podaci
     */
    public OpisniPodaci getOpis(){
        return opis;
    }
    
    /**
     * Dobijanje autentifikacije
     * @return autentifikacioni podaci
     */
    public AutentifikacioniPodaci getAutentifikacija(){
        return autentifikacija; 
    }
    
    /**
     * Poredjenje po punim vrijednosima svih podataka 
     * @param o instanca autorizacionih podataka
     * @return rezultat poredjenja
     */
    @Override 
    public boolean equals(Object o){
        if(o instanceof AutorizacioniPodaci){
            AutorizacioniPodaci a = (AutorizacioniPodaci) o;
            if(!a.opis.fullequals(opis)) return false;
            if(!a.autentifikacija.fullequals(autentifikacija)) return false;
        }
        return true;
    }
    
    /**
     * Provjera da li podaci dobijene instance odgovaraju lokalnim 
     * @param a instanca autorizacionih podataka
     * @throws InvalidAuthorizationException 
     */
    public void odgovara(AutorizacioniPodaci a) throws InvalidAuthorizationException{
        if(!equals(a)) throw new InvalidAuthorizationException();
    }
    
    /**
     * Strigizacija instance 
     * @return korisnicko ime i ime i prezime
     */
    @Override 
    public String toString(){
        return autentifikacija.toString()+"["+opis.toString()+"]";
    }
}
