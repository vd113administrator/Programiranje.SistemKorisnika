/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.pogled.kontrola;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.klijent.ClientController;

/**
 * Kontroler prijavljenosti 
 * @author Mikec
 */
public class LoggedInController extends ClientController implements Initializable { 
    private OdijavaController odijavaC;
    
    @FXML private TextArea opis;
    @FXML private AnchorPane main;
    @FXML private Label sesija; 
    @FXML private Button odjava;
    @FXML private Button deregistracija;
    
    @FXML 
    private void odjava(ActionEvent e){
        try {
            odijavaC.odijava();
            komunikator.isprazniRed();
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
    
    @FXML 
    private void deregistracija(ActionEvent e) throws IOException{
        Pane p = FXMLLoader.load(getClass().getResource(resursi.getProperty("deregistracija")));
        komunikator.getMain().getPane().getChildren().clear();
        komunikator.getMain().getPane().getChildren().addAll(p);
    }
    
    @Override
    public Pane getPane() {
        return main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        odijavaC = new OdijavaController();
        odijavaC.initialize(null, null);
        komunikator.postaviNaRed(this);
    }    
    
    public void setSessionIdInfo(String session){
        sesija.setText("Sesija : "+session);
    }
    
    public void postaviInformacije() throws IOException{
        List<String> lista = serverAdapter.ocitavanjePodataka();
        if(lista.size()!=1){
            String text = "";
            for(String s : lista){
                text+=s+"\n";
            }
            opis.setText(text);
        }
    }
}
