/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.klijent_konzola;

import programiranje.baza_korisnika_console.klijent.Aplikacija;
import programiranje.baza_korisnika_console.ekstenzije.BKSPEkstenzije;
import programiranje.baza_korisnika_console.model.PrijavaBean;
import programiranje.sistem_korisnika.klijent_konzola.ulaz_izlaz.SistemKorisnikaAdapterKlijenta;

/**
 * Glavma klasa konzolnog klijenta sistema  korisnika<br>
 * Prosiranje od konzolnog klijenta baze korisnika
 * @author Mikec
 */
public class SistemKorisnikaKlijentKonzola extends Aplikacija implements BKSPEkstenzije{    
    /**
     * Pocetna tacka 
     * @param args argumenti 
     */
    public static void main(String[] args) {
        SistemKorisnikaKlijentKonzola konzola = new SistemKorisnikaKlijentKonzola();
        setEkstenzije(konzola);
        Aplikacija.main(args);
    }

    /**
     * Aktivnost za komunikaciju sa serverom u nastavku sa bazom korisnika  
     * @param izbor izbor
     * @return da li je izasao 
     */
    @Override
    public boolean run(int izbor){
        SistemKorisnikaAdapterKlijenta adapter = new SistemKorisnikaAdapterKlijenta(server,inputThread); 
        switch(izbor){
            case 6:
                adapter.pravljenjeGrupe();
                return true;
            case 7:
                adapter.brisanjeGrupe();
                return true; 
            case 8: 
                adapter.uclanjenje();
                return true;
            case 9: 
                adapter.isclanjenje();
                return true;
            case 10:
                adapter.iskljucenje();
                return true; 
            case 11: 
                adapter.clanstvoUGrupama();
                return true;
            case 12: 
                adapter.listaVlasnistva();
                return true;
            case 13: 
                adapter.listaClanova();
                return true;
            case 14: 
                adapter.podaciGrupe();
                return true; 
            case 15: 
                adapter.listaKorisnika();
                return true; 
            case 16: 
                adapter.podaciKorisnika();
                return true; 
            case 17: 
                adapter.imenaGrupa();
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
        System.out.println("\nSISTEM_KORISNIKA.MENI");
        System.out.println("\t6. Dodavanje grupe");
        System.out.println("\t7. Brisanje grupe");
        System.out.println("\t8. Uclanjenje u grupu");
        System.out.println("\t9. Isclanjenje iz grupe");
        System.out.println("\t10. Iskljucenje iz grupe");
        System.out.println("\t11. Provjera clanstva u grupi");
        System.out.println("\t12. Lista grupa u vlasnistvu");
        System.out.println("\t13. Lista grupa u kojima je clan");
        System.out.println("\t14. Dobijanje podataka grupe");
        System.out.println("\t15. Dobijanje korisnika grupe");
        System.out.println("\t16. Provjera podataka korisnika u grupi");
        System.out.println("\t17. Imena svih grupa");
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