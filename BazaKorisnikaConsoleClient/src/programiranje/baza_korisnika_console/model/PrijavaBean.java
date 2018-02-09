/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.model;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * Model podataka za prijavu 
 * @author Mikec
 */
public class PrijavaBean {
    private String username = ""; 
    private String password = ""; 
    
    /**
     * Dobijanje korisnickog imena 
     * @return korisnicko ime
     */
    public String getUsername(){
        return username; 
    }
    
    /**
     * Dobijanje sifre
     * @return sifra
     */
    public String getPassword(){
        return password; 
    }
    
    /**
     * Postavljanje korisnickog imena
     * @param username novo korisnicko ime
     */
    public void setUsername(String username){
        this.username = username;
    }
    
    /**
     * Postavljanje sifre
     * @param password nova sifra
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Pretvorba podataka u listu
     * @return lista
     */
    public List<String> asList(){
        ArrayList<String> list = new ArrayList<>(); 
        list.add(username);
        list.add(password);
        return list; 
    }
    
    /**
     * Ocitavanje podataka iz liste
     * @param list podaci
     */
    public void fromList(List<String> list){
        username = list.get(0);
        password = list.get(1);
    }
    
    /**
     * Pretvorba podataka u par
     * @return par korisnickog imena i sifre
     */
    public Pair<String,String> asPair(){
        Pair<String,String> pair = new Pair<String,String>(username,password); 
        return pair; 
    }
    
    /**
     * Ocitavanje podataka iz para 
     * @param pair podaci
     */
    public void fromPair(Pair<String,String> pair){
        username = pair.getKey();
        password = pair.getValue();
    }
}
