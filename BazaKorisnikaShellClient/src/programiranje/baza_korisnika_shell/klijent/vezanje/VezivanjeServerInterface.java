/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent.vezanje;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import programiranje.baza_korisnika_shell.komunikacije.ClientDataAdapter;

/**
 * Interfejs koji veze serverska aplikacija
 * @author Mikec
 */
public interface VezivanjeServerInterface extends VezivanjeInterface{
    /**
     * Aktivnost vezanog servera 
     */
    public void run();
    /**
     * Poziv vezanih servera 
     * @param args argumenti 
     * @throws IOException 
     */
    public default void poziv(String ... args) throws IOException{
        try {
            VezivanjeGlobalKomunikator.setVezivanjeInterface(this);
            ApplicationMainProgram.setAplikacijskiMod(AplikacijskiMod.ULAZ_VANJSKE_APLIKACIJE_VEZANOG_SERVERA);
            ApplicationMainProgram.main(args);
        } catch (CertificateException ex) {
            Logger.getLogger(VezivanjeServerInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
