/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.konfig;

import java.io.File;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;

/**
 *
 * @author Mikec
 */
public final class PodaciKorisnikaInicijalizerServera {
    public static void inicijalizacija(){
        inicijalizacijaDirektorijuma();
    }
    public static void inicijalizacijaDirektorijuma(){
        PodaciKorisnikaAktivniDirektorijumi.setPorukeUSlikama(new File(KonstanteDirektorijuma.stegonografijaakiv));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikati(new File(KonstanteDirektorijuma.arhivacertifikata));
        PodaciKorisnikaAktivniDirektorijumi.setPovuceniSertifikati(new File(KonstanteDirektorijuma.arhivapovucenikcertifikata));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikacija(new File(KonstanteDirektorijuma.certifikacija));
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDirektorijuma();
    }
}
