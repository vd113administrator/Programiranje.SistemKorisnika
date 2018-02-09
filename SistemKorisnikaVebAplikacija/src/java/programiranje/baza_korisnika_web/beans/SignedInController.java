/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans;

/**
 * Kontroler aktivnosti na stranici posle prijavljenosti 
 * @author Mikec
 */
public class SignedInController {
    /**
     * Aktivnost preimenovanja 
     * @return JSF povrat
     */
    public String renamePage(){
        return "renamePage"; 
    }
}
