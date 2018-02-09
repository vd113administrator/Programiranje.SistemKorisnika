/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.autorizacija;

import javafx.util.Pair;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;

/**
 * Kontrola za autorizaciju
 * @author Mikec
 */
public class AutorizacijaControl {
    /**
     * Provjera sifre
     * @param password sifra
     * @return rezultat
     */
    private static synchronized boolean provjeraSifre(String password){
        if(password.length()<8) return false; 
        int vslova = 0; 
        int mslova = 0; 
        int brojevi = 0; 
        for(char c: password.toCharArray())
            if(Character.isUpperCase(c))
                vslova++; 
            else if(Character.isLowerCase(c))
                mslova++; 
            else if(Character.isDigit(c))
                brojevi++;
        return vslova!=0 && mslova!=0 && brojevi!=0;
            
    }
    
    /**
     * Provjera autorizacije 
     * @param ap autorizacioni podaci 
     * @return rezultat
     */
     public static synchronized Pair<Boolean,String> provjeraAutorizacije(AutorizacioniPodaci ap){
        if(ap.getOpis().getFirstname().trim().length()==0) return new Pair<>(false, "Nema imena."); 
        if(ap.getOpis().getIdentificator().trim().length()==0) return new Pair<>(false, "Nema identifikatora."); 
        if(ap.getOpis().getLastname().trim().length()==0) return new Pair<>(false, "Nema prezimena.");
        if(ap.getAutentifikacija().getUsername().length()==0) return new Pair<>(false, "Nema korisnickog imena.");
        if(ap.getAutentifikacija().getPassword().length()==0) return new Pair<>(false, "Nema sifre.");
        if(!provjeraSifre(ap.getAutentifikacija().getPassword()))             return new Pair<>(false, "Nesigurna sifra. Fale brojevi, velika ili mala slova ili je duzine manje od 8."); 
        return new Pair<>(true,""); 
    }
    
     /**
      * Provjera autorizacionih podataka znajuci hes
      * @param ap autorizacioni podaci 
      * @return rezultat
      */
    public static synchronized Pair<Boolean,String> provjeraAutorizacijePoHash(AutorizacioniPodaci ap){
        if(ap.getOpis().getFirstname().trim().length()==0) return new Pair<>(false, "Nema imena."); 
        if(ap.getOpis().getIdentificator().trim().length()==0) return new Pair<>(false, "Nema identifikatora."); 
        if(ap.getOpis().getLastname().trim().length()==0) return new Pair<>(false, "Nema prezimena.");
        if(ap.getAutentifikacija().getUsername().length()==0) return new Pair<>(false, "Nema korisnickog imena.");
        if(ap.getAutentifikacija().getHashPassword().length()==0) return new Pair<>(false, "Nema sifre.");
        return new Pair<>(true,""); 
    }
}
