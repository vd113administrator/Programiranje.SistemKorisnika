/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.kontrola.server;

import java.util.HashSet;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.model.Sesija;

/**
 * Kontrolne metode za prijavu 
 * @author Mikec
 */
public class PrijavaControl {
    private static HashSet<Sesija> sesije = new HashSet<>();
    
    /**
     * Otvaranje sesije 
     * @param ap autentifikacioni podaci 
     * @param s komunikacija s klijentima
     * @return rezultat prijave
     */
    public synchronized static String otvoriSesiju(AutentifikacioniPodaci ap, ServerDataAdapter s){
        Korisnik k = RegistracijaControl.getKorisnik(ap);
        if(k==null) return null; 
        Sesija ss = new Sesija(k,s);
        if(sesije.contains(ss)) return null; 
        sesije.add(ss);
        s.setSesija(ss);
        return ss.getSessionId(); 
    }
    
    /**
     * Zatvaranje sesije
     * @param s sesija
     * @return rezultat
     */
    public synchronized static boolean zatvoriSesiju(Sesija s){
        if(!sesije.contains(s)) return false; 
        sesije.remove(s);
        return true; 
    }
    
    /**
     * Provjera otvorenosti sesije
     * @param s sesija
     * @return rezultat
     */
    public synchronized static boolean otvorenaSesija(Sesija s){
        return sesije.contains(s); 
    }
}
