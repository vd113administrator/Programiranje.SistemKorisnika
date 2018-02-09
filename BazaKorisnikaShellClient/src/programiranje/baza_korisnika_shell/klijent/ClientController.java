/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;

/**
 * Opsti kontroler graficke klijentske aplikacije 
 * @author Mikec
 */
public abstract class ClientController extends ClientObject{
    protected GlobalApplicationCommunicator komunikator; 
    protected ClientDataAdapter serverAdapter; 
    protected Properties resursi;
    
    /**
     * Dobijanje FX panel za datu metodu 
     * @return panel 
     */
    public abstract Pane getPane();
    
    /**
     * Inicijalizacioni koraci ovih kontrolera
     * @param url url 
     * @param rb resursi
     */
    public void initialize(URL url, ResourceBundle rb) {
        komunikator = ApplicationMainProgram.getGlobalApplicationCommunicator();
        serverAdapter = ApplicationMainProgram.getClientDataAdapter();
        resursi = ApplicationMainProgram.getGrafickiResursi();
    }
}
