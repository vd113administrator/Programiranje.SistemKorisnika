/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konfiguracija;

/**
 *
 * @author Mikec
 */
public final class KonstanteSertifikataKorisnika {
    private KonstanteSertifikataKorisnika(){}
    public final static String korisnikPrivatniSertifikat = "privatni_sertifikat.jks";
    public final static String korisnikPristupniSertifikat = "pristupni_sertifikat.der";
    public final static String korisnikPotpisaniSertifikat = "pristupni_sertifikat.cer";
    public final static String korisnikPKCSMedjuSertifikat = "medjusertifikat.p12"; 
    public final static String korisnikPEMMedjuSertifikat = "medjusertifikat.pem";
    public final static String korisnikPrivatniKljuc = "privatni_kljuc.pem.key";
}
