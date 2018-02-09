/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.model_podataka;

import java.util.ArrayList;
import java.util.List;

/**
 * Model i struktura podataka za korisnika 
 * @author Mikec
 */
public class KorisnikBean {
    private String name = "";
    private String surname = "";
    private String username = ""; 
    private String password = "";
    private String identificator = "";
    private String telephone = "";
    private String workplaces = "";
    private String address = "";
    private String email = "";
    private String webs = "";
    private String tip = ""; 
    
    /**
     * Dobijanje imena 
     * @return ime  
     */
    public String getName() {
        return name;
    }

    /**
     * Dobijenje prezimena 
     * @return prezime
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Dobijanje korisnickog imena
     * @return korisnicko ime
     */
    public String getUsername() {
        return username;
    }

    /**
     * Dobijanje sifre
     * @return sifra 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Dobijanje identifikatora 
     * @return identifikator 
     */
    public String getIdentificator() {
        return identificator;
    }

    /**
     * Dobijanje telefona 
     * @return telefon 
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Dobijanje radnih mjesta 
     * @return radno mjesto 
     */
    public String getWorkplaces() {
        return workplaces;
    }

    /**
     * Dobijanje adrese 
     * @return adresa
     */
    public String getAddress() {
        return address;
    }

    /**
     * Dobijanje elektronske poste 
     * @return elektronska posta 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Dobijanje veb adrese 
     * @return veb adresa 
     */
    public String getWebs(){
        return webs;
    }
    
    /**
     * Dobijanje tipa korisnika
     * @return tip 
     */
    public String getType(){
        return tip;
    }
    
    /**
     * Postavljanje imena 
     * @param name novo ime 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Postavljanje prezimena 
     * @param surname novo prezime 
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Postavljanje korisnickog imena 
     * @param username novo korisnicko ime 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Postavljanje sifre 
     * @param password nova sifra 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Postavljanje identifikatora 
     * @param identificator novi identifikator
     */
    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }

    /**
     * Postavljanje telefona
     * @param telephone novi telefon 
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Postavljanje novog radnog mjesta
     * @param workplaces radno mjesto 
     */
    public void setWorkplaces(String workplaces) {
        this.workplaces = workplaces;
    }

    /**
     * Postavljanje adrese 
     * @param address nova adresa
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Postavljanje nove elektronske poste
     * @param email nova elektronska posta
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Postavljanje novih veb sajtova
     * @param webs novi veb sajtovi
     */
    public void setWebs(String webs) {
        this.webs = webs;
    }
    
    /**
     * Promjena tipa korisnika 
     * @param tip novi tip korisnika
     */
    public void setType(String tip){
        this.tip = tip;
    }
    
    /**
     * Jednacenje po korisnickom imenu 
     * @param o drugi korisnik 
     * @return rezultat 
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof KorisnikBean){
            KorisnikBean k =  (KorisnikBean) o;
            return username.equals(k.username);
        }
        return false; 
    }
    
    /**
     * Hes kod korisnika 
     * @return hes korisnickog imena 
     */
    @Override
    public int hashCode(){
        return username.hashCode();
    }
}
