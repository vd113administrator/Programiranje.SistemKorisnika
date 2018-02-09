/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.kontrola.server;

import programiranje.baza_korisnika_shell.model.Sesija;

/**
 * Kontrolne metode za odjavu 
 * @author Mikec
 */
public final class OdjavaControl {
    private OdjavaControl(){}
    /**
     * Metoda za izvrdenje odjave
     * @param s sesija
     */
    public static void odijava(Sesija s){
        if(s==null){
            System.out.println("Log out: - ");
            return;
        }
        PrijavaControl.zatvoriSesiju(s);
        System.out.println("Log out: session "+s);
        s.getServerDataAdapter().setSesija(null);
    }
}
