/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.model;

import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;

/**
 * Model podataka tipa sesije i upravljanje sesijama ovog servera 
 * @author Mikec
 */
public class Sesija implements Comparable{
    private static int count = 0; 
    private Korisnik korisnik; 
    private ServerDataAdapter adapter; 
    private String sessionId; 
    
    /**
     * Konstrukcija sesije i generisanje identifikacije 
     * @param k korisnik
     * @param s komunikacija 
     */
    public Sesija(Korisnik k, ServerDataAdapter s){
        korisnik = k; 
        adapter  = s;
        sessionId = Integer.toString(++count); 
    }
    
    /**
     * Dobijanje korisnika
     * @return korisnik
     */
    public Korisnik getKorisnik(){
        return korisnik; 
    }
    
    /**
     * Dobijanje komunikacionog sredstva 
     * @return komunikacija 
     */
    public ServerDataAdapter getServerDataAdapter(){
        return adapter; 
    }
    
    /**
     * Sesijski identifikator
     * @return sesijski identifikator
     */
    public String getSessionId(){
        return sessionId; 
    }

    /**
     * @return identifikator sesije - njegov hes 
     */
    @Override 
    public int hashCode(){
        return sessionId.hashCode();
    }
    
    /**
     * Jednacenje sesija po identifikatorus 
     * @param o instanca sesije 
     * @return rezultat
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Sesija){
            Sesija s = (Sesija) o;
            return sessionId.equals(s.sessionId);
        }
        return false;
    }
    
    /**
     * Stringizacija 
     * @return sesijski identifikator
     */
    @Override 
    public String toString(){
        return sessionId;
    }
    
    /**
     * Poredjenje sesija po identifikatoru
     * @param o instanca sesije 
     * @return rezultat poredjenja 
     */
    @Override
    public int compareTo(Object o) {
        Sesija s = (Sesija) o; 
        return sessionId.compareTo(s.sessionId);
    }
}
