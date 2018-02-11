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
public final class KonstanteSertifikataServera {
    private KonstanteSertifikataServera(){}
    public final static String glavniKeyStore = "podaci_korisnika.jmks";
    public final static String serverPrivatniSertifikat = "server.privatni_sertifikat.jks";
    public final static String serverPristupniSertifikat = "server.pristupni_sertifikat.der";
    public final static String serverPotpisaniSertifikat = "server.pristupni_sertifikat.cer";
    public final static String serverPKCSMedjuSertifikat = "server.medjusertifikat.p12"; 
    public final static String serverPEMMedjuSertifikat = "server.medjusertifikat.pem";
    public final static String serverPrivatniKljuc = "server.privatni_kljuc.pem.key";
    public final static String serverIzvrsnaDatotekaWindows = "server.sertifikacija.bat";
    public final static String serverIzvrsnaDatotekaLinux = "server.sertifikacija.sh";
}
