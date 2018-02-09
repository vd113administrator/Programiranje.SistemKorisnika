/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 * Upravljanje i generisanje salt kodova za razne potrebe <br>
 * Mapa saltova po kljucevima 
 * @author Mikec
 */
public class SaltManager {
    private HashMap<String,String> salts = new HashMap<String,String>();
    
    /**
     * Dodavanje para kljuc-salt
     * @param key kljuc
     * @param salt salt
     * @return rezultat dodavanja
     */
    public synchronized boolean add(String key, String salt){
         if(salts.values().contains(salt)) return false; 
         if(salts.keySet().contains(salt)) return false; 
         salts.put(key, salt);
         return true; 
    }
    
    /**
     * Brisanje salta po kljucu 
     * @param key kljuc
     * @return rezultat brisanja
     */
    public synchronized boolean removeKey(String key){
        if(!salts.keySet().contains(key)) return false;  
        salts.remove(key);
        return true; 
    }
    
    /**
     * Brisanje po saltu 
     * @param salt salt
     * @return rezultat brisanja 
     */
    public synchronized boolean removeSalt(String salt){
        if(!salts.values().contains(salt)) return false;  
        String key = "";
        for(HashMap.Entry<String,String> e : salts.entrySet()){
            key = e.getKey();
            if(e.getValue().equals(salt)) break;
        }
        removeKey(key);
        return true; 
    }
    
    /**
     * Provjera postojanja kljuca
     * @param key kljuc
     * @return rezultat
     */
    public synchronized boolean containsKey(String key){
        return salts.keySet().contains(key); 
    }
    
    /**
     * Provjera postojanja salta
     * @param salt salt
     * @return rezultat
     */
    public synchronized boolean containsSalt(String salt){
        return salts.values().contains(salt);
    }
    
    /**
     * Interno, automatsko generisanje salta 
     * @return salt
     */
    public synchronized String generateSalt(){
        String s; 
        do{
            s = "";
            for(int i=0; i<8; i++){
                s += Integer.toString(Math.abs(new Random().nextInt()%10));
            }
        }while(containsSalt(s));
        return s;
    }
    
    /**
     * Dobijanje salta
     * @param key kljuc
     * @return salt
     */
    public synchronized String getSalt(String key){
        return salts.get(key);
    }
    
    /**
     * Dobijanje kljuca
     * @param salt salt
     * @return kljuc
     */
    public synchronized String getKey(String salt){
        if(!containsSalt(salt)) return null;
        for(HashMap.Entry<String,String> me: salts.entrySet()){
            if(me.getValue().equals(salt)) return me.getKey();
        }
        return null; 
    }
   
    /**
     * Slika mape kljuceva, saltova
     * @return kopirana mapa
     */
    public synchronized Map<String,String> getCopyMap(){
        return new HashMap<>(salts); 
    }
}
