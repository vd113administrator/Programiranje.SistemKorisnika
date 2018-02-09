/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.klijent;

import programiranje.baza_korisnika_console.klijent.io.AdapterServera;
import java.io.IOException;
import java.util.ArrayList;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_CLOSE;

/**
 * Nit kojom se kontrolise izlaz iz aplikacije u slucaju prekida i normalnog izlaza
 * @author Mikec
 */
public class IzlaznaNit extends Thread{
    private AdapterServera server; 
    
    public static final String PBKSP_CLOSE = "IZLAZ"; 
    public static final String PBKSP_STOP = "ZAUSTAVLJANJE";
    
    private ArrayList<Runnable> activitiesAfterMain = new ArrayList<>();
    private ArrayList<Runnable> activitiesBeforeMain = new ArrayList<>();
    
    /**
     * Konstruktor 
     * @param server adapter servera  
     */
    public IzlaznaNit(AdapterServera server){
        this.server = server;
        System.out.println("Izlazna nit stvorena.");
    }
    
    /**
     * Dobijanje adaptera servera 
     * @return adapter servera 
     */
    public AdapterServera getAdapterServera(){
        return server; 
    }
    
    /**
     * Aktivnosti osluskivanja prekida i radna funkcija niti
     */
    @Override
    public void run(){
        System.out.println("Izlzna nit pokrenuta.");
        synchronized(server){
            for(Runnable r: activitiesBeforeMain) r.run();
        
            try {
                String com = server.cReadLine();
                while(!com.equals(PBKSP_STOP) && !com.equals(PBKSP_CLOSE))
                com = server.cReadLine();
                if(com.equals(PBKSP_STOP)) {
                    while(!com.equals(PBKSP_CLOSE)){
                        server.writeLine(BKSP_CLOSE);
                        com = server.cReadLine();
                    }
                    if(!Aplikacija.isClosed())  
                      synchronized(Aplikacija.class){
                        if(Aplikacija.getSesija()!=null) Aplikacija.odjava();
                        try{server.writeLine(BKSP_CLOSE);}
                        catch(Exception ex){ex.printStackTrace();}
                        try{server.izlaz();}
                        catch(Exception ex){ex.printStackTrace();}
                        try{server.getServerCS().close();}
                        catch(Exception ex){ex.printStackTrace();}
                        try{server.getServerS().close();}
                        catch(Exception ex){ex.printStackTrace();}
                        Aplikacija.setClosed();
                        Aplikacija.getInputThread().zaustavljanje();
                      }
                }
            }
            catch (IOException ex) {
            }
            
            for(Runnable r: activitiesAfterMain) r.run();
        }
        System.out.println("Izlzna nit zatvoreana.");
    }
    
    /**
     * Dodavanje predaktivnosti glavne aktivnosti 
     * @param r aktivnost
     */
    public void dodajAktivnostiPrijeGlavne(Runnable r){
        activitiesBeforeMain.add(r);
    }
    
    /**
     * Dodavanje postaktivnosti glavne aktivnosti 
     * @param r aktivnost
     */
    public void dodajAktivnostiPosleGlavne(Runnable r){
        activitiesAfterMain.add(r);
    }
}
