/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_config.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import programiranje.baza_korisnika_shell.config.data.ShellClientConfiguration;
import programiranje.baza_korisnika_shell.constants.StatikaKonfiguracije;

public class Aplikacija extends Application {
    private static ShellClientConfiguration konfiguracija = new ShellClientConfiguration(); 
    
    public static ShellClientConfiguration getKonfiguracija(){
        return konfiguracija; 
    }
    
    public static void setKonfiguracija(ShellClientConfiguration konfig){
        konfiguracija = konfig; 
    }
    
    public static void load(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(StatikaKonfiguracije.clientConfigFile)));
            konfiguracija = (ShellClientConfiguration) ois.readObject();
            if(konfiguracija==null) konfiguracija = new ShellClientConfiguration();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                ois.close();
            } catch (Exception ex) {    
            }
        }
    }
    
    public static void write() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(StatikaKonfiguracije.clientConfigFile)));
        oos.writeObject(konfiguracija);
        oos.close();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Vizuelizacija.fxml"));
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        load();
        launch(args);
    }
}
