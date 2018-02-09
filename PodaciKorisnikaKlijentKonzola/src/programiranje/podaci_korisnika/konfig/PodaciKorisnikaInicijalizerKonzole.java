/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konfig;

import java.io.File;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;

/**
 *
 * @author Mikec
 */
public class PodaciKorisnikaInicijalizerKonzole {
    public static void inicijalizacija(){
        inicijalizacijaDirektorijuma();
    }
    public static void inicijalizacijaDirektorijuma(){
        PodaciKorisnikaAktivniDirektorijumi.setPorukeIzSlika(new File(KonstanteDirektorijuma.stegonografijaporuke));
        PodaciKorisnikaAktivniDirektorijumi.setPorukeUSlikama(new File(KonstanteDirektorijuma.stegonografijapodaci));
        PodaciKorisnikaAktivniDirektorijumi.setSlikeBezPoruka(new File(KonstanteDirektorijuma.stegonografijaslike));
        PodaciKorisnikaAktivniDirektorijumi.setStegonografija(new File(KonstanteDirektorijuma.stegonografija));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikati(new File(KonstanteDirektorijuma.arhivacertifikata));
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDirektorijuma();
    }
}
