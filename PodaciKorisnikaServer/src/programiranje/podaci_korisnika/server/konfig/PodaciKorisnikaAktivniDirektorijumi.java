/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.konfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import programiranje.podaci_korisnika.ui.FileUnit;

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

    public static FileUnit getShellSertifikovanjeServera() {
        return shellSertifikovanjeServera;
    }

    public static void setShellSertifikovanjeServera(FileUnit shellSertifikovanjeServera) {
        PodaciKorisnikaAktivniDirektorijumi.shellSertifikovanjeServera = shellSertifikovanjeServera;
    }

    public static FileUnit getBatchSertifikovanjeServera() {
        return batchSertifikovanjeServera;
    }

    public static void setBatchSertifikovanjeServera(FileUnit batchSertifikovanjeServera) {
        PodaciKorisnikaAktivniDirektorijumi.batchSertifikovanjeServera = batchSertifikovanjeServera;
    }

    public static FileUnit getConfOpenSSLservera() {
        return confOpenSSLservera;
    }

    public static void setConfOpenSSLservera(FileUnit confOpenSSLservera) {
        PodaciKorisnikaAktivniDirektorijumi.confOpenSSLservera = confOpenSSLservera;
    }
    
    private static FileUnit shellSertifikovanjeServera;
    private static FileUnit batchSertifikovanjeServera; 
    private static FileUnit confOpenSSLservera; 
    private static FileUnit crlnumberDatotekaServera;
    private static FileUnit serialDatotekaServer; 
    private static FileUnit indexTextCRLDatabaseServera; 
    
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
    
    public static void kreiranjeNepostojecihDatoteka() throws IOException{
            File file1 = new File("crlnumber");
            File file2 = new File("serial");
            File file3 = new File("index.txt");
            
            ByteArrayOutputStream crlnum = new ByteArrayOutputStream(); 
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(crlnum,"US-ASCII"));
            pw.print(1000);
            pw.close();
            
            crlnumberDatotekaServera = new FileUnit(file1).setContent(crlnum.toByteArray());
            serialDatotekaServer = new FileUnit(file2).setContent(new byte[0]); 
            indexTextCRLDatabaseServera = new FileUnit(file3).setContent(new byte[0]); 
            
            shellSertifikovanjeServera.setDirectory(sertifikati).saveIfnotExists();
            batchSertifikovanjeServera.setDirectory(sertifikati).saveIfnotExists(); 
            confOpenSSLservera.setDirectory(sertifikacija).saveIfnotExists();
            
            
            crlnumberDatotekaServera.setDirectory(povuceniSertifikati).saveIfnotExists();
            serialDatotekaServer.setDirectory(povuceniSertifikati).saveIfnotExists(); 
            indexTextCRLDatabaseServera.setDirectory(povuceniSertifikati).saveIfnotExists();
    }
}
