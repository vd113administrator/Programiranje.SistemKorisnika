/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.baza_podataka;

import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.model.Korisnik;

/**
 * Ogranicenja na tekst podatke
 * @author Mikec
 */
public final class ProvjeraImena {
    private ProvjeraImena(){
    }
    
    /**
     * Provjera na ograninje velikih slova, malih slova, brojeva, .,:;-_`@# i novi red
     * @param s string
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(String s){
        if(s==null) return true; 
        for(char c : s.toCharArray()){
            if(Character.isDigit(c)) continue;
            if(Character.isUpperCase(c)) continue; 
            if(Character.isLowerCase(c)) continue;
            if(c=='-') continue;
            if(c=='_') continue;
            if(c==' ') continue;
            if(c=='.') continue; 
            if(c==',') continue; 
            if(c==':') continue; 
            if(c==';') continue; 
            if(c=='`') continue;
            if(c=='@') continue;
            if(c=='#') continue;
            if(c=='\n') continue; 
            return false; 
        }
        return true; 
    }
    
    /**
     * Provjera niza stringova na {@link provjeraVSMSBDCM(java.lang.String)}
     * @param ss niz znakova
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(String ... ss){
        for(String s:ss)
            if(!provjeraVSMSBDCM(s)) return false; 
        return true; 
    }
    
    /**
     * Provjera podataka korisnika na {@link provjeraVSMSBDCM(java.lang.String)}
     * @param k korisnik
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(Korisnik k){
        if(!provjeraVSMSBDCM(k.getDeskripcija())) return false;
        if(!provjeraVSMSBDCM(k.getAutentifikacija())) return false;
        return true; 
    }
    
    /**
     * Provjera podataka korisnika na {@link provjeraVSMSBDCM(java.lang.String)}
     * @param k opisni podaci korisnika
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(OpisniPodaci k){
        return provjeraVSMSBDCM(k.getAdress(),
                k.getEmails(),k.getFirstname(),k.getIdentificator(),k.getJobs(),
                k.getLastname(),k.getPhones(),k.getWebs());
    }
    
    /**
     * Provjera podataka korisnika na {@link provjeraVSMSBDCM(java.lang.String)}
     * @param k autentifikacioni podaci korisnika
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(AutentifikacioniPodaci k){
        return provjeraVSMSBDCM(k.getUsername(),k.getPassword());
    }
}
