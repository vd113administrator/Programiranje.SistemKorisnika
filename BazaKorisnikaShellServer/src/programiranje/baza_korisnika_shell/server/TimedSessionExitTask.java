/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

import java.util.Timer;
import java.util.TimerTask;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import static programiranje.baza_korisnika_shell.server.ServerMainThread.PBKSP_STOP;

/**
 * Zadatak za prekidanje komunikacije po isteku vremena vremenski ogranicene sesije 
 * @author Mikec
 */
public class TimedSessionExitTask extends TimerTask{
   private ServerMainThread nit; 
   private ServerDataAdapter adapter; 
   private Timer timer;  
   
   /**
    * Konstruktor
    * @param th nit kojom se realizuje klijent server komunikacija za datu sesiju 
    * @param a komunikacijsko sredstvo sa klijentom sinhronizovano sa niti 
    * @param t sat 
    */
   public TimedSessionExitTask(ServerMainThread th, ServerDataAdapter a, Timer t){
       adapter = a; 
       timer = t;
       nit = th; 
   }

    /**
     * Aktivnost zadatak - zaustavljanje 
     */
    @Override
    public synchronized void run() {
        if(nit.isClosed()) return;
        synchronized(nit){
            timer.cancel();
            timer.purge();
            adapter.cWriteLine(PBKSP_STOP);
        }
    }
    
    /**
     * Odustajanje od zaustavljanja
     */
    public synchronized void odustani(){
        timer.cancel();
        timer.purge();
    }
}
