/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent.vezanje.kontroler;

import programiranje.baza_korisnika_shell.pogled.kontrola.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.ClientController;
import programiranje.baza_korisnika_shell.klijent.vezanje.AplikacijskiMod;
import programiranje.baza_korisnika_shell.klijent.vezanje.VezivanjeGlobalKomunikator;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;

/**
 * Prijavni kontroler za vezane aplikacije 
 * @author Mikec
 */
public class PrijavaVanjskojAplikacijiController extends ClientController implements Initializable {
    @FXML private AnchorPane main;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button login;
    @FXML private Button cancel; 
    
    public static final String BKSP_N1_VEZANJE = "VEZANJE";
    
    /**
     * Aktivnost prijavne forme za vezane aplikacije 
     * @param e desavanje 
     */
    @FXML private void login(ActionEvent e){
        try {
            serverAdapter.prijava(username.getText(), password.getText());
            password.setText("");
            String s = serverAdapter.getSessionId();
            if(s==null) return; 
            VezivanjeGlobalKomunikator.setSessionId(s);
            komunikator.getMain().getPane().getChildren().clear();
            VezivanjeGlobalKomunikator.getStage().close();
            VezivanjeGlobalKomunikator.getVezivanjeInterface().run();
            if(ApplicationMainProgram.getAplikacijskiMod()!=
                    AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE_VEZANOG_SERVERA) return;
            ClientDataAdapter adapter = VezivanjeGlobalKomunikator.getAdapterZaServer(); 
            adapter.writeLine(BKSP_N1_VEZANJE);
            adapter.readLine();
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
    
    /**
     * Odustajanje od prijave
     * @param e dogadjaj 
     */
    @FXML private void cancel(ActionEvent e){
        password.setText("");
        komunikator.skiniSaReda();
        komunikator.getMain().getPane().getChildren().clear();
        komunikator.getMain().getPane().getChildren().addAll(komunikator.dobijSaReda().getPane());
        System.gc();
    }
    
    /**
     * @return forma prijave za vanjske aplikacije 
     */
    @Override
    public Pane getPane() {
        return main;
    }
    
    /**
     * inicijalizacije 
     * @param url url resursa 
     * @param rb snop resursa 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       super.initialize(url, rb);
       komunikator.postaviNaRed(this);
    }    
}
