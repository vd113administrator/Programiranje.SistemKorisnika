/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.pogled.kontrola;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.ClientController;
import programiranje.baza_korisnika_shell.klijent.vezanje.AplikacijskiMod;

/**
 * Kontroler odjavljenosti
 * @author Mikec
 */
public class LoggedOutController extends ClientController implements Initializable {

    @FXML private AnchorPane main;
    @FXML private Button prijavaButton; 
    @FXML private Button registracijaButton; 
    
    @Override
    public Pane getPane() {
        return main;
    }
    
    @FXML 
    private void pritisakP(ActionEvent e){
        if(ApplicationMainProgram.getAplikacijskiMod() == AplikacijskiMod.SAMOSTALNA_APLIKACIJA){
          try {
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("prijava")));
            
            komunikator.getMain().getPane().getChildren().clear(); 
            komunikator.getMain().getPane().getChildren().add(p);    
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
       }
       if(ApplicationMainProgram.getAplikacijskiMod() == AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE){
          try {
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("prijavaVanjskojAplikaciji"))); 
            komunikator.getMain().getPane().getChildren().clear(); 
            komunikator.getMain().getPane().getChildren().add(p);    
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
       }
       if(ApplicationMainProgram.getAplikacijskiMod() == AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE_VEZANOG_SERVERA){
          try {
            
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("prijavaVanjskojAplikaciji")));
            komunikator.getMain().getPane().getChildren().clear(); 
            komunikator.getMain().getPane().getChildren().add(p);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
       }
    }
    
    @FXML 
    private void pritisakR(ActionEvent e){
        try {
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("registracija")));
            
            komunikator.getMain().getPane().getChildren().clear(); 
            komunikator.getMain().getPane().getChildren().add(p);
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        if(ApplicationMainProgram.getAplikacijskiMod()==AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE)
            main.getChildren().remove(registracijaButton);
        
        if(ApplicationMainProgram.getAplikacijskiMod()==AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE_VEZANOG_SERVERA)
            main.getChildren().remove(registracijaButton);
        
        komunikator.isprazniRed();
        komunikator.postaviNaRed(this);
        System.gc();
    }    
}
