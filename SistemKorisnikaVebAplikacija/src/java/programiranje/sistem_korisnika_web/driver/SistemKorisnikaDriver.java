/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.driver;

import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGP;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati;

/**
 * Drajevri dogadjaja za sistem korisnika 
 * @author Mikec
 */
public class SistemKorisnikaDriver extends Driver{
    /**
     * Konstruktori 
     */
    public SistemKorisnikaDriver(){
        add(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_PRAVLJENJE_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_BRISANJE_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_KORISNIK_AKTIVNOST_UCLANJENJE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_AKTIVNOST_ISCLANJENJE.toString());
        add(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_CLANSTVA_U_GRUPI.toString());
        add(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_VLASNISTVA.toString());
        add(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_PRIPADNOSTI.toString()); 
        add(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_ISKLJUCIVANJE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_KORISNIKA_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_KORISNIKA_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_IMENA_GRUPA.toString()); 
        add(ProtokolSKGP.SKGP_N1_ADMIN_AKTIVNOST_PREIMENOVANJE_GRUPE.toString());
        add(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_GRUPE.toString()); 
    }
}
