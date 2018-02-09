/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Upravljac svim vezacima aplikacije
 * @author Mikec 
 */
public class DriverManager implements Serializable{
    private ArrayList<Driver> vezaci = new ArrayList<Driver>();
    private transient ArrayList<Runnable> akcije = new ArrayList<Runnable>(); 
    
    /**
     * Konstruktor
     */
    public DriverManager(){
        vezaci.add(new BazaKorisnikaDriver());
        vezaci.add(new SistemKorisnikaDriver());
        vezaci.add(new OpisivanjeDriver());
        vezaci.add(new UlazniDriver());
    }
    
    /**
     * Dodavanje vezaca
     * @param d novi vezac dogadjaja 
     */
    public synchronized void addDriver(Driver d){
        vezaci.add(d);
    }
    
    /**
     * Brisanje vezaca
     * @param d vezac
     */
    public synchronized void removeDriver(Driver d){
        vezaci.remove(d);
    }
    
    /**
     * Izvrsavanje komandi
     * @param command komanda
     */
    public synchronized void go(String command){
        for(Driver d: vezaci){
           d.go(command);
        }
    }
    
    /**
     * Izvrsavanje komandi specificnog vezaca
     * @param vezac klasa za vezac
     * @param command komanda 
     */
    public synchronized void go(Class<? extends Driver> vezac, String command){
        for(Driver d: vezaci){
            if(d.getClass().equals(vezac))
                d.go(command);
        }
        for(Runnable run: akcije){
            run.run();
        }
    }
    
    /**
     * Dodavanje aktivnosti na nivou menadzara
     * @param run nova aktivnost
     */
    public synchronized void addManagerAction(Runnable run){
        akcije.add(run);
    }
    
    /**
     * Brisanje aktivnosti na nivou menadzera
     * @param run aktivnost
     */
    public synchronized void removeManagerAction(Runnable run){
        akcije.add(run);
    }
    
    /**
     * Dobijanje prvog vezaca za zadanu klasu
     * @param klasa aktivnost
     * @return dati vezac
     */
    public synchronized Driver getFirstDriver(Class<? extends Driver> klasa){
        for(Driver d: vezaci)
            if(d.getClass().equals(klasa)) return d;
        return null; 
    }
}
