/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeSiframa;
import programiranje.baza_korisnika.cert.util.SaltManager;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseThread;
import static programiranje.baza_korisnika_shell.server.ServerStatics.serverControlPort;
import static programiranje.baza_korisnika_shell.server.ServerStatics.serverPort;
import programiranje.baza_korisnika_shell.server.extensions.BKSPProtocolExtension;
import programiranje.baza_korisnika_shell.server.izlaz.IzlazThread;

/**
 * Ulazna tacka servera 
 * @author Mikec
 */
public class ServerMainProgram {
    public static boolean RUNNING = true;
    public static boolean BLOCKED = false;
    
    private static ServerGlobalCommunicator komunikator; 
    private static UserDatabaseAdapter databaseAdapter; 
    private static UserDatabaseThread databaseThread; 
    private static IzlazThread izlazThread;
    private static ServerSocket ss;
    private static ServerSocket cs;
    private static SaltManager sm; 
    private static UpravljanjeSiframa us; 
    private static BKSPProtocolExtension bpe;
    
    static { komunikator = new ServerGlobalCommunicator();}
    
    /**
     * @return osnovni serverski komunikator 
     */
    public static ServerGlobalCommunicator getServerKomunikator(){
        return komunikator; 
    }
    
    /**
     * @return komunikacija sa bazom podataka 
     */
    public static UserDatabaseAdapter getUserDatabaseAdapter(){
        return databaseAdapter; 
    }
    
    /**
     * Aplikacija 
     * @param args argumenti 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException{ 
            sm = new SaltManager();
            us = new UpravljanjeSiframa(); 
            
            komunikator.setSaltManager(sm);
            komunikator.setUpravljanjeSiframa(us);
            
            String host = komunikator.getKonfiguracija().getDatabaseHost();
            String user = komunikator.getKonfiguracija().getDatabaseUser();
            String password = komunikator.getKonfiguracija().getDatabasePassword();
            
            databaseAdapter = new UserDatabaseAdapter(host,user,password);
            komunikator.setDatabaseAdapter(databaseAdapter);
            
            databaseThread = new UserDatabaseThread(komunikator);
            izlazThread = new IzlazThread();
            komunikator.setDatabaseThread(databaseThread);
            
            databaseAdapter.konektuj();
            databaseThread.start();
            databaseAdapter.ucitavanje();
            
            izlazThread.start();
            
            try {
                ss = new ServerSocket(serverPort);
                cs = new ServerSocket(serverControlPort); 
                System.out.println("Server running...");
                while (RUNNING) {
                    if(!RUNNING) break;
                    BLOCKED = true; 
                    Socket sock = ss.accept();
                    Socket csock = cs.accept(); 
                    BLOCKED = false;
                    if(!RUNNING) break; 
                    ServerMainThread st =
                            new ServerMainThread(sock, csock).setBKSPPotocolExtension(bpe);
                    st.start();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                izlazThread.close();
            }
            
            databaseThread.izlaz();
            databaseAdapter.raskonektuj();
	}
	
        /**
         * Postavi aktivno
         * @param running parametar
         */
	public static void setRunning(boolean running) {
		RUNNING = running;
	}
        
        /**
         * Izlazak
         * @throws IOException 
         */
        public static void close() throws IOException{
            setRunning(false);
            System.out.println("Gasenje servera.");
            ServerMainThread.closeAll();
            if(!BLOCKED) return;
            Socket socket = new Socket(ss.getInetAddress(),serverPort);
            socket.close();
            Socket csocket = new Socket(cs.getInetAddress(),serverControlPort);
            csocket.close();
        }
        
        /**
         * @return ekstenzija
         */
        public static BKSPProtocolExtension getBKSPExtension(){
            return bpe;
        }
        
        /**
         * Postavljenje ekstenzije
         * @param e ekstenzija aplikacije 
         * @return 
         */
        public static boolean setBKSPExtension(BKSPProtocolExtension e){
            if(bpe!=null) return false; 
            bpe = e; 
            return true; 
        }
}
