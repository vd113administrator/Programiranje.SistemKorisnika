/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.konfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.server.konstante.KonstanteDatoteka;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 *
 * @author Mikec
 */
public final class PodaciKorisnikaInicijalizerServera {
    public static void inicijalizacija(){
        try {
            inicijalizacijaDirektorijuma();
            inicijalizacijaDatoteka();
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaInicijalizerServera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void inicijalizacijaDirektorijuma(){
        PodaciKorisnikaAktivniDirektorijumi.setPorukeUSlikama(new File(KonstanteDirektorijuma.stegonografijaakiv));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikati(new File(KonstanteDirektorijuma.arhivacertifikata));
        PodaciKorisnikaAktivniDirektorijumi.setPovuceniSertifikati(new File(KonstanteDirektorijuma.arhivapovucenikcertifikata));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikacija(new File(KonstanteDirektorijuma.certifikacija));
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDirektorijuma();
    }
    public static void inicijalizacijaDatoteka() throws IOException{
        InputStream s1 = PodaciKorisnikaInicijalizerServera.class.getResourceAsStream(KonstanteDatoteka.opensslconfResource);
        InputStream s2 = PodaciKorisnikaInicijalizerServera.class.getResourceAsStream(KonstanteDatoteka.sertifikacijaserveraShellResource);
        InputStream s3 = PodaciKorisnikaInicijalizerServera.class.getResourceAsStream(KonstanteDatoteka.sertifikacijaserveraBatchResource);
        int n1 = s1.available(); 
        int n2 = s2.available(); 
        int n3 = s3.available();
        byte [] c1 = new byte[n1]; 
        byte [] c2 = new byte[n2]; 
        byte [] c3 = new byte[n3];
        s1.read(c1); 
        s2.read(c2); 
        s3.read(c3); 
        s1.close();
        s2.close();
        s3.close(); 
        File f1 = new File(KonstanteDatoteka.opensslconfFile);
        File f2 = new File(KonstanteDatoteka.sertifikacijaserveraShellFile);
        File f3 = new File(KonstanteDatoteka.sertifikacijaserveraBatchFile);
        FileUnit u1 = new FileUnit(f1,c1);
        FileUnit u2 = new FileUnit(f2,c2);
        FileUnit u3 = new FileUnit(f3,c3);
        PodaciKorisnikaAktivniDirektorijumi.setConfOpenSSLservera(u1);
        PodaciKorisnikaAktivniDirektorijumi.setShellSertifikovanjeServera(u2);
        PodaciKorisnikaAktivniDirektorijumi.setBatchSertifikovanjeServera(u3);
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDatoteka();
    }
}
