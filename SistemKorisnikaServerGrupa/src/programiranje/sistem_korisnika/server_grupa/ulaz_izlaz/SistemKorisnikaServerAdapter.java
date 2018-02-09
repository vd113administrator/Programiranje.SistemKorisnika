/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.server_grupa.ulaz_izlaz;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarBrisanjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarClanstvoBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarIsclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarIskljucenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarListanjaKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPodaciGrupe;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPodaciKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPravljenjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarPreimenovanjaGrupaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatBrisanjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPravljenjeGrupeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarProveraClanstvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarProveraVlasnistvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ParametarUclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.ReazultatUclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatClanstvoBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatIsclanjenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatIskljucenjeBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatListanjaGrupa;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatListanjaKorisnikaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPodaciGrupe;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPodaciKorisnika;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPreimenovanjaGrupaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatProveraClanstvaBean;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatProvereVlasnistvaBean;

/**
 * Prosirenje komunikacije, ka klijentu, baze korisnika na sistem korisnika
 * @author Mikec
 */
public class SistemKorisnikaServerAdapter {
    private static ServerDataAdapter server;
    
    /**
     * Konstruktor 
     * @param adapter komunikacije klijenta - baza korisnika 
     */
    public SistemKorisnikaServerAdapter(ServerDataAdapter adapter){
        server = adapter;
    }
    
    /**
     * Dobijanje komunikacije baze_korisnika prema kiljentima
     * @return komunikacija
     */
    public ServerDataAdapter getBKShellServerAdapter(){
        return server;
    }
    
    /**
     * Kreiranje grupe i ulazni podaci klijenta 
     * @return parametrizovani podaci
     */
    public ParametarPravljenjeGrupeBean fromClientPravljenjeGrupe(){
        try {
            String admin = server.readLine();
            String group = server.readLine();
            return new ParametarPravljenjeGrupeBean(admin, group);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Vracanje povratnog rezultata pri pravljenju grupe
     * @param x parametrizovani rezultat pravljenja grupe
     */
    public void toClientPravljenjeGrupe(RezultatPravljenjeGrupeBean x){
        server.writeLine(x.getRezultat());
        server.writeLine(x.getGreska());
    }
    
    /**
     * Podaci za brsanje grupe
     * @return parametrizovani podaci brisanja grupe
     */
    public ParametarBrisanjeGrupeBean fromClientBrisanjeGrupe(){
        try {
            String admin = server.readLine();
            String group = server.readLine();
            return new ParametarBrisanjeGrupeBean(admin, group);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata brisanja grupe
     * @param x rezultat brisanja grupe
     */
    public void toClientBrisanjeGrupe(RezultatBrisanjeGrupeBean x){
        server.writeLine(x.getRezultat());
        server.writeLine(x.getGreska());
    }
    
    /**
     * Dobijanje podataka za ocitavanje liste clanstva (grupe u kojima je clan) 
     * @return parametrizovani podaci dobijeni od klijenta 
     */
    public ParametarProveraClanstvaBean fromClientListaClanstva(){
        try {
            String user = server.readLine();
            return new ParametarProveraClanstvaBean(user);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Kojent i lista clanstva
     * @param x parametrizovan rezultat ocitavanja liste grupa clanstva iz baze 
     */
    public void toClientListaClanstva(RezultatProveraClanstvaBean x){
        List<String> lista = x.getImenaGrupa();
        server.writeLine(Integer.toString(lista.size()));
        for(String str: lista)
            server.writeLine(str);
    }
    
    /**
     * Dobijanje parametara liste grupa vlasnictva od klijenta 
     * @return parametrizovana lista grupa
     */
    public ParametarProveraVlasnistvaBean fromClientListaVlasnistva(){
        try {
            String user = server.readLine();
            return new ParametarProveraVlasnistvaBean(user);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Ispis rezultata listanja grupa vlasnistva klijentu 
     * @param x parametrizovan rezultat 
     */
    public void toClientListaVlasnistva(RezultatProvereVlasnistvaBean x){
        List<String> lista = x.getImenaGrupa();
        server.writeLine(Integer.toString(lista.size()));
        for(String str: lista)
           server.writeLine(str);
    }
    
    /**
     * Octavanje podataka za uclanjenje u grupu s klijentske strane 
     * @return parametrizovani podaci
     */
    public ParametarUclanjenjeBean fromClientUclanjenje(){
        try {
            String user = server.readLine();
            String grupa = server.readLine();
            return new ParametarUclanjenjeBean(grupa,user);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Davanje klijentu rezultata ucitavanja 
     * @param rezultat parametrizovani podaci
     */
    public void toClientUclanjenje(ReazultatUclanjenjeBean rezultat){
        server.writeLine(rezultat.getInfo());
        server.writeLine(rezultat.getError());
                
    }
    
    /**
     * Dobijanje podataka za isclanjenje od klijenta 
     * @return parametrizovani podaci
     */
    public ParametarIsclanjenjeBean fromClientIsclanjenje(){
        try {
            String user = server.readLine();
            String grupa = server.readLine();
            return new ParametarIsclanjenjeBean(user, grupa);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata isclanjenja klijentu
     * @param rezultat parametrizovani rezultat
     */
    public void toClientIsclanjenje(RezultatIsclanjenjeBean rezultat){
        server.writeLine(rezultat.getInfo());
        server.writeLine(rezultat.getError());
    }
    
    /**
     * Dobijanje podataka za iskljucenje od klijenta 
     * @return parametrizovani podaci
     */
    public ParametarIskljucenjeBean fromClientIskljucenje(){
        try {
            String administrator = server.readLine(); 
            String grupa = server.readLine();
            String user = server.readLine();
            return new ParametarIskljucenjeBean(administrator, user, grupa);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Dobijanje podataka za iskljucenje
     * @param rezultat iskljucenja
     */
    public void toClientIskljucenje(RezultatIskljucenjeBean rezultat){
        server.writeLine(rezultat.getInfo());
        server.writeLine(rezultat.getError());
    }
    
    /**
     * Dobjanje podataka za provjeravanja clanstva sa klijenta
     * @return parametarizovani podaci
     */
    public ParametarClanstvoBean fromClientClanstvo(){
        try { 
            String user = server.readLine();
            String grupa = server.readLine();
            return new ParametarClanstvoBean(user, grupa);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata provjere clanstva 
     * @param rezultat provjere clanstva
     */
    public void toClientClanstvo(RezultatClanstvoBean rezultat){
        server.writeLine(rezultat.getRezultat());
        server.writeLine(rezultat.getGreska());
    }
    
    /**
     * Dobijanje podataka za ocitavanje korisnika grupe
     * @return parametrizovani podaci
     */
    public ParametarListanjaKorisnikaBean fromClientListanjeKorisnika(){
        try { 
            String administrator = server.readLine();
            String grupa = server.readLine();
            return new ParametarListanjaKorisnikaBean(administrator, grupa);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata listanja korisnika klijentu 
     * @param x parametrizovani rezultat
     */
    public void toClientListanjeKorisnika(RezultatListanjaKorisnikaBean x){
        List<String> lista = x.getImenaClanova();
        server.writeLine(Integer.toString(lista.size()));
        for(String str: lista)
           server.writeLine(str);
    }
    
    /**
     * Slanje rezultata ocitavanja imena grupa klijentu 
     * @param x parametrizovani rezultat 
     */
    public void toClientImenaGrupa(RezultatListanjaGrupa x){
        List<String> lista = x.getGrupe();
        server.writeLine(Integer.toString(lista.size()));
        for(String str: lista)
           server.writeLine(str);
    }
    
    /**
     * Dobijanje podataka parametara za ocitavanje podataka o grupi 
     * @return parametrizovani parametri
     */
    public ParametarPodaciGrupe fromClientPodaciGrupe(){
        try {
            String a = server.readLine();
            String b = server.readLine(); 
            return new ParametarPodaciGrupe(a,b);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata ocitavanja podataka grupe klijentu
     * @param rp parametrizovani rezultat
     */
    public void toClientPodaciGrupe(RezultatPodaciGrupe rp){
        server.writeLine(rp.getGrupa().getAdministrator().getUsername());
        server.writeLine(rp.getGrupa().getIdentifikator());
        server.writeLine(rp.getGrupa().getNaziv());
    }
    
    /**
     * Dobijanje podataka parametara za ocitavanje podataka korisnika
     * @return parametrizovani podaci
     */
    public ParametarPodaciKorisnikaBean fromClientPodaciKorisnika(){
        try {
            String a = server.readLine();
            String b = server.readLine(); 
            String c = server.readLine();
            return new ParametarPodaciKorisnikaBean(a,b,c);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata ocitavanja parametara korisnika ka klijentu 
     * @param r parametrizovani rezultati
     */
    public void toClientPodaciKorisnika(RezultatPodaciKorisnika r){
        server.writeLine(r.getKorisnik().getName());
        server.writeLine(r.getKorisnik().getSurname());
        server.writeLine(r.getKorisnik().getUsername());
        server.writeLine(r.getKorisnik().getAddress());
        server.writeLine(r.getKorisnik().getIdentificator());
        server.writeLine(r.getKorisnik().getWorkplaces());
        server.writeLine(r.getKorisnik().getTelephone());
        server.writeLine(r.getKorisnik().getEmail());
        server.writeLine(r.getKorisnik().getWebs());
        server.writeLine(r.getKorisnik().getType());
    }
    
    /**
     * Prijem podataka za preimenovanje grupe, od klijenta
     * @return parametrizovani podatak
     */
    public ParametarPreimenovanjaGrupaBean fromClientPreimenovanjeGrupe(){
        try {
            String adminuname = server.readLine();
            String oldname = server.readLine(); 
            String newname = server.readLine();
            return new ParametarPreimenovanjaGrupaBean(adminuname,oldname,newname);
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Slanje rezultata preimenovanja podataka 
     * @param r parametrizovani rezultati 
     */
    public void toClientPreimenovanjeGrupe(RezultatPreimenovanjaGrupaBean r){
        server.writeLine(r.getUspjeh());
        server.writeLine(r.getGreska());
    }
}
