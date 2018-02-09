/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

/**
 * Drajevri dogadjaja za promjene opisa korisnika, uloga i grupa
 * @author Mikec
 */
public class OpisivanjeDriver extends Driver{
    /**
     * Aktivnosti kod opisivanja
     */
    public enum Aktivnost{
        IZMJENA_OPISA,
        ODUSTAJANJE_OD_OPISA
    }
    /**
     * Konstruktor
     */
    public OpisivanjeDriver(){
        add(Aktivnost.IZMJENA_OPISA.toString());
        add(Aktivnost.ODUSTAJANJE_OD_OPISA.toString());
    }
}
