/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent.vezanje.kontroler;

import programiranje.baza_korisnika_shell.pogled.kontrola.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.klijent.ClientController;

/**
 * Kontroler za vanjske vezane aplikacije 
 * @author Mikec
 */
public class OdijavaSaVanjskeAplikacijeController extends ClientController{   
    /**
     * Odjava izvana 
     */
    public void odijava(){
        serverAdapter.odijava();
    }

    /**
     * @return forma nije podrzana ovim kontrolerom
     */
    @Override
    public Pane getPane() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
