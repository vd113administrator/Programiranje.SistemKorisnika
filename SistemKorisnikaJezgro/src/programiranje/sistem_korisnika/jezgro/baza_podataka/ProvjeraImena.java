/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.baza_podataka;

/**
 * Ogranicenja sadrzaja podataka kod nekih podataka sistema korisnika
 * @author Mikec
 */
public final class ProvjeraImena {
    private ProvjeraImena(){
    }
    
    /**
     * Provjera ogranicenja za jedan string 
     * @param s string
     * @return rezultat
     */
    public static boolean provjeraVSMSBDCM(String s){
        if(s==null) return true;
        for(char c : s.toCharArray()){
            if(Character.isDigit(c)) continue;
            if(Character.isUpperCase(c)) continue; 
            if(Character.isLowerCase(c)) continue;
            if(c=='-') continue;
            if(c=='_') continue;
            if(c==' ') continue;
            if(c=='.') continue; 
            if(c==',') continue; 
            if(c==':') continue; 
            if(c==';') continue; 
            if(c=='`') continue;
            if(c=='@') continue;
            if(c=='#') continue;
            if(c=='\n') continue; 
            return false; 
        }
        return true; 
    }
    
    /**
     * Provjera na nizu stringova 
     * @param ss niz stringova
     * @return rezultat provjere 
     */
    public static boolean provjeraVSMSBDCM(String ... ss){
        for(String s:ss)
            if(!provjeraVSMSBDCM(s)) return false; 
        return true; 
    }
}
