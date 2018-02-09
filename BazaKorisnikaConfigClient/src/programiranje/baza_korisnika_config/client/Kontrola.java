/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_config.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import programiranje.baza_korisnika_shell.config.data.ShellClientConfiguration;

public class Kontrola implements Initializable {
    @FXML TextField serverAddress;
    @FXML TextField certificateFilename;
    @FXML Button save; 
    
    @FXML 
    private void save(ActionEvent e) throws IOException{
        Aplikacija.setKonfiguracija(
                new ShellClientConfiguration(serverAddress.getText(),certificateFilename.getText()));
        Aplikacija.write();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverAddress.setText(Aplikacija.getKonfiguracija().getServerAddress());
        certificateFilename.setText(Aplikacija.getKonfiguracija().getServerCertificate());
        serverAddress.end();
    }     
}
