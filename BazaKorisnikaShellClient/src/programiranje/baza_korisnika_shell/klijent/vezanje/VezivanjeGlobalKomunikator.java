/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent.vezanje;

import javafx.stage.Stage;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.klijent.GlobalApplicationCommunicator;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.ServerCloseThread;

/**
 * Globalni podaci i metode za komuniciranje vezanih aplikacija 
 * @author Mikec
 */
public final class VezivanjeGlobalKomunikator {
    private VezivanjeGlobalKomunikator(){}
    
    private static String sessionId; 
    /**
     * Postavljanje sesijskog identifikatora 
     * @param sId novi sesijski identifikator 
     */
    public static void setSessionId(String sId){
        sessionId = sId; 
    }
    
    /**
     * Dobijanje sesijskog identifikatora 
     * @return sesijski identifikator
     */
    public static String getSessionId(){
        return sessionId;
    }
    
    private static OpisniPodaci podaci;
    
    /**
     * Postavljanje opisnih podataka 
     * @param data podaci 
     */
    public static void setOpis(OpisniPodaci data){
        podaci = data;
    }
    
    /**
     * Dobijanje opisnih podataka
     * @return opisni podaci
     */
    public static OpisniPodaci getOpis(){
        return podaci;
    }
    
    private static String username;
    /**
     * Postavljanje korisnickog imena 
     * @param uname korisnicko ime
     */
    public static void setKorisnickoIme(String uname){
        username = uname; 
    }
    
    /**
     * Dobijanje korisnickog imena 
     * @return korisnicko ime
     */
    public static String getKorisnickoIme(){
        return username;
    }
    
    
    private static GlobalApplicationCommunicator prijavaKomunikator; 
    
    /**
     * Globalni aplikacijski komunikator 
     * @param komunikator komunikator pocetne aplikacije 
     */
    public static void setPrijavaKomunikator(GlobalApplicationCommunicator komunikator){
        prijavaKomunikator = komunikator;
    }
    
    /**
     * Dobijanje komunikatora 
     * @return komunikator 
     */
    public static GlobalApplicationCommunicator getKomunikator(){
        return prijavaKomunikator; 
    }
    
    private static ClientDataAdapter adapterZaServer; 
    /**
     * Dobijanje komunikacije sa klijentom 
     * @return komunikacija 
     */
    public static ClientDataAdapter getAdapterZaServer(){
        return adapterZaServer; 
    }
    
    /**
     * Postavljanje komunikacije 
     * @param adapter komunikacija
     */
    public static void setAdapterZaServer(ClientDataAdapter adapter){
        adapterZaServer = adapter;
    }
    
    private static ServerCloseThread serverskiPrekidi; 
    
    /**
     * Nit za izlaz 
     * @return nit za kontrolu izlaz i prekida
     */
    public static ServerCloseThread getServerCloseThread(){
        return serverskiPrekidi; 
    }
    
    /**
     * Postavljanje niti za izlaz
     * @param thread nova nit za izlaz i pekide 
     */
    public static void setServerCloseThread(ServerCloseThread thread){
        serverskiPrekidi = thread;
    }
    
    private static Stage stage; 
    
    /**
     * @return tekuca forma
     */
    public static Stage getStage(){
        return stage; 
    }
    
    /**
     * Postaljanje nove forme 
     * @param s forma
     */
    public static void setStage(Stage s){
        stage = s;  
    }
    
    private static VezivanjeInterface v; 
    
    /**
     * Postavljanje novog interfejsa za vezivanje 
     * @param vi  novi interfejs za vezivanje 
     */
    public static void setVezivanjeInterface(VezivanjeInterface vi){
        v = vi; 
    }
    
    /**
     * Dobijanje interfejsa za vezivanje 
     * @return interfejs vezivanja 
     */
    public static VezivanjeInterface getVezivanjeInterface(){
        return v; 
    }
}
