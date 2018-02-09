/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import programiranje.baza_korisnika_shell.config.data.ShellClientConfiguration;
import programiranje.baza_korisnika_shell.constants.StatikaKonfiguracije;

/**
 * Konfiguracioni i staticki podaci i metode za upravljanje na nivou aplikacije
 * @author Mikec
 */
public final class StatickiPodaci {
    private StatickiPodaci(){}
    public final static int serverPort = 9000;
    public final static int serverControlPort = 9001; 
    private static InetAddress serverAddress;
    private static String certificatePath = "arch.sertifikacija.aktivno/server.pristupni_sertifikat.cer"; 
    private final static String clientConfigFile = "client.config.srz";
    static {
        ShellClientConfiguration konfiguracija = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(StatikaKonfiguracije.clientConfigFile)));
            konfiguracija = (ShellClientConfiguration) ois.readObject();
            if(konfiguracija==null) konfiguracija = new ShellClientConfiguration();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                ois.close();
            } catch (Exception ex) {    
            }
        }
        if(konfiguracija == null){
            try { serverAddress = InetAddress.getByName("127.0.0.1"); }
            catch (UnknownHostException ex) {}
        }
        else{
            try { 
                serverAddress = InetAddress.getByName(konfiguracija.getServerAddress()); 
                certificatePath = konfiguracija.getServerCertificate();
            }
            catch (UnknownHostException ex) {}
        }
    }
    public static InetAddress getServerAddress(){
        return serverAddress; 
    }
    public static void setServerAddress(InetAddress adresa){
        serverAddress = adresa; 
    }
    public static String getCertificatePath(){
        return certificatePath;
    }
}
