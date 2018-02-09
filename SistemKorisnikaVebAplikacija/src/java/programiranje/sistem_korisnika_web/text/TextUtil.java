/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.text;

/**
 * Alati za tekst 
 * @author Mikec
 */
public class TextUtil {
    private TextUtil(){}
    /**
     * Uklanjanje ALG kombinacija
     * @param s string
     * @return promjenjeni string 
     */
    public static String removeALG(String s){
        if(s==null) s = "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"); 
    }
    
    /**
     * Dekodovanje ALG kombinacija
     * @param s string
     * @return promjenjeni string 
     */
    public static String reverseALG(String s){
        if(s==null) s = "";
        return s.replace("&gt;",">").replace("&lt;","<").replace("&amp;","&");
    }
    
    /**
     * Kodovanje KCN kombinacija
     * @param s string
     * @return promjenjeni string 
     */
    public static String removeKCN(String s){
        if(s==null) s = "";
        return s.replace("\\", "&Backslash;").replace("\"","&quot;").replace("\'","&apos;"); 
    }
    
    /**
     * Dekodovanje KCN kombinacija
     * @param s string 
     * @return promjenjeni string
     */
    public static String reverseKCN(String s){
        if(s==null) s = ""; 
        return s.replace("&apos;","\'").replace("&quot;","\"").replace("&Backslash;","\\"); 
    }
    
    /**
     * Kombinacija ALG i KCN - kodovanje
     * @param s string
     * @return promijenjenji string 
     */
    public static String removeSpecial(String s){
        return removeKCN(removeALG(s));
    }
    
    /**
     * Kombinacija provjera ALG i KCN - dekodovanje 
     * @param s string
     * @return promijenjeni string
     */
    public static String reverseSpecial(String s){
        return reverseKCN(reverseALG(s));
    }
    
    /**
     * Kombinacija provjera ALG i KCN - kodovanje
     * @param s string
     * @param n broj ponavljanja 
     * @return promijenjeni string
     */
    public static String removeALG(String s, int n){
        if(n<=0) return s; 
        return removeALG(removeALG(s),--n);
    }
    
    /**
     * Kombinacija provjera ALG i KCN - dekodovanje
     * @param s string 
     * @param n broj
     * @return novi string
     */
    public static String reverseALG(String s, int n){
        if(n<=0) return s; 
        return reverseALG(reverseALG(s),--n);
    }
    
    /**
     * Kodovanje KCN metodom
     * @param s ulaz
     * @param n broj
     * @return rezultat
     */
    public static String removeKCN(String s, int n){
        if(n<=0) return s; 
        return removeKCN(removeKCN(s),--n); 
    }
    
    /**
     * Dekodovanje KCN metodom 
     * @param s ulaz
     * @param n broj
     * @return rezultat
     */
    public static String reverseKCN(String s, int n){
        if(n<=0) return s; 
        return reverseKCN(reverseKCN(s),--n); 
    }
    
    /**
     * Provjera slicnosti KCN 
     * @param s string
     * @return promjenjen string
     */
    public static String slicanKCN(String s){
        if(s==null) s = "";
        return s.replace("\\", "&UpperLeftArrow;").replace("\"","&CloseCurlyDoubleQuote;").replace("\'","&CloseCurlyQuote;");
    }
    
    /**
     * Priprema za HTML 
     * @param s string 
     * @return pripremljen string
     */
    public static String pripremiZaHTML(String s){
        s=removeALG(s);
        s=slicanKCN(s);
        return s;
    }
}
