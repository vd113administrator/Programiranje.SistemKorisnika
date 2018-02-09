/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server.izlaz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class <br>
 * Kontrola dogadjaja forme za izlaz
 * @author Mikec
 */
public class IzlazController implements Initializable {
    @FXML Button exit; 
    
    /**
     * Aktivnost izlaza, odnosno pritiska na dugme
     * @param ae parametri desavanja 
     */
    @FXML 
    private void exit(ActionEvent ae){
        Platform.exit();
    }
    
    /**
     * Iniicijalizacione aktivnosti 
     * @param url lokacija resursa 
     * @param rb snop resursa 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }    
}
