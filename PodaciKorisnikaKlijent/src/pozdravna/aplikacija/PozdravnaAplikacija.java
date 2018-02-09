/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pozdravna.aplikacija;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import programiranje.baza_korisnika_shell.klijent.vezanje.VezivanjeInterface;

/**
 * 
 * @author Mikec
 */
public class PozdravnaAplikacija extends Application implements VezivanjeInterface{
    private static String[] arguments; 
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PozdravnaAplikacijaView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Korisnik");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        arguments = args; 
        PozdravnaAplikacija app = new PozdravnaAplikacija(); 
        app.poziv(args);
    }

    @Override
    public void run() {
        try {
            Stage stage = new Stage();
            start(stage);
        } catch (Exception ex) {
            Logger.getLogger(PozdravnaAplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
