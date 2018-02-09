/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.standard;

/**
 * Protokol SKGP - naredbe
 * @author Mikec
 */
public enum ProtokolSKGP {
    SKGP_1_ADMIN_AKTIVNOST_PRAVLJENJE_GRUPE,
    SKGP_1_ADMIN_AKTIVNOST_BRISANJE_GRUPE,
    SKGP_1_KORISNIK_AKTIVNOST_UCLANJENJE,
    SKGP_1_CLAN_AKTIVNOST_ISCLANJENJE,
    SKGP_1_KORISNIK_PROVJERA_CLANSTVA_U_GRUPI,
    SKGP_1_KORISNIK_PROVJERA_LISTA_VLASNISTVA,
    SKGP_1_KORISNIK_PROVJERA_LISTA_PRIPADNOSTI, 
    SKGP_1_ADMIN_AKTIVNOST_ISKLJUCIVANJE,
    SKGP_1_CLAN_PROVJERA_PODACI_GRUPE,
    SKGP_1_CLAN_PROVJERA_KORISNIKA_GRUPE,
    SKGP_1_CLAN_PROVJERA_PODACI_KORISNIKA_GRUPE,
    SKGP_1_CLAN_IMENA_GRUPA, 
    SKGP_N1_ADMIN_AKTIVNOST_PREIMENOVANJE_GRUPE
}
