/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.ekstenzije;

/**
 * Klasa za prosirivanje aplikacije
 * @author Mikec
 */

public interface BKSPEkstenzije{
    /**
     * Ispis menija izbor opcijes
     * @return kod izbora
     */
    public int meni(); 
    /**
     * Aktivnosti aplikacije
     * @param izbor kod izbora 
     * @return status izvrsavanja 
     */
    public boolean run(int izbor);
}
