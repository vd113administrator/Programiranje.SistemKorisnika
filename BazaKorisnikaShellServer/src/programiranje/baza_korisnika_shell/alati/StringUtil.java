/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.alati;

/**
 * Staticke metode za rad sa stringovima
 * @author Mikec
 */
public final class StringUtil {
    private StringUtil(){}
    
    /**
     * Izbacivanje apostrofa iz stringa 
     * @param str string 
     * @return preuredjeni string
     */
    public static String sqlEscape(String str){
        return str.replace("\'", "");
    }
}
