/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import programiranje.baza_korisnika_shell.klijent.vezanje.AplikacijskiMod;
import programiranje.baza_korisnika_shell.klijent.vezanje.VezivanjeGlobalKomunikator;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;

/**
 * Glavni aplikacioni program 
 * @author Mikec
 */
public class ApplicationMainProgram extends Application {
    private static Properties resursi = new Properties();
    private static GlobalApplicationCommunicator komunikator;
    private static ClientDataAdapter serverAdapter;
    private static AplikacijskiMod mod = AplikacijskiMod.SAMOSTALNA_APLIKACIJA;
    private static boolean terminated; 
    private static boolean closed; 
    
    /**
     * @return aplikacijski mod u vezi vezivanja
     */
    public static AplikacijskiMod getAplikacijskiMod(){
        return mod;
    }
    
    /**
     * Postavi aplikacijski mod u vezi vezivanja 
     * @param mode novi aplikacioni mod
     */
    public static void setAplikacijskiMod(AplikacijskiMod mode){
        mod = mode;
    }
    
    /**
     * Prekidanje rada aplikacije 
     */
    public static void termiante(){
        terminated = true;
    }
    
    /**
     * @return globalni aplikacijski komunikator 
     */
    public static GlobalApplicationCommunicator getGlobalApplicationCommunicator(){
        return komunikator; 
    }
    
    /**
     * @return komunikacija prema serveru
     */
    public static ClientDataAdapter getClientDataAdapter(){
        return serverAdapter; 
    }
    
    /**
     * @return FXML graficki resursi
     */
    public static Properties getGrafickiResursi(){
        return resursi; 
    }
    
    /**
     * @return da li je terminirano 
     */
    public static boolean isTerminated(){
        return terminated; 
    }
    
    /**
     * @return da li je izasao iz aplikacije 
     */
    public static boolean isClosed(){
        return closed; 
    }
    
    /**
     * Ulazna tacka aplikacije 
     * @param stage forma 
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        VezivanjeGlobalKomunikator.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource(resursi.getProperty("main")));
        Scene scene = new Scene(root,500,600);
        stage.setTitle("Korisnik");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ulazna tacka programa 
     * @param args argumenti 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException, CertificateException {
        resursi.load(ApplicationMainProgram.class.getResourceAsStream(
                "/programiranje/baza_korisnika_shell/pogled/Resources.properties"));
        
        komunikator = new GlobalApplicationCommunicator();
        serverAdapter = new ClientDataAdapter(komunikator);
        
        VezivanjeGlobalKomunikator.setPrijavaKomunikator(komunikator);
        VezivanjeGlobalKomunikator.setAdapterZaServer(serverAdapter);
        
        if(terminated) {
            Platform.exit(); 
            return;
        }
        
        launch(args);
        
        if(terminated) return;
        serverAdapter.close();
        closed = true; 
    }
}
