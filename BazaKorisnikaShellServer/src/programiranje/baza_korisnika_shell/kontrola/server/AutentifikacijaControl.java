/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.kontrola.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.InvalidAutentificationException;

/**
 * Grupa metoda u vezi sa autentifikacijom 
 * @author Mikec
 */
public class AutentifikacijaControl {
    /**
     * Provjera parameara sifre 
     * @param password sifra 
     * @return rezultat provjere
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
     * Provjera autrntifikacionih podataka na postojanje u bazi i validnost
     * @param ap autentifikacioni podaci 
     * @param uname korisnicko ime
     * @param pass sifra
     * @return rezultat provjere
     */
    public static synchronized boolean provjeraAutentifikacije(AutentifikacioniPodaci ap, String uname, String pass){
        if(ap.getUsername().length()==0) return false; 
        try {
            ap.odgovara(uname, pass);
            return true; 
        } catch (InvalidAutentificationException ex) {
            return false; 
        }
    }
}
