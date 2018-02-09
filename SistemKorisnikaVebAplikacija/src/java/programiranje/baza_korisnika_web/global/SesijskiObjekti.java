/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.global;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import programiranje.baza_korisnika_web.control.AdapterServera;

/**
 * Obijekti vezani za sesiju veb aplikacije 
 * @author Mikec
 */
public final class SesijskiObjekti implements Serializable{
    private transient Socket shellServerSocket;
    private transient Socket shellControlServerSocket;
    private transient AdapterServera serverAdapter;
    private transient String connectionId;
    private transient String sessionId;
    
    public static final String SESSIONTYPE_BKSP_BASIC = "BASIC"; 
    public static final String SESSIONTYPE_BKSP_TIMED = "TIMED";
    
    /**
     * Konstruktor
     */
    public SesijskiObjekti(){
        setShellServer();
    }
    
    /**
     * Inicijalizacija 
     */
    public void setShellServer(){
        try {
            resetShellServer();
            shellServerSocket = new Socket(StatickiPodaci.getServerAddress(),StatickiPodaci.serverPort);
            shellControlServerSocket = new Socket(StatickiPodaci.getServerAddress(),StatickiPodaci.serverControlPort);
            serverAdapter = new AdapterServera(shellServerSocket,shellControlServerSocket);
            serverAdapter.setSesijskiObjekti(this);
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
     * Ponistavanje 
     */
    public void resetShellServer(){
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
     * Dobijanje adaptera servera baze korisnika
     * @return adapter servera 
     */
    public  AdapterServera getShellServerAdapter(){
        return serverAdapter; 
    }
    
    /**
     * Dobijanje konekcijskog identifikatora 
     * @return konekcijski identifikator 
     */
    public String getConnectionId(){
        return connectionId;
    }
    
    /**
     * Setovanje konekcijskog identifikatora
     * @param cid konekcijski identifikator
     */
    public void setConnectionId(String cid){
        connectionId = cid;
    }
    
    /**
     * Dobijanje identifikatora sesije prema serveru 
     * @return identifikator server sesije 
     */
    public String getSessionId(){
        return sessionId; 
    }
    
    /**
     * Postavljanje identifikatora sesije 
     * @param str identifikator sesije 
     */
    public void setSessionId(String str){
        sessionId = str; 
    }
    
    /**
     * Dobijenje korisnickog imena prijavljenog korisnika
     * @return korisnicko ime 
     * @throws IOException 
     */
    public String getLoggedInUsername() throws IOException{
        try{ return serverAdapter.podaciIn().getValue().get(0); }
        catch(IndexOutOfBoundsException ex){ return ""; }
    }
}
