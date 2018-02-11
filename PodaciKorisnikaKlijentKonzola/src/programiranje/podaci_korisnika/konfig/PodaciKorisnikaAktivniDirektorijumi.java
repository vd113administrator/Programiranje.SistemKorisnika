/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konfig;

import java.io.File;
import java.io.IOException;
import programiranje.podaci_korisnika.ui.FileUnit;

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
    
    
    private static FileUnit slikaJava; 
    private static FileUnit slikaOpenssl; 
    private static FileUnit slikaSteghide; 
    private static FileUnit slikaSportZOI; 
    private static FileUnit slikaFudbalEuroleague;
    private static FileUnit slikaFudbalSP; 

    public static FileUnit getSlikaJava() {
        return slikaJava;
    }

    public static FileUnit getSlikaSportZOI() {
        return slikaSportZOI;
    }

    public static void setSlikaSportZOI(FileUnit slikaSportZOI) {
        PodaciKorisnikaAktivniDirektorijumi.slikaSportZOI = slikaSportZOI;
    }

    public static FileUnit getSlikaFudbalEuroleague() {
        return slikaFudbalEuroleague;
    }

    public static void setSlikaFudbalEuroleague(FileUnit slikaFudbalEuroleague) {
        PodaciKorisnikaAktivniDirektorijumi.slikaFudbalEuroleague = slikaFudbalEuroleague;
    }

    public static FileUnit getSlikaFudbalSP() {
        return slikaFudbalSP;
    }

    public static void setSlikaFudbalSP(FileUnit slikaFudbalSP) {
        PodaciKorisnikaAktivniDirektorijumi.slikaFudbalSP = slikaFudbalSP;
    }

    public static void setSlikaJava(FileUnit slikaJava) {
        PodaciKorisnikaAktivniDirektorijumi.slikaJava = slikaJava;
    }

    public static FileUnit getSlikaOpenssl() {
        return slikaOpenssl;
    }

    public static void setSlikaOpenssl(FileUnit slikaOpenssl) {
        PodaciKorisnikaAktivniDirektorijumi.slikaOpenssl = slikaOpenssl;
    }

    public static FileUnit getSlikaSteghide() {
        return slikaSteghide;
    }

    public static void setSlikaSteghide(FileUnit slikaSteghide) {
        PodaciKorisnikaAktivniDirektorijumi.slikaSteghide = slikaSteghide;
    }

    public static void kreiranjeNepostojecihDatoteka() throws IOException{ 
        slikaJava.setDirectory(slikeBezPoruka).saveIfnotExists(); 
        slikaOpenssl.setDirectory(slikeBezPoruka).saveIfnotExists(); 
        slikaSteghide.setDirectory(slikeBezPoruka).saveIfnotExists();
        slikaSportZOI.setDirectory(slikeBezPoruka).saveIfnotExists(); 
        slikaFudbalEuroleague.setDirectory(slikeBezPoruka).saveIfnotExists(); 
        slikaFudbalSP.setDirectory(slikeBezPoruka).saveIfnotExists();
    }     
    
}
