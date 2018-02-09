/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.konfig;

import java.io.File;

/**
 *
 * @author Mikec
 */
public final class PodaciKorisnikaAktivniDirektorijumi {
    private PodaciKorisnikaAktivniDirektorijumi(){}
    private static File sertifikati;
    private static File sertifikacija; 
    private static File povuceniSertifikati; 
    private static File porukeUSlikama; 

    public static File getSertifikati() {
        return sertifikati;
    }

    public static void setSertifikati(File sertifikati) {
        PodaciKorisnikaAktivniDirektorijumi.sertifikati = sertifikati;
    }

    public static File getSertifikacija() {
        return sertifikacija;
    }

    public static void setSertifikacija(File sertifikacija) {
        PodaciKorisnikaAktivniDirektorijumi.sertifikacija = sertifikacija;
    }

    public static File getPovuceniSertifikati() {
        return povuceniSertifikati;
    }

    public static void setPovuceniSertifikati(File povuceniSertifikati) {
        PodaciKorisnikaAktivniDirektorijumi.povuceniSertifikati = povuceniSertifikati;
    }

    public static File getPorukeUSlikama() {
        return porukeUSlikama;
    }

    public static void setPorukeUSlikama(File porukeUSlikama) {
        PodaciKorisnikaAktivniDirektorijumi.porukeUSlikama = porukeUSlikama;
    }
    
    public static void kreiranjeNepostojecihDirektorijuma(){
        if(!sertifikacija.exists()) sertifikacija.mkdir(); 
        if(!porukeUSlikama.exists()) porukeUSlikama.mkdir();
        if(!sertifikati.exists()) sertifikati.mkdir();
        if(!povuceniSertifikati.exists()) povuceniSertifikati.mkdir();
    }
}
