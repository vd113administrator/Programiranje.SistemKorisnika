/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * Podaci za prijavu
 * @author Mikec
 */
public class PrijavaBean implements Serializable{
    private String username = "";
    private String password = "";
    
    /**
     * Dobijanje korisnickog imena 
     * @return korisnciko ime
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
     * @param username novo korisnciko ime
     */
    public void setUsername(String username){
        this.username = username;
    }
    
    /**
     * Postavljanje sifre 
     * @param password sifra
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Dobijanje kao liste 
     * @return lista podataka 
     */
    public List<String> asList(){
        ArrayList<String> list = new ArrayList<>(); 
        list.add(username);
        list.add(password);
        return list; 
    }
    
    /**
     * Ucitavanje iz liste
     * @param list lista
     */
    public void fromList(List<String> list){
        username = list.get(0);
        password = list.get(1);
    }
    
    /**
     * Dobijenje kao para 
     * @return par
     */
    public Pair<String,String> asPair(){
        Pair<String,String> pair = new Pair<String,String>(username,password); 
        return pair; 
    }
    
    /**
     * Ocitavanje iz para 
     * @param pair par podataka
     */
    public void fromPair(Pair<String,String> pair){
        username = pair.getKey();
        password = pair.getValue();
    }
}
