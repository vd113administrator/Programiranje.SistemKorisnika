/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.config.data;

import java.io.Serializable;

/**
 * Konfiguracija serverera
 * @author Mikec
 */
public class ShellServerConfiguration implements Serializable{
    private String databaseHost = "localhost"; 
    private String databaseUser = "root"; 
    private String databaseTable = "bk_programiranje_data"; 
    private String databasePassword = ""; 
    private int timedSessionExitPeriod = 600000;
    private String serverCertificate = "arch.sertifikacija.aktivno/server.pristupni_sertifikat.cer";
    private String serverPrivateKey = "arch.sertifikacija.aktivno/server.privatni_sertifikat.jks";
    /**
     * Osnovni konstruktor sa sifrom baze
     * @param password  sifra baze
     */
    public ShellServerConfiguration(String password){
        databasePassword = password;
    }
    
    /**
     * Konstruktor sa svim konfiguracionim podacima sem maksimalnim vremenom cekanja na veb sesiju
     * @param host host
     * @param user korisnik
     * @param table tabela
     * @param password sifra
     */
    public ShellServerConfiguration(String host, String user, String table, String password){
        databaseHost = host;
        databaseUser = user;
        databaseTable = table;
        databasePassword = password;
    }
    
    /**
     * Konstruktor sa svim konfiguracionim podacima
     * @param host host baze 
     * @param user korisnicko ime  
     * @param table tabela baze korisnika 
     * @param password sifra baze 
     * @param tsp maksimalno vrijeme cekanja
     */
    public ShellServerConfiguration(String host, String user, String table, String password, int tsp){
        databaseHost = host;
        databaseUser = user;
        databaseTable = table;
        databasePassword = password;
        timedSessionExitPeriod = tsp;
    }
    
    
    public ShellServerConfiguration(String host, String user, 
            String table, String password, int tsp,
            String certificate, String privateKey){
        databaseHost = host;
        databaseUser = user;
        databaseTable = table;
        databasePassword = password;
        timedSessionExitPeriod = tsp;
        serverCertificate = certificate;
        serverPrivateKey = privateKey;
    }
    
    /**
     * Dobijanje baze hosta
     * @return  host
     */
    public String getDatabaseHost(){
        return databaseHost; 
    }
    
    /**
     * Dobijanje korisnika baze
     * @return korisnicko ime za korisnika baze
     */
    public String getDatabaseUser(){
        return databaseUser;
    }
    
    /**
     * Tabela baze podataka 
     * @return ime tabele
     */
    public String getDatabaseTable(){
        return databaseTable; 
    }
    
    /**
     * Sifra baze podataka 
     * @return sifra
     */
    public String getDatabasePassword(){
        return databasePassword;
    }
    
    /**
     * Period cekanja na izlazak sesije
     * @return period
     */
    public int getTimedSessionExitPeriod(){
        return timedSessionExitPeriod;
    }
    
    public String getServerCertificate(){
        return serverCertificate; 
    }
    
    public String getServerPrivateKey(){
        return serverPrivateKey;
    }
}
