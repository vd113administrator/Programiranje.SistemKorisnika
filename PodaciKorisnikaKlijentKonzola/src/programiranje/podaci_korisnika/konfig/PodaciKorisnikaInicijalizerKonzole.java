/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.konstante.KonstanteDatoteka;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 *
 * @author Mikec
 */
public class PodaciKorisnikaInicijalizerKonzole {
    public static void inicijalizacija(){
        try {
            inicijalizacijaDirektorijuma(); 
            inicijalizacijaDatoteka();
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaInicijalizerKonzole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void inicijalizacijaDirektorijuma(){
        PodaciKorisnikaAktivniDirektorijumi.setPorukeIzSlika(new File(KonstanteDirektorijuma.stegonografijaporuke));
        PodaciKorisnikaAktivniDirektorijumi.setPorukeUSlikama(new File(KonstanteDirektorijuma.stegonografijapodaci));
        PodaciKorisnikaAktivniDirektorijumi.setSlikeBezPoruka(new File(KonstanteDirektorijuma.stegonografijaslike));
        PodaciKorisnikaAktivniDirektorijumi.setStegonografija(new File(KonstanteDirektorijuma.stegonografija));
        PodaciKorisnikaAktivniDirektorijumi.setSertifikati(new File(KonstanteDirektorijuma.arhivacertifikata));
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDirektorijuma();
    }
    
    public static void inicijalizacijaDatoteka() throws IOException{
        InputStream s1 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.javaoriginalResurs);
        InputStream s2 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.openssloriginalResurs);
        InputStream s3 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.steghideoriginalResurs);
        InputStream s4 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.olympicsResurs);
        InputStream s5 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.footballchampionatResurs);
        InputStream s6 = PodaciKorisnikaAktivniDirektorijumi.class.getResourceAsStream(KonstanteDatoteka.footballeuroleagueResurs);
        int n1 = s1.available(); 
        int n2 = s2.available(); 
        int n3 = s3.available(); 
        int n4 = s4.available(); 
        int n5 = s5.available(); 
        int n6 = s6.available(); 
        byte[] c1 = new byte[n1];
        byte[] c2 = new byte[n2]; 
        byte[] c3 = new byte[n3];
        byte[] c4 = new byte[n4];
        byte[] c5 = new byte[n5]; 
        byte[] c6 = new byte[n6];
        s1.read(c1); 
        s2.read(c2); 
        s3.read(c3);
        s4.read(c4); 
        s5.read(c5); 
        s6.read(c6);
        s1.close();
        s2.close();
        s3.close();
        s4.close();
        s5.close();
        s6.close();
        File f1 = new File(KonstanteDatoteka.javaoriginalSlika);
        File f2 = new File(KonstanteDatoteka.openssloriginalSlika);
        File f3 = new File(KonstanteDatoteka.steghideoriginalSlika);
        File f4 = new File(KonstanteDatoteka.olympicsSlika);
        File f5 = new File(KonstanteDatoteka.footballchampionatSlika);
        File f6 = new File(KonstanteDatoteka.footballeuroleagueSlika);
        FileUnit u1 = new FileUnit(f1,c1);
        FileUnit u2 = new FileUnit(f2,c2);
        FileUnit u3 = new FileUnit(f3,c3);
        FileUnit u4 = new FileUnit(f4,c4);
        FileUnit u5 = new FileUnit(f5,c5);
        FileUnit u6 = new FileUnit(f6,c6);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaJava(u1);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaOpenssl(u2);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaSteghide(u3);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaSportZOI(u4);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaFudbalSP(u5);
        PodaciKorisnikaAktivniDirektorijumi.setSlikaFudbalEuroleague(u6);
        PodaciKorisnikaAktivniDirektorijumi.kreiranjeNepostojecihDatoteka();
    }
}
