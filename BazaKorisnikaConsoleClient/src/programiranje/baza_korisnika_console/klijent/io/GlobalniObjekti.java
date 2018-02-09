/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.klijent.io;

import programiranje.baza_korisnika_console.staticno.StatickiPodaci;
import java.io.IOException;
import java.net.Socket;

/**
 * Globalni objekti i metodi na nivou aplikacije
 * @author Mikec
 */
public final class GlobalniObjekti {
    private static Socket shellServerSocket;
    private static Socket shellControlServerSocket; 
    private static AdapterServera serverAdapter; 
    private static String connectionId; 
    private static String sessionId; 
    
    public static final String SESSIONTYPE_BKSP_BASIC = "BASIC"; 
    public static final String SESSIONTYPE_BKSP_TIMED = "TIMED";
   
    private GlobalniObjekti(){
        setShellServer();
    }
    
    /**
     * Inicijalizacija adapter za server
     */
    public static void setShellServer(){
        try {
            resetShellServer();
            shellServerSocket = new Socket(StatickiPodaci.getServerAddress(),StatickiPodaci.serverPort);
            shellControlServerSocket = new Socket(StatickiPodaci.getServerAddress(),StatickiPodaci.serverControlPort);
            serverAdapter = new AdapterServera(shellServerSocket,shellControlServerSocket);
            serverAdapter.writeLine(SESSIONTYPE_BKSP_TIMED);
            connectionId = serverAdapter.readLine();
        } catch (Exception ex) {
            shellServerSocket = null;
            shellControlServerSocket = null;
            serverAdapter = null;
            connectionId = null; 
        }
    }
    
    /**
     * Ponistavanje adaptera za server
     */
    public static void resetShellServer(){
        try {
            if(serverAdapter!=null) serverAdapter.izlaz();
            if(shellServerSocket!=null) shellServerSocket.close();
            if(shellServerSocket!=null) shellControlServerSocket.close();
        } catch (IOException ex) {
        } finally {
           shellServerSocket = null;
           shellControlServerSocket = null;
           serverAdapter = null;
           connectionId = null; 
           sessionId = null;
        }
    }
    
    /**
     * Dobijanje aplikacionog adaptera za server 
     * @return adapter servera
     */
    public  static AdapterServera getShellServerAdapter(){
        return serverAdapter; 
    }
    
    /**
     * Dobijanje identifikacije konekcije 
     * @return konekcioni identifikator
     */
    public static String getConnectionId(){
        return connectionId;
    }
    
    /**
     * Dobijanje sesijskog identifikatora 
     * @return sesijski identifikator
     */
    public static String getSessionId(){
        return sessionId; 
    }
    
    /**
     * Postavljanje ili ponistavanje sesijskog identifikatora
     * @param str novi identifikator
     */
    public static void setSessionId(String str){
        sessionId = str; 
    }
}
