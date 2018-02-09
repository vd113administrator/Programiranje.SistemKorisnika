/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.config.data;

import java.io.Serializable;

/**
 * Klasa za konfiguracione podatke
 * @author Mikec
 */
public class ShellClientConfiguration implements Serializable{
    private String serverAddress = "127.0.0.1"; 
    private String serverCertificate = "arch.sertifikacija.aktivno/server.pristupni_sertifikat.cer";
    /**
     * Konstruktor sa podrazumijevanim podacima
     */
    public ShellClientConfiguration(){
    }
    /**
     * Konstruktor sa adresom servera
     * @param serverAddress adresa servera
     */
    public ShellClientConfiguration(String serverAddress, String serverCertificate){
        this.serverAddress = serverAddress; 
        this.serverCertificate = serverCertificate; 
    }
    /**
     * Dobijanje serverske adrese
     * @return serverska adresa
     */
    public String getServerAddress(){
        return serverAddress;
    }
    
    public String getServerCertificate(){
        return serverCertificate;
    }
}
