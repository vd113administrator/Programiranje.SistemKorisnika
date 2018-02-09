/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_config.server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import programiranje.baza_korisnika_shell.config.data.ShellClientConfiguration;
import programiranje.baza_korisnika_shell.config.data.ShellServerConfiguration;

public class Kontrola implements Initializable {
    @FXML TextField databaseHost;
    @FXML TextField databaseUser; 
    @FXML TextField databaseTable;
    @FXML PasswordField databasePassword; 
    @FXML TextField timeExitTimedSession;
    @FXML TextField certificateFilename;
    @FXML TextField privatekeyFilename;
    
    @FXML Button save; 
    
    @FXML 
    private void save(ActionEvent e) throws IOException{
        int time = 600000;
        try{ time = Integer.parseInt(timeExitTimedSession.getText());} catch(Exception ex){} 
        ShellServerConfiguration konfiguracija = new ShellServerConfiguration(
            databaseHost.getText(),
            databaseUser.getText(),
            databaseTable.getText(),
            databasePassword.getText(),
            time,certificateFilename.getText(), 
                 privatekeyFilename.getText()
        );
        Aplikacija.setKonfiguracija(konfiguracija);
        Aplikacija.write();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHost.setText(Aplikacija.getKonfiguracija().getDatabaseHost());
        databaseUser.setText(Aplikacija.getKonfiguracija().getDatabaseUser());
        databaseTable.setText(Aplikacija.getKonfiguracija().getDatabaseTable());
        databasePassword.setText(Aplikacija.getKonfiguracija().getDatabasePassword());
        timeExitTimedSession.setText(Integer.toString(Aplikacija.getKonfiguracija().getTimedSessionExitPeriod()));
        certificateFilename.setText(Aplikacija.getKonfiguracija().getServerCertificate());
        privatekeyFilename.setText(Aplikacija.getKonfiguracija().getServerPrivateKey());
    }     
}
