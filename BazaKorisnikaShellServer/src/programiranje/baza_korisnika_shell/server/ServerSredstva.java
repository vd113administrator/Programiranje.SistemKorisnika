/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ucitavane datoteka resursa 
 * @author Mikec
 */
public final class ServerSredstva {
    private ServerSredstva(){}
    
    public final static String sqlNaredbe = "/programiranje/baza_korisnika_shell/baza_podataka/naredbe.properties";
    public final static Properties sqlNaredbeObj = new Properties(); 
    static { 
        try { sqlNaredbeObj.load(ServerSredstva.class.getResourceAsStream(sqlNaredbe));} 
        catch (IOException ex) {}
    }
}    
    
