/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_podataka;

import java.util.ArrayList;

/**
 * Model podataka za grupu
 * @author Mikec
 */
public class GrupaBean {
    private KorisnikBean administrator; 
    private ArrayList<KorisnikBean> korisnici = new ArrayList<>(); 
    private String naziv;
    private String identifikator; 
    
    /**
     * Minimalni konstruktor 
     * @param korisnik administrator
     * @param naziv naziv grupe
     */
    public GrupaBean(KorisnikBean korisnik, String naziv){
        this.naziv = naziv; 
        this.administrator = korisnik; 
        this.korisnici.add(korisnik);
    }
    
    /**
     * Konstrukor i sa identifiaktor 
     * @param korisnik administrator
     * @param naziv naziv grupe
     * @param identifikator identifikacija
     */
    public GrupaBean(KorisnikBean korisnik, String naziv, String identifikator){
        this.naziv = naziv; 
        this.administrator = korisnik; 
        this.korisnici.add(korisnik);
        this.identifikator = identifikator;
    }
    
    /**
     * Dobijanje administratora
     * @return  administrator 
     */
    public KorisnikBean getAdministrator(){
        return administrator; 
    }
    
    /**
     * Dobijanje naziva grupe
     * @return naziv grupe
     */
    public String getNaziv(){
        return naziv; 
    }
    
    /**
     * Identifikator grupe 
     * @return identifikator grupe
     */
    public String getIdentifikator(){
        return identifikator;
    }
    
    /**
     * Dobijanje korisnika grupe 
     * @return korisnici
     */
    public synchronized ArrayList<KorisnikBean> getKorisnici(){
        return new ArrayList<>(korisnici);
    }
    
    /**
     * Dodavanje korisnika u grupu 
     * @param korisnik korisnik 
     * @return rezultat
     */
    public synchronized boolean dodajKorisnika(KorisnikBean korisnik){
        return korisnici.add(korisnik);
    }
    
    /**
     * Uklanjanje korsinika iz grupe 
     * @param korisnik korisnik 
     * @return rezultat 
     */
    public synchronized boolean ukloniKorisnika(KorisnikBean korisnik){
        return korisnici.remove(korisnik);
    }
    
    /**
     * Jednacenje korisnika 
     * @param o drugi korisnik 
     * @return rezultat
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof KorisnikBean){
            GrupaBean k = (GrupaBean) o;
            return naziv.equals(k.naziv);
        }
        return false; 
    }
    
    /**
     * Hes kod za grupu
     * @return hes kod naziva grupe
     */
    @Override
    public int hashCode(){
        return naziv.hashCode();
    }
}
