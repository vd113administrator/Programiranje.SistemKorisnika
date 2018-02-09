/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model podataka registracije
 * @author Mikec
 */
public class RegistracijaBean {
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

    /**
     * Pretvorba podataka klase  u listu bez sifre (opisni podaci)
     * @return lista
     */
    public List<String> asSignInList(){
        ArrayList<String> list = new ArrayList<>(); 
        list.add(username);
        list.add(name);
        list.add(surname);
        list.add(identificator);
        list.add(address);
        list.add(telephone);
        list.add(email);
        list.add(workplaces);
        list.add(webs);
        return list; 
    }
    
    /**
     * Pretvorba podataka klase u listu sa sifrom (registracioni podaci)
     * @return lista podataka 
     */
    public List<String> asSignUpList(){
        ArrayList<String> list = new ArrayList<>(); 
        list.add(identificator);
        list.add(name);
        list.add(surname);
        list.add(username);
        list.add(password);
        list.add(address);
        list.add(telephone);
        list.add(workplaces);
        list.add(email);
        list.add(webs);
        return list; 
    }
    
    /**
     * Ocitavanje podataka iz liste bez sifre
     * @param list podaci
     */
    public void fromSignInList(List<String> list){
        username = list.get(0);
        name = list.get(1);
        surname = list.get(2);
        identificator = list.get(3);
        address = list.get(4);
        telephone = list.get(5);
        email = list.get(6);
        workplaces = list.get(7);
        webs = list.get(8);
    }
    
    /**
     * Ocitavanje podataka iz liste sa sifrom 
     * @param list podaci
     */
    public void fromSignUpList(List<String> list){
        identificator = list.get(0);
        name = list.get(1);
        surname = list.get(2);
        username = list.get(3);
        password = list.get(4);
        address = list.get(5);
        telephone = list.get(6);
        workplaces = list.get(7);
        email = list.get(8);
        webs = list.get(9); 
    }
    
    /**
     * Dobijanje imena korisnika
     * @return ime 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Dobijanje prezimena korisnika
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
     * Dobijanje radnog mjesta
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
     * Dobijanje emaila
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Dobijanje veb sajtova 
     * @return sajtovi
     */
    public String getWebs() {
        return webs;
    }
    
    /**
     * Postavljanje imena
     * @param name ime
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Postavljanje prezimena 
     * @param surname prezime 
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Postavljanje korisnickog imena
     * @param username korisnicko ime 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Postavljanje sifre
     * @param password sifra
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Postavljanje identifikacije 
     * @param identificator identifikacija 
     */
    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }

    /**
     * Postavljanje telefona
     * @param telephone telefon 
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Postavljanje radnih mjesta 
     * @param workplaces radna mjesta
     */
    public void setWorkplaces(String workplaces) {
        this.workplaces = workplaces;
    }
    
    /**
     * Postavljanje adrese
     * @param address adresa
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Postavljanje email-a 
     * @param email email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Postavljanje veb sajtova 
     * @param webs veb sajtovi
     */
    public void setWebs(String webs) {
        this.webs = webs;
    }
}
