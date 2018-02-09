/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.sertifikacija;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import programiranje.podaci_korisnika.konfiguracija.KonstanteSertifikataKorisnika;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;

/**
 *
 * @author Mikec
 */
public class ImenovanjeSertifikata implements Serializable{
    private String imeDatotekeSertifikata; 
    private String user; 
    private TipSertifikata tip; 
    
    public static enum TipSertifikata{cer, der, pem, p12, key, csr, jks};
    private static enum KarakteristikeKljuceva{PK, NK, NPK, NPKS};
    
    public ImenovanjeSertifikata(String ime, String user, TipSertifikata tip){
        imeDatotekeSertifikata = ime; 
        this.user = user; 
        this.tip = tip; 
    }
    public ImenovanjeSertifikata(File file, String user, TipSertifikata tip){
        imeDatotekeSertifikata = file.getName(); 
        this.user = user; 
        this.tip = tip; 
    }
    
    public ImenovanjeSertifikata(String user, TipSertifikata tip){
        this.user = user; 
        this.tip = tip;  
    }
    
    
    
    public String getImeDatotekeSertifikata() {
        return imeDatotekeSertifikata;
    }

    public ImenovanjeSertifikata setImeDatotekeSertifikata(String ime) {
        imeDatotekeSertifikata = ime;
        return this; 
    }
    
    public ImenovanjeSertifikata setImeDatotekeSertifikata(File file) {
        imeDatotekeSertifikata = file.getName();
        return this;
    }
    
    
    public TipSertifikata getTipSertifikata(){
        return tip; 
    }
    
    public String getUser(){
        return user; 
    }
    
    public boolean vazeceImeSertifikata(){
        if(imeDatotekeSertifikata==null || imeDatotekeSertifikata.trim().length()==0) return false; 
        String[] parts = imeDatotekeSertifikata.split("-");

        switch(parts.length){ 
            case 3:
                if(!parts[0].equals(user)) return false;  
                try{FormatiranjeDatuma.uklapanjeDatuma(parts[1]);}
                catch(ParseException ex){return false;}
                switch(tip){
                    case cer:
                        if(!parts[2].equals(KarakteristikeKljuceva.NPKS)) return false;  
                    case der:
                        if(!parts[2].equals(KarakteristikeKljuceva.NPK)) return false; 
                    case pem: 
                        if(!parts[2].equals(KarakteristikeKljuceva.PK)) return false;
                    case p12:
                        if(!parts[2].equals(KarakteristikeKljuceva.PK)) return false;
                    case key:
                        if(!parts[2].equals(KarakteristikeKljuceva.PK)) return false;
                    case jks:
                        if(!parts[2].equals(KarakteristikeKljuceva.PK)) return false;
                    case csr:
                       if(!parts[2].equals(KarakteristikeKljuceva.NK)) return false;
                }
            case 4:
                switch(tip){
                    case cer:
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPotpisaniSertifikat)) return false;
                        break; 
                    case der:
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPristupniSertifikat)) return false; 
                        break;
                    case pem: 
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPEMMedjuSertifikat)) return false;
                        break;
                    case p12:
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPKCSMedjuSertifikat)) return false;
                        break;
                    case key:
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPrivatniKljuc)) return false;
                        break;
                    case jks:
                        if(!parts[3].equals(KonstanteSertifikataKorisnika.korisnikPrivatniSertifikat)) return false;
                        break;
                    case csr:
                        if(!parts[3].equals(KarakteristikeKljuceva.NK)) return false;
                        break;
                }
                return true;
            default:
                return false; 
        }
    }
}
