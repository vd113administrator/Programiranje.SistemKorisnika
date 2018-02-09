/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server.izlaz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import programiranje.baza_korisnika_shell.alati.server.vezivanje.VezivanjeGlobalCommunicator;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;

/**
 * Nit za koja obuhvaca medjudjelovanje forme i servera 
 * @author Mikec
 */
public class IzlazThread extends Thread{
    private String[] args;
    private ArrayList<Runnable> activitiesAfterMain = new ArrayList<>();
    private ArrayList<Runnable> activitiesBeforeMain = new ArrayList<>(); 
    
    /**
     * Dodavane aktivnosti prije pokretanja forme 
     * @param r aktivnost
     */
    public void dodajAktivnostiPrijeGlavne(Runnable r){
        activitiesBeforeMain.add(r);
    }
    
    /**
     * Dodavanje aktivnosti posle glavne 
     * @param r aktivnost
     */
    public void dodajAktivnostiPosleGlavne(Runnable r){
        activitiesAfterMain.add(r);
    }
    
    /**
     * Konstruktor izlazne niti 
     * @param args 
     */
    public IzlazThread(String ... args){
        this.args = args;
    }
    
    /**
     * Izvrsavanje aktivnosti i pokretanje forme, te osluskivanje
     */
    @Override
    public void run(){
        VezivanjeGlobalCommunicator.setIzlazThread(this);
        try {
            IzlazApplication.pokreni(args);
            for(Runnable r: activitiesBeforeMain) r.run();
            ServerMainProgram.close();
            for(Runnable r: activitiesAfterMain) r.run();
        } catch (IOException ex) {
            Logger.getLogger(IzlazThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Izlaz iz forme, samim tim i servera 
     */
    public void close(){
        Platform.exit();
    }
}
