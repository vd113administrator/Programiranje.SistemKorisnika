/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import programiranje.baza_korisnika.cert.alg.UpravljanjeSiframa;
import programiranje.baza_korisnika.cert.util.SaltManager;
import programiranje.baza_korisnika_shell.config.data.ShellServerConfiguration;
import programiranje.baza_korisnika_shell.constants.StatikaKonfiguracije;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseThread;

/**
 * Osnovni serverski komunikaor 
 * @author Mikec
 */
public class ServerGlobalCommunicator {
    private UserDatabaseAdapter databaseAdapter; 
    private UserDatabaseThread databaseThread;
    private SaltManager saltManager; 
    private UpravljanjeSiframa upravljanjeSiframa; 
    
    /**
     * Komunikacija baze korisnika 
     * @return adapter
     */
    public UserDatabaseAdapter getDatabaseAdapter(){
        return databaseAdapter; 
    }
    
    /**
     * Postavljanje komunikacije prema bazi podataka 
     * @param adapter 
     */
    public void setDatabaseAdapter(UserDatabaseAdapter adapter){
        databaseAdapter = adapter; 
    }
    
    /**
     * Dobijanje niti za aktivnosti baze podataka
     * @return nit
     */
    public UserDatabaseThread getDatabaseThread(){
        return databaseThread; 
    }
    
    /**
     * Postavljanje nit baze podataka 
     * @param thread nova nit baze podataka 
     */
    public void setDatabaseThread(UserDatabaseThread thread){
        databaseThread = thread; 
    }
    
    private ShellServerConfiguration konfiguracija = new ShellServerConfiguration("");
    {loadKonfiguracija();}
    
    /**
     * Ocitavanje konfiguracije 
     */
    private void loadKonfiguracija(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(StatikaKonfiguracije.serverConfigFile)));
            konfiguracija = (ShellServerConfiguration) ois.readObject();
            if(konfiguracija==null) konfiguracija = new ShellServerConfiguration("");
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                ois.close();
            } catch (Exception ex) {    
            }
        }
    }
    
    /**
     * @return konfiguracija servera 
     */
    public ShellServerConfiguration getKonfiguracija(){
        return konfiguracija;
    }
    
    /**
     * @return menadzer saltova 
     */
    public SaltManager getSaltManager(){
        return saltManager;
    }
    
    /**
     * @return menadzer za sifre 
     */
    public UpravljanjeSiframa getUpravljanjeSiframa(){
        return upravljanjeSiframa; 
    }
    
    /**
     * Postavljanje menadzera za saltove 
     * @param sm menadzer za slat
     */
    public void setSaltManager(SaltManager sm){
        saltManager = sm; 
    }
    
    /**
     * Postavljanje upravljanja siframa 
     * @param us upravljanje siframa 
     */
    public void setUpravljanjeSiframa(UpravljanjeSiframa us){
        upravljanjeSiframa = us; 
    }
    
    public void setKonfiguracija(ShellServerConfiguration konfiguracija){
        this.konfiguracija = konfiguracija;
    }
}
