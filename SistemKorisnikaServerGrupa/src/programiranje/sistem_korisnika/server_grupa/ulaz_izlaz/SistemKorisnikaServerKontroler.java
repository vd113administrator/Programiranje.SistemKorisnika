/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.server_grupa.ulaz_izlaz;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.sistem_korisnika.jezgro.baza_podataka.SistemKorisnikaAdapterBaze;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarBrisanjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarClanstvoBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarIsclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarIskljucenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarListanjaKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPodaciGrupe;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPodaciKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPravljenjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPreimenovanjaGrupaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarProveraClanstvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarProveraVlasnistvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarUclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ReazultatUclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatBrisanjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatClanstvoBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatIsclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatIskljucenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatListanjaGrupa;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatListanjaKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPodaciGrupe;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPodaciKorisnika;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPravljenjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPreimenovanjaGrupaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatProveraClanstvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatProvereVlasnistvaBean;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati;

/**
 * Kontrolne funkcije za funkcionisanje servera koji korisnte adapter 
 * @author Mikec
 */
public class SistemKorisnikaServerKontroler {
    private SistemKorisnikaServerAdapter server;
    private SistemKorisnikaAdapterBaze base;
    
    /**
     * Konstruktor
     * @param adapter komunikacija na nivou sistema korisnika
     * @param dbadapter  komunikacija prema bazi podataka 
     */
    public SistemKorisnikaServerKontroler(SistemKorisnikaServerAdapter adapter, 
                                          UserDatabaseAdapter dbadapter){
        server = adapter;
        base = new SistemKorisnikaAdapterBaze(dbadapter); 
    }
    
    /**
     * Konstruktor
     * @param adapter komunikacija na nivou baze korisnika 
     * @param dbadapter  komunikacija prema bazi
     */
    public SistemKorisnikaServerKontroler(ServerDataAdapter adapter, 
                                          UserDatabaseAdapter dbadapter){
        server = new SistemKorisnikaServerAdapter(adapter);
        base = new SistemKorisnikaAdapterBaze(dbadapter);
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za klijenta na nivou sistema korisnika
     * @return adapter 
     */
    public SistemKorisnikaServerAdapter getSKServerGrupaAdapter(){
        return server;
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za klijenta na nivou baze korisnika
     * @return adapter 
     */
    public ServerDataAdapter getBKServerShellAdapter(){
        return server.getBKShellServerAdapter(); 
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za bazu podataka na nivou sistema korisnika
     * @return adapter 
     */
    public SistemKorisnikaAdapterBaze getSKAdapterDatabase(){
        return base;
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za klijenta na nivou baze korisnika
     * @return adapter 
     */
    public UserDatabaseAdapter getBKAdapterDatabase(){
        return base.getAdapterBKShellServerBaze(); 
    }
    
    /**
     * Aktivnost pravljenja grupe
     */
    public void pravljenjeGrupe(){ 
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PRAVLJENJE_GRUPE");
            ParametarPravljenjeGrupeBean bean = server.fromClientPravljenjeGrupe();
            RezultatPravljenjeGrupeBean uspeh = new RezultatPravljenjeGrupeBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
            boolean res = base.pravljenjeGrupe(bean.getBuduciAdministrator(), bean.getNazivNoveGrupe());
            if(res) server.toClientPravljenjeGrupe(uspeh);
            else server.toClientPravljenjeGrupe(new RezultatPravljenjeGrupeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Pravljenje grupe neuspjesno. Provjeri egzistenciju."));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Akivnost brisanja grupe
     */
    public void brisanjeGrupe(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:BRISANJE_GRUPE");
            ParametarBrisanjeGrupeBean bean = server.fromClientBrisanjeGrupe();
            RezultatBrisanjeGrupeBean uspeh = new RezultatBrisanjeGrupeBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
            boolean res = base.brisnanjeGrupe(bean.getAdministrator(), bean.getNazivNoveGrupe());
            if(res) server.toClientBrisanjeGrupe(uspeh);
            else server.toClientBrisanjeGrupe(new RezultatBrisanjeGrupeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Brisanje grupe neuspjesno. Provjeri egzistenciju."));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost uclanjenja 
     */
    public void uclanjenje(){
        try {
            ParametarUclanjenjeBean bean = server.fromClientUclanjenje();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:UCLANJENJE");
            ReazultatUclanjenjeBean uspeh = new ReazultatUclanjenjeBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
            boolean res = base.uclanjenje(bean.getKorisnik(), bean.getNazivGrupe());
            if(res) server.toClientUclanjenje(uspeh);
            else server.toClientUclanjenje(new ReazultatUclanjenjeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Uclanjenje neuspjesno. Provjeri egzistenciju."));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost isclanjenja
     */
    public void isclanjenje(){
        try {
            ParametarIsclanjenjeBean bean = server.fromClientIsclanjenje();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:ISCLANJENJE");
            RezultatIsclanjenjeBean uspeh = new RezultatIsclanjenjeBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
            boolean res = base.isclanjenje(bean.getClan(), bean.getNazivGrupe());
            if(res) server.toClientIsclanjenje(uspeh);
            else server.toClientIsclanjenje(new RezultatIsclanjenjeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Isclanjenje neuspjesno. Provjeri egzistenciju."));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost iskljucenja 
     */
    public void iskljucenje(){
        try {
            ParametarIskljucenjeBean bean = server.fromClientIskljucenje();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:ISKLJUCENJE");
            
            List<String> grupe = base.listaVlasnistva(bean.getAdministrator());
            if(!grupe.contains(bean.getGrupa()))
                server.toClientIskljucenje(new RezultatIskljucenjeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Iskljucenje neuspjesno. Provjeri administratora."));
            else{
                RezultatIskljucenjeBean uspeh = new RezultatIskljucenjeBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
                boolean res = base.isclanjenje(bean.getKorisnik(), bean.getGrupa());
                if(res) server.toClientIskljucenje(uspeh);
                else server.toClientIskljucenje(new RezultatIskljucenjeBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Iskljucenje neuspjesno. Provjeri egzistenciju."));
            }
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Aktivnost provjere clanstva
     */
    public void provjeraClanstva(){
        try {
            ParametarClanstvoBean bean = server.fromClientClanstvo();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PROVJERA_CLANSTVA");
            RezultatClanstvoBean uspeh = new RezultatClanstvoBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
            boolean res = base.provjeraClanstva(bean.getKorisnik(), bean.getGrupa());
            if(res) server.toClientClanstvo(uspeh);
            else server.toClientClanstvo(new RezultatClanstvoBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Nije clan grupe."));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Lista provjere clanstva 
     */
    public void listaVlasnistva(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:VLASNISTVO");
            ParametarProveraVlasnistvaBean bean = server.fromClientListaVlasnistva();
            RezultatProvereVlasnistvaBean rbean = new RezultatProvereVlasnistvaBean(base.listaVlasnistva(bean.getKorisnik()));
            server.toClientListaVlasnistva(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    /**
     * Aktivnost listanja grupa pripadnosti
     */
    public void listaPripadnosti(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:CLANSTVO");
            ParametarProveraClanstvaBean bean = server.fromClientListaClanstva();
            RezultatProveraClanstvaBean rbean = new RezultatProveraClanstvaBean(base.listaClanstva(bean.getKorisnik()));
            server.toClientListaClanstva(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost listanja grupe korisnika
     */
    public void listaKorisnika(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:KORISNICI");
            ParametarListanjaKorisnikaBean bean = server.fromClientListanjeKorisnika();
            RezultatListanjaKorisnikaBean rbean = new RezultatListanjaKorisnikaBean(base.listaKorisnika(bean.getGrupa(),bean.getAdministrator()));
            server.toClientListanjeKorisnika(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost listanja podataka grupe
     */
    public void podaciGrupe(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PODACI_GRUPE");
            ParametarPodaciGrupe bean = server.fromClientPodaciGrupe();
            RezultatPodaciGrupe rbean = new RezultatPodaciGrupe(base.podaciOGrupi(bean.getAdministrator(), bean.getGrupa()));
            server.toClientPodaciGrupe(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost listanja podataka korisnika 
     */
    public void podaciKorisnika(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PODACI_KORISNIKA");
            ParametarPodaciKorisnikaBean bean = server.fromClientPodaciKorisnika();
            RezultatPodaciKorisnika rbean = new RezultatPodaciKorisnika(base.podaciOKorisniku(bean.getAdministrator(), bean.getGrupa(), bean.getKorisnik()));
            server.toClientPodaciKorisnika(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost listanje grupa 
     */
    public void listaGrupa(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:GRUPE");
            RezultatListanjaGrupa rbean = new RezultatListanjaGrupa(base.naziviGrupa());
            server.toClientImenaGrupa(rbean);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost preimenovanja grupe
     */
    public void preimenovanjeGrupe(){
        try {
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PREIMENOVANJE");
            ParametarPreimenovanjaGrupaBean bean = server.fromClientPreimenovanjeGrupe();
            RezultatPreimenovanjaGrupaBean res = base.preimenovanjaGrupa(bean.getAdminUname(),bean.getStariNaziv(), bean.getNoviNaziv());
            server.toClientPreimenovanjeGrupe(res);
        } catch (SQLException ex) {
            Logger.getLogger(SistemKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
