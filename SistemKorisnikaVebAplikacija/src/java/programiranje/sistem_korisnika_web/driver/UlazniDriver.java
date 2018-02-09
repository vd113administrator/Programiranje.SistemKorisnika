/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

/**
 * Drajver dogadjaj ulaska na server
 * @author Mikec
 */
public class UlazniDriver extends Driver{
    /**
     * Aktivnosti 
     */
    public static enum Aktivnosti{
        ULAZNI_PROLAZ
    }
    /**
     * Konstruktor
     */
    public UlazniDriver(){
        add(Aktivnosti.ULAZNI_PROLAZ.toString());
    }
}
