/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.global;

/**
 * Protokol BKSP
 * @author Mikec
 */
public final class ProtokolBKSP {
    private ProtokolBKSP(){}
    
    public static final String BKSP_SUCCESS = "USPJESNO";
    public static final String BKSP_ERROR = "GRESKA"; 
    
    public static final String BKSP_REGISTRACIJA = "REGISTRACIJA";
    public static final String BKSP_DEREGISTRACIJA = "DEREGISTRACIJA";
    public static final String BKSP_PRIJAVA = "PRIJAVA";
    public static final String BKSP_ODIJAVA = "ODJAVA";
    public static final String BKSP_CLOSE = "IZLAZ";
    public static final String BKSP_PODACI = "PODACI";
    
    public static final String PBKSP_CLOSE = "IZLAZ"; 
    public static final String PBKSP_STOP = "ZAUSTAVLJANJE";
    
    public static final String BKSP_N1_VEZANJE = "VEZANJE";
    
    public static final String BKSP_N2_AKTIVNOST_PROMENA_TIPA_KORISNIKA  = "BKSP_N2_AKTIVNOST_PROMENA_TIPA_KORISNIKA"; 
    public static final String BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA = "BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA";
    public static final String BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA = "BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA"; 
}
