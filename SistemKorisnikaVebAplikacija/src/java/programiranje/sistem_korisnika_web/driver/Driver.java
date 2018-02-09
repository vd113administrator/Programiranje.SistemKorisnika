/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Vezac za dogadjaje 
 * @author Mikec
 */
public abstract class Driver implements Serializable{
    private final HashMap<String,ArrayList<Runnable>> mapa = new HashMap<>();
    private final ArrayList<Runnable> lista = new ArrayList<>();
    
    /**
     * Konstruktor
     */
    public Driver(){
    }
    
    /**
     * Pokretanje svih mapiranih aktivnosti na komandu
     * @param command komanda
     */
    protected synchronized void go(String command){
        ArrayList<Runnable> x = mapa.get(command);
        if(x!=null) 
            for(Runnable r: x){
                r.run();
            }
        for(Runnable r: lista)
            r.run();
    }
    
    /**
     * Dodavanje komanda 
     * @param command nova komanda
     */
    protected synchronized void add(String command){
        remove(command);
        mapa.put(command, new ArrayList<>());
    }
    
    /**
     * Dodavanje aktivnosti na komande
     * @param command komanda
     * @param action nova aktivnost
     */
    protected synchronized void add(String command, Runnable action){
        if(!mapa.containsKey(command)){
            mapa.put(command, new ArrayList<>());
        }
        mapa.get(command).add(action);
    }
    
    /**
     * Brisanje komande (i svih njenih komandi)
     * @param command komanda za brisanje
     */
    protected synchronized void remove(String command){
        mapa.remove(command);
    }
    
    /**
     * Brisanje aktivnosti za  komande (i svih njenih komandi)
     * @param command komanda
     * @param action aktivnost
     */
    protected synchronized void remove(String command, Runnable action){
        if(!mapa.containsKey(command)) return; 
        mapa.get(command).remove(action);
        if(mapa.get(command).isEmpty())
            mapa.remove(command);
    }
    
    /**
     * Dodavanje koamnde na nivou vezaca 
     * @param action nova aktivnost
     */
    public synchronized void addDriverAction(Runnable action){
        lista.add(action);
    }
    
    /**
     * Brisanje komande na nivou komande
     * @param action aktivnost
     */
    public synchronized void removeDriverAction(Runnable action){
        lista.remove(action);
    }
}