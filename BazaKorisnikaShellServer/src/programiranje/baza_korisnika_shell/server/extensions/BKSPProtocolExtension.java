/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server.extensions;

import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;

/**
 * Prosirenja preko aplikacije
 * @author Mikec
 */
public interface BKSPProtocolExtension{
    /**
     * Aktivnosti interne aplikacije 
     * @param BKSPExArg naredba prosirenog BKSP 
     * @param sa komunikacija sa klijentom 
     * @param dbadapter komunikacija sa bazom podataka
     */
    public abstract void run(String BKSPExArg, ServerDataAdapter sa, UserDatabaseAdapter dbadapter);
}
