/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.server_grupa;

import java.sql.SQLException;
import programiranje.baza_korisnika_shell.config.data.ShellServerConfiguration;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;
import programiranje.baza_korisnika_shell.server.extensions.BKSPProtocolExtension;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGP;
import programiranje.sistem_korisnika.server_grupa.ulaz_izlaz.SistemKorisnikaServerKontroler;

/**
 * Prva tacka servera grupa sistema korisnika
 * @author Mikec
 */
public class SistemKorisnikaServerGrupa implements BKSPProtocolExtension{ 
    /**
     * Prva metoda 
     * @param args argumenti
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ServerMainProgram.getServerKomunikator().setKonfiguracija(new ShellServerConfiguration("vidmers"));
        SistemKorisnikaServerGrupa server = new SistemKorisnikaServerGrupa();
        ServerMainProgram.setBKSPExtension(server);
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
        SistemKorisnikaServerKontroler server = new SistemKorisnikaServerKontroler(adapter,dbadapter);
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_PRAVLJENJE_GRUPE.toString()))
           server.pravljenjeGrupe();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_BRISANJE_GRUPE.toString()))
           server.brisanjeGrupe();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_KORISNIK_AKTIVNOST_UCLANJENJE.toString()))
           server.uclanjenje();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_CLAN_AKTIVNOST_ISCLANJENJE.toString()))
           server.isclanjenje();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_ISKLJUCIVANJE.toString()))
           server.iskljucenje();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_PRIPADNOSTI.toString()))
           server.listaPripadnosti();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_VLASNISTVA.toString()))
           server.listaVlasnistva();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_KORISNIKA_GRUPE.toString()))
           server.listaKorisnika();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_KORISNIKA_GRUPE.toString()))
           server.podaciKorisnika();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_GRUPE.toString()))
           server.podaciGrupe();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_CLANSTVA_U_GRUPI.toString()))
           server.provjeraClanstva();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_1_CLAN_IMENA_GRUPA.toString()))
           server.listaGrupa();
        if(BKSPExArg.equals(ProtokolSKGP.SKGP_N1_ADMIN_AKTIVNOST_PREIMENOVANJE_GRUPE.toString()))
           server.preimenovanjeGrupe();
    }    
}
