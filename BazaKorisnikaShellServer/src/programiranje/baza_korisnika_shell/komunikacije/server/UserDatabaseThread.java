/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.komunikacije.server;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.server.ServerGlobalCommunicator;

/**
 * Zasebna nit za spore operacije prema bazi, moguce izvodjenje performansi
 * @author Mikec
 */
public class UserDatabaseThread extends Thread{
    protected Deque<Runnable>  naredbe = new ArrayDeque<>(); 
    protected ServerGlobalCommunicator komunikator; 
    protected UserDatabaseAdapter databaseAdapter; 
    private boolean izlaz; 
    
    /**
     * Konstruktor servera sa spoljnim svijetom 
     * @param c komunikator 
     */
    public UserDatabaseThread(ServerGlobalCommunicator c){
        komunikator = c;
        databaseAdapter = komunikator.getDatabaseAdapter();
    }
    
    /**
     * Aktivnosti rada sa bazom podataka
     */
    public void run(){
        while(true){
            synchronized(this){
                try {
                    if(izlaz && naredbe.size()==0) break;
                    if(!izlaz && naredbe.size()==0) wait();
                    if(izlaz && naredbe.size()==0) break;
                    naredbe.pollFirst().run();
                } catch (InterruptedException ex) {
                }
            }
        }
    }
    
    /**
     * Dodavanje korisnika iz baze/ aktivnost na red cekanja
     * @param k korisnik
     */
    public synchronized void dodaj(Korisnik k){
        naredbe.addLast(()->{
            try {
                databaseAdapter.dodajKorisnika(k);
                System.out.println("Dodavanje u bazu : "+k);
            } catch (SQLException ex) {
            }
        });
        notify();
    }
    
  
    /**
     * Brisanje korisnika iz baze / aktivnost na red cekanja
     * @param k korisnik
     */
    public synchronized void obrisi(Korisnik k){
        naredbe.addLast(()->{
            try {
                databaseAdapter.obrisiKorisnika(k);
                System.out.println("Brisanje iz baze : "+k);
            } catch (SQLException ex) {
            }
        });
        notify();
    }
    
    /**
     * Promjena parametara 
     * @param oldusername staro korisnicko ime
     * @param newusername novo korisnicko ime
     * @param newop novi opisni podaci
     */
    public synchronized void promenaParametara(String oldusername, String newusername, OpisniPodaci newop){
        naredbe.addLast(()->{
            try{
                boolean x = databaseAdapter.promenaParametara(oldusername, newusername, newop);
                System.out.println("Promena parametara korisnika baze. "+oldusername+":"+x);
            }catch(Exception ex){
            }
        });
        notify();
    }
    
    /**
     * Promjena sifre
     * @param oldusername staro korisnicko ime 
     * @param newap nova sifra 
     */
    public synchronized void promenaSifre(String oldusername, AutentifikacioniPodaci newap){
        naredbe.addLast(()->{
            try{
                boolean x = databaseAdapter.promenaSifre(oldusername, newap);
                System.out.println("Promena sifre korisnika baze. "+oldusername+":"+x);
            }catch(Exception ex){
            }
        });
        notify();
    }
    
    /**
     * Promjena tipa korisnika
     * @param oldusername staro korisnicko ime 
     * @param newType novi tip
     */
    public synchronized void promenaTipa(String oldusername, String newType){
        naredbe.addLast(()->{
            try{
                boolean x = databaseAdapter.promenaTipa(oldusername, newType);
                System.out.println("Promena parametara korisnika baze. "+oldusername+":"+x);
            }catch(Exception ex){
            }
        });
        notify();
    }
    
    private transient boolean resProvereSifre;
    private transient boolean nowait; 
    public boolean proveraSifre(String oldusername, AutentifikacioniPodaci newap){
        final Object synchron = new Object(); 
        nowait = false;
        resProvereSifre = false;
        synchronized(this){
          naredbe.addLast(()->{
            try{
                boolean x = databaseAdapter.proveraSifre(oldusername, newap);
                System.out.println("Provera sifre korisnika baze - "+oldusername+":"+x);
                resProvereSifre = x;
            }catch(Exception ex){
            }
            synchronized(synchron){
                nowait = true;
                synchron.notify();
            }
          });
          notify();
        }
        synchronized(synchron){
            if(!nowait)
                try {
                    synchron.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserDatabaseThread.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        return resProvereSifre; 
    }
    
    /**
     * Izlaz
     */
    public synchronized void izlaz(){
        notify();
        izlaz = true; 
    }
}
