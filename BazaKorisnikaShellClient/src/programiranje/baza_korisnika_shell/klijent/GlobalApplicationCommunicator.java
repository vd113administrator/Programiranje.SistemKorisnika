/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.klijent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import programiranje.baza_korisnika_shell.config.data.ShellClientConfiguration;
import programiranje.baza_korisnika_shell.constants.StatikaKonfiguracije;
import programiranje.baza_korisnika_shell.pogled.kontrola.DeregistracijaController;
import programiranje.baza_korisnika_shell.pogled.kontrola.LoggedInController;
import programiranje.baza_korisnika_shell.pogled.kontrola.LoggedOutController;
import programiranje.baza_korisnika_shell.pogled.kontrola.MainController;
import programiranje.baza_korisnika_shell.pogled.kontrola.OdijavaController;
import programiranje.baza_korisnika_shell.pogled.kontrola.PrijavaController;
import programiranje.baza_korisnika_shell.pogled.kontrola.RegistracijaController;

/**
 * Opsti komunikator aplikacije, barata sa operacijama i grafickim prikazima
 * @author Mikec
 */
public class GlobalApplicationCommunicator {
    private MainController main; 
    private LoggedOutController loggedout;
    private LoggedInController loggedin;
    private PrijavaController prijava;
    private OdijavaController odijava; 
    private RegistracijaController registracija; 
    private DeregistracijaController deregistracija;
    
    private Deque<ClientController> red = new ArrayDeque<>();
    
    /**
     * Osnovni kontroler
     * @return kontroler 
     */
    public MainController getMain(){
        return main; 
    }
    
    /**
     * Kontroler odjavaljenosti
     * @return kontroler
     */
    public LoggedOutController getLoggedout(){
        return loggedout;
    }
    
    /**
     * Kontroler prijavenosti
     * @return kontroler
     */
    public LoggedInController getLoggedIn(){
        return loggedin;
    }
    
    /**
     * Kontroler prijeve 
     * @return kontroler
     */
    public PrijavaController getPrijava(){
        return prijava;
    }
    
    /**
     * Kontroler odjave
     * @return kontroler
     */
    public OdijavaController getOdijava(){
        return odijava; 
    }
    
    /**
     * Kontroler registracije
     * @return kontroler
     */
    public RegistracijaController getRegistracija(){
        return registracija; 
    }
    
    /**
     * Kontroler deregistracije
     * @return kontroler 
     */
    public DeregistracijaController getDeregistracija(){
        return deregistracija; 
    }
    
    /**
     * Kontroler pocetnog prikaza 
     * @param main kontroler
     */
    public void setMain(MainController main){
        this.main = main;
    }
    
    /**
     * Postavljanje aktivnog kontrolera 
     * @param c kontroler
     */
    public void postavi(ClientController c){
        if(c instanceof LoggedOutController)
            loggedout = (LoggedOutController) c;
        if(c instanceof LoggedInController)
            loggedin = (LoggedInController) c;
        if(c instanceof PrijavaController)
            prijava = (PrijavaController) c;
        if(c instanceof OdijavaController)
            odijava = (OdijavaController) c;
        if(c instanceof RegistracijaController)
            registracija = (RegistracijaController) c;
        if(c instanceof DeregistracijaController)
            deregistracija = (DeregistracijaController) c;
    }
    
    /**
     * Ponistavanje kontrolera
     * @param c kontroler
     */
    public void ponisti(ClientController c){
        if(c instanceof LoggedOutController)
            loggedout = null;
        if(c instanceof LoggedInController)
            loggedin = null;
        if(c instanceof PrijavaController)
            prijava = null;
        if(c instanceof OdijavaController)
            odijava = null;
        if(c instanceof RegistracijaController)
            registracija = null;
        if(c instanceof DeregistracijaController)
            deregistracija = null;
    }
    
    /**
     * Postavljanje na vrh reda izvrsavanja 
     * @param c kontroler
     * @return da li je postavljen
     */
    public boolean postaviNaRed(ClientController c){
        for(ClientController cc: red){
            if(cc.getClass().equals(c.getClass())) return false;
        }
        red.push(c);
        postavi(c);
        return true;
    }
    
    /**
     * Skidanje kontrolera sa vrha reda 
     * @return kontroler
     */
    public ClientController skiniSaReda(){
        if(red.size()==0) return null;
        ClientController cc = red.pop();
        ponisti(cc);
        return cc;
    }
    
    /**
     * Uzimanje kontrolera s vrha reda 
     * @return kontroler 
     */
    public ClientController dobijSaReda(){
        if(red.size()==0) return null;
        ClientController cc = red.getFirst();
        return cc;
    }
    
    /**
     * Ispraznjenje reda 
     */
    public void isprazniRed(){
        while(red.size()>0) skiniSaReda();
    }
    
    private ShellClientConfiguration konfiguracija = new ShellClientConfiguration();
    {loadKonfiguracija();}
    
    /**
     * Ocitavanje klijentske konfiguracije 
     */
    private void loadKonfiguracija(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(StatikaKonfiguracije.clientConfigFile)));
            konfiguracija = (ShellClientConfiguration) ois.readObject();
            if(konfiguracija==null) konfiguracija = new ShellClientConfiguration();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                ois.close();
            } catch (Exception ex) {    
            }
        }
    }
    
    /**
     * Dobijanje konfiguracije 
     * @return konfiguracija
     */
    public ShellClientConfiguration getKonfiguracija(){
        return konfiguracija;
    }
}