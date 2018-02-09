/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

/**
 * Staticki podaci servera 
 * @author Mikec
 */
public final class ServerStatics {
    private ServerStatics(){} 
    public static final int serverPort = 9000;
    public static final int serverControlPort = 9001; 
    public static final String databaseName = "baza_korisnika";
    public static final String databaseTable = "bk_programiranje_data";
    public static final String adefaultDatabaseHost = "localhost";
    public static final String defaultDatabaseUser = "root";
    public static final String defaultDatabasePassword = "vidmers";
}
