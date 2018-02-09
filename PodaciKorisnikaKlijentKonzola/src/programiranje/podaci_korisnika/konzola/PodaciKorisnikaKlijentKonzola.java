/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konzola;

import programiranje.baza_korisnika_console.klijent.Aplikacija;
import programiranje.baza_korisnika_console.ekstenzije.BKSPEkstenzije;
import programiranje.baza_korisnika_console.model.PrijavaBean;
import programiranje.podaci_korisnika.konfig.PodaciKorisnikaInicijalizerKonzole;
import programiranje.podaci_korisnika.konzola.ui.PodaciKorisnikaAdapterKlijenta;

/**
 * Glavma klasa konzolnog klijenta sistema  korisnika<br>
 * Prosiranje od konzolnog klijenta baze korisnika
 * @author Mikec
 */
public class PodaciKorisnikaKlijentKonzola extends Aplikacija implements BKSPEkstenzije{    
    /**
     * Pocetna tacka 
     * @param args argumenti 
     */
    public static void main(String[] args) {
        PodaciKorisnikaKlijentKonzola konzola = new PodaciKorisnikaKlijentKonzola();
        setEkstenzije(konzola);
        PodaciKorisnikaInicijalizerKonzole.inicijalizacija();
        Aplikacija.main(args);
    }

    /**
     * Aktivnost za komunikaciju sa serverom u nastavku sa bazom korisnika  
     * @param izbor izbor
     * @return da li je izasao 
     */
    @Override
    public boolean run(int izbor){
        PodaciKorisnikaAdapterKlijenta adapter = new PodaciKorisnikaAdapterKlijenta(server,inputThread); 
        switch(izbor){
            case 6:
                adapter.listaKorisnika();
                return true;
            case 7:
                adapter.podaciKorisnika();
                return true; 
            case 8:
                adapter.podaciDrugogKorisnika();
                return true; 
            case 9: 
                adapter.zahtijevZaVlastitimSertifikatom();
                return true; 
            case 10: 
                adapter.listaSertifikata();
                return true; 
            case 11: 
                adapter.listaSertifikovanih();
                return true; 
            case 12: 
                adapter.zahtijevZaSertifikatomDrugogKorisnika();
                return true; 
            case 13: 
                adapter.listaSlikaBezPoruke();
                return true; 
            case 14: 
                adapter.listaProcitanihPoruka();
                return true; 
            case 15:
                adapter.listaProcitanihEnkriptovanihPoruka();
                return true;
            case 16: 
                adapter.listaNeprocitanihPoruka();
                return true; 
            case 17:
                adapter.stvaranjeSkripteZaUgradjivanje();
                return true; 
            case 18: 
                adapter.stvaranjeSkripteZaIzdvajanje();
                return true; 
            case 19:
                adapter.ocitavanjeEnkriptovanePoruke();
                return true;
            case 20:
                adapter.slanjePorukeUSlici();
                return true; 
            case 21: 
                adapter.listaNeprimljenihPorukaServera();
                return true; 
            case 22:
                adapter.prijemSvihPorukaSaServera();
                return true; 
            case 23: 
                adapter.prijemPorukeSaServera();
                return true; 
            case 24: 
                adapter.ocitavanjeKontrolnePoruke();
                return true; 
            case 25: 
                adapter.listaKontrolnihPoruka();
                return true;
            case 26: 
                adapter.mapaIsporuka();
                return true; 
            default:
                return false;
        }
    }

    /**
     * Ispis menija 
     * @return izbor 
     */
    @Override
    public int meni(){
        System.out.println("\nPODACI_KORISNIKA.MENI");
        System.out.println("\t6. Lista korisnika u bazi");
        System.out.println("\t7. Podaci prijavljenog korisnika");
        System.out.println("\t8. Podaci drugog korisnika");
        System.out.println("\nPODACI_KORISNIKA.MENI:SERTIFIKOVANJE");
        System.out.println("\t9. Zahtijev za vlastitim sertifikatom \n\t   "+
                          "(plus privatni kljuc i serverski sertifikat)");
        System.out.println("\t10. Lista imena svih sertifikata na serveru");
        System.out.println("\t11. Lista imena svih sertifikoanih korisnika");
        System.out.println("\t12. Zahtijev za sertifikatom drugog korisnika");
        System.out.println("\nPODACI_KORISNIKA.MENI:STEGANOGRAFIJA");
        System.out.println("\t13. Ocitavanje liste slika bez poruke");
        System.out.println("\t14. Ocitavanje liste procitanih otvorenih poruka");
        System.out.println("\t15. Ocitavanje liste procitanih kriptovanih poruka");
        System.out.println("\t16. Ocitavanje liste neprocitanih poruka");
        System.out.println("\t17. Kreiranje skripte za ugradjivanje poruke u sliku");
        System.out.println("\t18. Kreiranje skripte za izdvajanje poruke iz slike");
        System.out.println("\t19. Ocitavanje kriptovane poruke izdvojene iz slike");
        System.out.println("\t20. Slanje poruke u slici");
        System.out.println("\t21. Listanje neprimljenih poruka na serveru");
        System.out.println("\t22. Zahtijev za prijemom svih poruka sa servera");
        System.out.println("\t23. Zahtijev za prijemom ciljne poruke sa servera");
        System.out.println("\t24. Kontrolno ocitavanje poruka u slici za slanje ili ugradnju u sliku");
        System.out.println("\t25. Listanje poruka u formata za kontrolisanje sadrzaja");
        System.out.println("\t26. Ocitavanje spiska isporuka sa servera");
        return 0;
    }
    
    /**
     * Dobijanje sesije 
     * @return sesija
     */
    public static String getSesija(){
        return sesija;
    }
    
    /**
     * Dobijanje prijavljenog korisnika 
     * @return korisnik
     */
    public static PrijavaBean getPrijavljeniKorisnik(){
        return prijavaBean;
    }
}