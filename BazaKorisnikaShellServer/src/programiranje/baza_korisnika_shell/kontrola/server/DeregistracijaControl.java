/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.kontrola.server;

import programiranje.baza_korisnika_shell.model.Sesija;

/**
 * Grupa metoda za deregistraciju 
 * @author Mikec
 */
public final class DeregistracijaControl {
    private DeregistracijaControl(){}
    
    /**
     * Kontrolna metoda kojom se izvrsava deregistracija na poznate podatke 
     * @param s sesija
     * @param uname korisnicko ime
     * @param pass sifra 
     * @return rezultat
     */
    public static boolean deregistruj(Sesija s, String uname, String pass){
        try{
            s.getKorisnik().getAutentifikacija().odgovara(uname,pass);
        }catch(Exception e){
            System.out.println("Brisnaje korisnika : "+s.getKorisnik()+" neuspesno");
            return false; 
        }
        OdjavaControl.odijava(s);
        RegistracijaControl.obrsisiKorisnika(s.getKorisnik().getAutentifikacija());
        System.out.println("Brisnaje korisnika : "+s.getKorisnik());
        return true;
    }
}
