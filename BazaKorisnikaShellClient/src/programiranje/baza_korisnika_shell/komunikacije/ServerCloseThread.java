/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.komunikacije;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.vezanje.AplikacijskiMod;

/**
 * Nit za kontrolu izlaza i prekida 
 * @author Mikec
 */
public class ServerCloseThread extends Thread{
    private Socket sock;
    private PrintWriter writer;
    private BufferedReader reader;
    private ClientDataAdapter adapter;
    private DigitalnaEnvelopa env; 
    
    public static final String PBKSP_CLOSE = "IZLAZ"; 
    public static final String PBKSP_STOP = "ZAUSTAVLJANJE";
    
    private ArrayList<Runnable> activitiesAfterMain = new ArrayList<>();
    private ArrayList<Runnable> activitiesBeforeMain = new ArrayList<>();
    
    /**
     * Konstruktor 
     * @param csock kontrolni prikljucak na server
     * @param cda komunikacija 
     * @throws IOException 
     */
    public ServerCloseThread(Socket csock, ClientDataAdapter cda) throws IOException{
        adapter = cda;
        sock = csock;
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
        
        env = adapter.getDigitalnaEnvelopa().instancirajSaIstomTackom(writer, reader);
        
        UpravljanjeDokumentima ud = new UpravljanjeDokumentima(); 
        SignatureManager sim = new SignatureManager(env, ud);
        
        try{
            sim.initSymetric();
            sim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
            sim.initSignature(); 
        }catch(Exception ex){
        }
        
        this.reader = new PotpisaniDekriptReader(sim);
        this.writer = new PotpisaniEnkriptWriter(sim,true);
    }
    
    /**
     * Aktivnost 
     */
    public void run(){
        for(Runnable r: activitiesBeforeMain) r.run();
        
        try {
            String com = reader.readLine();
            while(!com.equals(PBKSP_STOP) && !com.equals(PBKSP_CLOSE))
                com = reader.readLine();
            if(com.equals(PBKSP_STOP)) {
                zaustavljanje();
                while(!com.equals(PBKSP_CLOSE))
                    com = reader.readLine();
            }
            writer.close();
            reader.close();
            sock.close();
        } catch (IOException ex) {
        }
        
        for(Runnable r: activitiesAfterMain) r.run();
    }
    
    /**
     * Zaustavljanje niti i klijenta
     * @throws IOException 
     */
    public void zaustavljanje() throws IOException{
        Platform.runLater(()->{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Korisnik");
            alert.setContentText("Server je otkazao komunikaciju.");
            alert.showAndWait();
        });
        adapter.close();
        ApplicationMainProgram.termiante();
    }
    
    /**
     * Dodavanje aktivnosti prije glavne
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
}
