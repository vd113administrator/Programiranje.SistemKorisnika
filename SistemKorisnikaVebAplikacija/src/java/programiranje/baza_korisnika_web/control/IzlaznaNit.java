/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_web.global.SesijskiObjekti;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_CLOSE;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_ODIJAVA;

/**
 * Nit kojom se kontrolisu izlazi i prekidi 
 * @author Mikec
 */
public class IzlaznaNit extends Thread{
    private AdapterServera server; 
    
    public static final String PBKSP_CLOSE = "IZLAZ"; 
    public static final String PBKSP_STOP = "ZAUSTAVLJANJE";
    
    private ArrayList<Runnable> activitiesAfterMain = new ArrayList<>();
    private ArrayList<Runnable> activitiesBeforeMain = new ArrayList<>();
    
    private SesijskiObjekti sesijskiObjekti; 
    
    /**
     * Konstruktor 
     * @param server komunikacija ka serveru BK 
     */
    public IzlaznaNit(AdapterServera server){
        this.server = server;
    }
    
    /**
     * Dobijanje komunikacije prema serveru 
     * @return komunikacija 
     */
    public AdapterServera getAdapterServera(){
        return server; 
    }
    
    /**
     * Radna metoda
     */
    @Override
    public void run(){
        synchronized(server){
            for(Runnable r: activitiesBeforeMain) r.run();
        
            try {
                String com = server.cReadLine();
                while(!com.equals(PBKSP_STOP) && !com.equals(PBKSP_CLOSE))
                com = server.cReadLine();
                if(com.equals(PBKSP_STOP)) {
                    if(sesijskiObjekti.getSessionId()!=null){
                        sesijskiObjekti.getShellServerAdapter().odijavaOut();
                    }
                    server.writeLine(BKSP_CLOSE);
                    while(!com.equals(PBKSP_CLOSE))
                        com = server.cReadLine();
                }
               
                sesijskiObjekti.resetShellServer();
                server.getServerS().close();
                
                } catch (IOException ex) {
                }
        
            for(Runnable r: activitiesAfterMain) r.run();
        }
    }
    
    /**
     * Dodavanje aktivnosti prije izlaza
     * @param r aktivnost
     */
    public void dodajAktivnostiPrijeGlavne(Runnable r){
        activitiesBeforeMain.add(r);
    }
    
    /**
     * Dodavanje aktivnosti posle izlaza 
     * @param r aktivnost
     */
    public void dodajAktivnostiPosleGlavne(Runnable r){
        activitiesAfterMain.add(r);
    }
    
    /**
     * Dobijanje sesijskih objekata
     * @return sesijski objekti 
     */
    public SesijskiObjekti getSesijskiObjekti(){
        return sesijskiObjekti;
    }
    
    /**
     * Postavljanje drugih sesijskih objekata 
     * @param sesija sesija
     */
    public void setSesijskiObjekti(SesijskiObjekti sesija){
        sesijskiObjekti = sesija; 
    }
}
