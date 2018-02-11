/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konzola;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private static boolean menubreaker = true;
    
    private void menubreak(){
        if(!menubreaker){
            menubreaker = true; 
        }else{
            System.out.print("\n[Pritisni dugme za nastavak ...]");
            try{new BufferedReader(new InputStreamReader(System.in)).readLine();}catch(Exception ex){}
        }
    }
    
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
                menubreak();
                return true;
            case 7:
                adapter.podaciKorisnika();
                menubreak();
                return true; 
            case 8:
                adapter.podaciDrugogKorisnika();
                menubreak();
                return true; 
            case 9: 
                adapter.zahtijevZaVlastitimSertifikatom();
                menubreak();
                return true; 
            case 10: 
                adapter.listaSertifikata();
                menubreak();
                return true; 
            case 11: 
                adapter.listaSertifikovanih();
                menubreak();
                return true; 
            case 12: 
                adapter.zahtijevZaSertifikatomDrugogKorisnika();
                menubreak();
                return true; 
            case 13: 
                adapter.listaSlikaBezPoruke();
                menubreak();
                return true; 
            case 14: 
                adapter.listaProcitanihPoruka();
                menubreak();
                return true; 
            case 15:
                adapter.listaProcitanihEnkriptovanihPoruka();
                menubreak();
                return true;
            case 16: 
                adapter.listaNeprocitanihPoruka();
                menubreak();
                return true; 
            case 17:
                adapter.stvaranjeSkripteZaUgradjivanje();
                menubreak();
                return true; 
            case 18: 
                adapter.stvaranjeSkripteZaIzdvajanje();
                menubreak();
                return true; 
            case 19:
                adapter.ocitavanjeEnkriptovanePoruke();
                menubreak();
                return true;
            case 20:
                adapter.slanjePorukeUSlici();
                menubreak();
                return true; 
            case 21: 
                adapter.listaNeprimljenihPorukaServera();
                menubreak();
                return true; 
            case 22:
                adapter.prijemSvihPorukaSaServera();
                menubreak();
                return true; 
            case 23: 
                adapter.prijemPorukeSaServera();
                menubreak();
                return true; 
            case 24: 
                adapter.ocitavanjeKontrolnePoruke();
                menubreak();
                return true; 
            case 25: 
                adapter.listaKontrolnihPoruka();
                menubreak();
                return true;
            case 26: 
                adapter.mapaIsporuka();
                menubreak();
                return true; 
            case 27:
                adapter.informacijeOSlikama();
                menubreak();
                return true; 
            case 28: 
                adapter.zahtijevZaPovlacenjeSertifikata();
                menubreak();
                return true; 
            case 29: 
                adapter.zahtijevZaCRLListu();
                menubreak();
                return true; 
            case 30: 
                adapter.ocitavanjeCRLListe();
                menubreak();
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
        System.out.println("\nPODACI_KORISNIKA.MENI:OSTALE OPERACIJE");
        System.out.println("\t27. Prikaz informacija o slikama");
        System.out.println("\t28. Zahtijev za povlacenjem sertifikata");
        System.out.println("\t29. Zahtijev za CRL listom");
        System.out.println("\t30. Ocitavanje CRL liste");
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