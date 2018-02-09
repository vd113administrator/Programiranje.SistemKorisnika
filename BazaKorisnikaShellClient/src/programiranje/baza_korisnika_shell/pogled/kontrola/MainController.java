/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.pogled.kontrola;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.ClientController;

/**
 * Kontroler pocetne stranice 
 * @author Mikec
 */
public class MainController extends ClientController implements Initializable {
    @FXML private AnchorPane main;
    
    @Override
    public Pane getPane() {
        return main;
    }
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            super.initialize(url, rb);
            komunikator.setMain(this);
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("loggedout")));
            
            main.getChildren().clear(); 
            main.getChildren().add(p);
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
