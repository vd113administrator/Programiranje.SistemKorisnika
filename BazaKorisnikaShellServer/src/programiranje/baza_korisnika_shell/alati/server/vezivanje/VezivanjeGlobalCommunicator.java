package programiranje.baza_korisnika_shell.alati.server.vezivanje;

import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.server.ServerGlobalCommunicator;
import programiranje.baza_korisnika_shell.server.izlaz.IzlazThread;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Prenos upravljanja sa osnovnog servera na vezani server <br>
 * Prenosi se upravljanje protokolom datih naredbi i veza s klijentom <br>
 * @author Mikec
 */
public final class VezivanjeGlobalCommunicator{
    private static ServerGlobalCommunicator komunikator;
    /**
     * Postavljanje osnovnog serverskog komunikatora
     * @param k  osnovni serverski komunikator
     */
    public static void setServerCommunicator(ServerGlobalCommunicator k){
        komunikator = k; 
    }
    
    private static ServerDataAdapter serverAdapter;
    /**
     * Dobijanje serverskog adaptera
     * @return serverski adapter
     */
    public static ServerDataAdapter getServerAdapter(){
       return serverAdapter; 
    }
    /**
     * Postavljanje serverskog adaptera
     * @param adapter novi serverski adapter
     */
    public static void setServerAdapter(ServerDataAdapter adapter){
        serverAdapter = adapter;
    }
    
    private static IzlazThread it; 
    /**
     * Dobijanje izlazne niti
     * @return lokalna izlazna nit
     */
    public static IzlazThread getIzlazThread(){
        return it; 
    }
    /**
     * Postavljanje izlazne niti 
     * @param ithread izlazna nit 
     */
    public static void setIzlazThread(IzlazThread ithread){
        it = ithread; 
    }
}
