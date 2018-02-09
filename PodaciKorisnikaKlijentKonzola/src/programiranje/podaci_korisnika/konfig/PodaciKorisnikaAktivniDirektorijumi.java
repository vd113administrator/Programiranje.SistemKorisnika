/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konfig;

import java.io.File;

/**
 *
 * @author Mikec
 */
public final class PodaciKorisnikaAktivniDirektorijumi {
    private PodaciKorisnikaAktivniDirektorijumi(){}
    private static File sertifikati;
    private static File porukeUSlikama; 
    private static File porukeIzSlika; 
    private static File slikeBezPoruka; 
    private static File stegonografija; 
    
    public static File getSertifikati() {
        return sertifikati;
    }

    public static void setSertifikati(File sertifikati) {
        PodaciKorisnikaAktivniDirektorijumi.sertifikati = sertifikati;
    }

    public static File getPorukeUSlikama() {
        return porukeUSlikama;
    }

    public static void setPorukeUSlikama(File porukeUSlikama) {
        PodaciKorisnikaAktivniDirektorijumi.porukeUSlikama = porukeUSlikama;
    }

    public static File getPorukeIzSlika() {
        return porukeIzSlika;
    }

    public static void setPorukeIzSlika(File porukeIzSlika) {
        PodaciKorisnikaAktivniDirektorijumi.porukeIzSlika = porukeIzSlika;
    }

    public static File getSlikeBezPoruka() {
        return slikeBezPoruka;
    }

    public static void setSlikeBezPoruka(File slikeBezPoruka) {
        PodaciKorisnikaAktivniDirektorijumi.slikeBezPoruka = slikeBezPoruka;
    }
   
    public static File getStegonografija() {
        return stegonografija;
    }

    public static void setStegonografija(File stegonografija) {
        PodaciKorisnikaAktivniDirektorijumi.stegonografija = stegonografija;
    }
    
   
    
    public static void kreiranjeNepostojecihDirektorijuma(){
        if(!porukeIzSlika.exists()) porukeIzSlika.mkdir(); 
        if(!porukeUSlikama.exists()) porukeUSlikama.mkdir();
        if(!slikeBezPoruka.exists()) slikeBezPoruka.mkdir();
        if(!stegonografija.exists()) stegonografija.mkdir();
        if(!sertifikati.exists()) sertifikati.mkdir();
    }
}
