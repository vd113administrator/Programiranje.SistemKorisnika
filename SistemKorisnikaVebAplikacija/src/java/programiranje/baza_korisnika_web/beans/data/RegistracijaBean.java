/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.sistem_korisnika_web.text.TextUtil;

/**
 * Podaci za registraciju 
 * @author Mikec
 */
public class RegistracijaBean implements Serializable{
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
     * Dobijanje kao liste bez sifre
     * @return lista podataka 
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
     * Dobijenje kao liste sa sifrom 
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
     * Ucitavanje kao iz liste bez sifre 
     * @param list lista podataka 
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
     * Ucitavanje iz liste sa sifrom 
     * @param list lista podataka 
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
     * Dobijanje imena 
     * @return ime
     */
    public String getName() {
        return TextUtil.removeALG(name);
    }

    /**
     * Dobijanje prezimena 
     * @return prezime
     */
    public String getSurname() {
        return TextUtil.removeALG(surname);
    }

    /**
     * Dobijanje korisnckog imena
     * @return korisnicko ime 
     */
    public String getUsername() {
        return TextUtil.removeALG(username);
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
        return TextUtil.removeALG(identificator);
    }

    /**
     * Dobijanje telefona 
     * @return telefon
     */
    public String getTelephone() {
        return TextUtil.removeALG(telephone);
    }

    /**
     * Dobijanje radnoih mjesta 
     * @return radnja mjesta 
     */
    public String getWorkplaces() {
        return TextUtil.removeALG(workplaces);
    }

    /**
     * Dobijanje adrese 
     * @return adresa
     */
    public String getAddress() {
        return TextUtil.removeALG(address);
    }

    /**
     * Dobijanje emaila
     * @return email
     */
    public String getEmail() {
        return TextUtil.removeALG(email);
    }

    /**
     * Dobijanje veba 
     * @return veb
     */
    public String getWebs() {
        return TextUtil.removeALG(webs);
    }
    
    /**
     * Postavljanje imena
     * @param name ime
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Postavljanje prezimea 
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
     * Postavljanje prezimena 
     * @param password prezime
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Postavljanje identifikatora 
     * @param identificator identifikator 
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
     * Postavljanje adresa
     * @param address adrese
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Postavljanje emailova 
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Postavljanje vebova
     * @param webs vebovi
     */
    public void setWebs(String webs) {
        this.webs = webs;
    }
}
