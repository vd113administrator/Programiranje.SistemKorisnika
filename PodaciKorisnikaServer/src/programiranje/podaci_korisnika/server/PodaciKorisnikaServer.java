/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server;

import java.sql.SQLException;
import programiranje.baza_korisnika_shell.config.data.ShellServerConfiguration;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;
import programiranje.baza_korisnika_shell.server.extensions.BKSPProtocolExtension;
import programiranje.podaci_korisnika.server.konfig.PodaciKorisnikaInicijalizerServera;
import programiranje.podaci_korisnika.server.ui.PodaciKorisnikaServerKontroler;
import programiranje.podaci_korisnika.standard.NaredbeProtokolaPROPIS;

/**
 * Prva tacka servera grupa sistema korisnika
 * @author Mikec
 */
public class PodaciKorisnikaServer implements BKSPProtocolExtension{ 
    /**
     * Prva metoda 
     * @param args argumenti
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ServerMainProgram.getServerKomunikator().setKonfiguracija(new ShellServerConfiguration("vidmers"));
        PodaciKorisnikaServer server = new PodaciKorisnikaServer();
        ServerMainProgram.setBKSPExtension(server);
        PodaciKorisnikaInicijalizerServera.inicijalizacija();
        ServerMainProgram.main(args);
    }

    /**
     * Aktivnosti i prosirenje protokola BKSP kojima se prosiruje baza_korisnika
     * @param BKSPExArg komanda prosirenog protokola
     * @param adapter komunikacija s klijentima
     * @param dbadapter adapter baze
     */
    @Override
    public void run(String BKSPExArg, ServerDataAdapter adapter, UserDatabaseAdapter dbadapter){
        PodaciKorisnikaServerKontroler server = new PodaciKorisnikaServerKontroler(adapter,dbadapter);
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA.toString()))
           server.listaKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_PODACIMA.toString().toString()))
           server.podaciKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_SERTIFIKATOM.toString()))
           server.zahtijevZaVlastitimSertifikatom();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_PODACIMA.toString()))
           server.podaciDrugogKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_SERTIFIKATOM.toString()))
           server.zahtijevZaSertifikatomKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_INFORMACIJA_O_BROJU_SERTIFIKATA.toString()))
           server.informacijaOBrojuSertifikata();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA_SERTIFIKOVANIH_KORISNIKA.toString()))
           server.listanjeSertifikovanihKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_SERTIFIKATA_KORISNIKA.toString()))
           server.listanjeImenaSetifikata();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_POSTAVLJANJE_PORUKE_U_SLICI.toString()))
           server.postavljanjePorukeUSlici();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA.toString()))
           server.listanjePorukaUSlikama();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA_ZA_KORISNIKA.toString()))
           server.listanjePorukaUSlikamaZaKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_PREUZIMANJE_SVIH_SLIKA_S_PORUKAMA_ZA_KORISNIKA.toString()))
           server.preizimanjeSvihSlikaSPorukamaZaKorisnika();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_PORUKOM_U_SLICI.toString()))
           server.preuzimanjeCiljaneSlikeSPorukom();
        if(BKSPExArg.equals(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_SPISKOM_ISPORUKA.toString()))
           server.zahtijevZaMapomIsporuka();
    }
}
