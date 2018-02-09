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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.ClientController;

/**
 * Kontroler deregistracije 
 * @author Mikec
 */
public class DeregistracijaController extends ClientController implements Initializable{
    @FXML private AnchorPane main;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button deregister;
    @FXML private Button cancel; 
    
    @FXML private void deregister(ActionEvent e){
        try {
            boolean res = serverAdapter.deregistracija(username.getText(), password.getText());
            if(!res) return;
            Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("loggedout")));
            komunikator.getMain().getPane().getChildren().clear();
            komunikator.getMain().getPane().getChildren().addAll(p);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Greska u komunikaciji sa serverom.");
            alert.setTitle("Korisnik");
            alert.showAndWait();
            ApplicationMainProgram.termiante();
            Platform.exit();
        }
    }
    
    @FXML private void cancel(ActionEvent e){
        komunikator.skiniSaReda();
        komunikator.getMain().getPane().getChildren().clear();
        komunikator.getMain().getPane().getChildren().addAll(komunikator.dobijSaReda().getPane());
        System.gc();
    }
    
    @Override
    public Pane getPane() {
        return main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       super.initialize(url, rb);
       komunikator.postaviNaRed(this);
    }    
}
