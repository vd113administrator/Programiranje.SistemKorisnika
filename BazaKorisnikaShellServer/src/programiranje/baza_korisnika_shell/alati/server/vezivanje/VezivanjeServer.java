/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.alati.server.vezivanje;

import java.sql.SQLException;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;

/**
 * Klasa vezivanja servera koju treba naslijediti server koji ce se vezati 
 * @author Mikec
 */
public abstract class VezivanjeServer{
    /**
     * Metoda koja treba da sadrzi aktivnosti za rad servera 
     */
    public abstract void poziv();
    
    /**
     * Pozivanje osnovnog servera i kreiranje vezanog servera
     * @param c  klasa vezanog servera 
     * @param args argumenti iz maina vezanog servera
     * @throws Exception 
     */
    public static void start(Class<? extends VezivanjeServer> c,String ... args) throws Exception{
        VezivanjeControl.setVezaniServer(c.newInstance());
        ServerMainProgram.main(args);
    }
}
