/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pozdravna.aplikacija;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import programiranje.baza_korisnika_shell.klijent.vezanje.VezivanjeGlobalKomunikator;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;

/**
 *
 * @author Mikec
 */
public class PozdravnaAplikacijaController implements Initializable {
    private ClientDataAdapter serverAdapter; 
    
    @FXML
    private TextArea text; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
            serverAdapter = VezivanjeGlobalKomunikator.getAdapterZaServer(); 
            postaviInformacije();
        } catch (IOException ex){
            Logger.getLogger(PozdravnaAplikacijaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void postaviInformacije() throws IOException{
        List<String> lista = serverAdapter.ocitavanjePodataka();
        String txt = "";
        if(lista.size()!=1){
            String text = "";
            for(String s : lista){
                txt+=s+"\n";
            }
        }
        text.setText(txt);
    }
}
