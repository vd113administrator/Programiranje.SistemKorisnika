/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.pogled.kontrola;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ClientController;

/**
 * Kontroler odjave 
 * @author Mikec
 */
public class OdijavaController extends ClientController implements Initializable {
    @FXML private AnchorPane main;
    
    @Override
    public Pane getPane() {
        return main;
    }
    
    public void odijava(){
        serverAdapter.odijava();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }
}
