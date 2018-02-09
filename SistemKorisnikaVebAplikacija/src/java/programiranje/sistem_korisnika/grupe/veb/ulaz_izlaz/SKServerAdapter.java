/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.veb.ulaz_izlaz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGP;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati;
import static programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati.SKGP_O1_POZITIVNO;
import programiranje.sistem_korisnika_web.opis.kontrola.PrivremeneInformacije;

/**
 * Komunikacija prema klijenti za sistem korisnika i SKGP
 * @author Mikec
 */
public class SKServerAdapter{
    private AdapterServera as; 
    
    /**
     * Konstruktor
     * @param as adapter servera
     */
    public SKServerAdapter(AdapterServera as){
        this.as = as;
    }
    
    /**
     * Dobijanje adaptera servera
     * @return komunikacija prema klijentu na nivou baze
     */
    public AdapterServera getAdapterServera(){
        return as;
    }
    /**
     * Dobijanje grupa u kojima je prijavljeni korisnik clan
     * @return grupe u kojima je se clan 
     */
    public ArrayList<String> getImenaGrupaZaClanstvo(){
        try {
            ArrayList<String> grupe = new ArrayList<>();
            String ok = GlobalneFunkcije.getSesijskiObjekti().getSessionId();
            String admin = as.podaciIn().getValue().get(0);
            if(ok==null) return grupe;
            as.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_PRIPADNOSTI.toString());
            as.writeLine(admin);
            int ngrupa = Integer.parseInt(as.readLine());
            for(int i=0; i<ngrupa; i++)
                grupe.add(as.readLine());
            return grupe; 
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    /**
     * Donijanje imena grupa za koje je korisnik vlasnik 
     * @return grupe u kojima je se administrator
     */
    public ArrayList<String> getImenaGrupaZaVlasnistvo(){
        try {
            ArrayList<String> grupe = new ArrayList<>();
            String ok = GlobalneFunkcije.getSesijskiObjekti().getSessionId();
            String admin = as.podaciIn().getValue().get(0);
            if(ok==null) return grupe;
            as.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_VLASNISTVA.toString());
            as.writeLine(admin);
            int ngrupa = Integer.parseInt(as.readLine());
            for(int i=0; i<ngrupa; i++)
                grupe.add(as.readLine());
            return grupe; 
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Dobijanje liste svih grupa
     * @return lista svih grupe
     */
    public ArrayList<String> getListaSvihGrupa(){      
        ArrayList<String> lista = new ArrayList<String>(); 
        as.writeLine(ProtokolSKGP.SKGP_1_CLAN_IMENA_GRUPA.toString());

        try{
            int n = Integer.parseInt(as.readLine());
            for(int i=0; i<n; i++) 
                lista.add(as.readLine());
        }catch(Exception ex){
        }
        
        PrivremeneInformacije.setImenaGrupa(lista);
        return lista; 
    }
    
    /**
     * Dobijanje svih podataka grupe 
     * @param grupa ime grupe
     * @return lista podataka
     */
    public ArrayList<String> getPodaci(String grupa){
        ArrayList<String> grupe = new ArrayList<String>();
        try{
            String ok = GlobalneFunkcije.getSesijskiObjekti().getSessionId();
            String admin = as.podaciIn().getValue().get(0);
            if(ok==null) return grupe;
            
            as.writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_GRUPE.toString());
            
            as.writeLine(admin);
            as.writeLine(grupa);
            
            String admingrupe = as.readLine();
            String imegrupe = as.readLine();
            String idgrupe = as.readLine();
           
            
            grupe.add(imegrupe);
            grupe.add(admingrupe);
            grupe.add(idgrupe);
            return grupe; 
        }
        catch(IOException ex){
            return grupe; 
        }
    }
    
    /**
     * Dodavanje grupe
     * @param user korisnik
     * @param group ime grupe
     * @return rezultat
     */
    public Pair<Boolean,String> dodavanjeGrupe(PrijavaBean user, String group){
        getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_PRAVLJENJE_GRUPE.toString());
        getAdapterServera().writeLine(user.getUsername());
        getAdapterServera().writeLine(group);
        try{
            String res = getAdapterServera().readLine(); 
            String error = getAdapterServera().readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                return new Pair<>(true,"");
            }else{
                return new Pair<>(false,error);
            }
        }catch(Exception ex){
            return new Pair<>(false,"");
        }
    }
    
    /**
     * Brisanje grupe
     * @param user korisnik 
     * @param group ime grupe
     * @return brisanje grupe
     */
    public Pair<Boolean,String> brisanjeGrupe(PrijavaBean user, String group){
        getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_BRISANJE_GRUPE.toString());
        String administrator = user.getUsername();
        String grupa = group;
        getAdapterServera().writeLine(administrator);
        getAdapterServera().writeLine(grupa);
        try{
            String res = getAdapterServera().readLine(); 
            String error = getAdapterServera().readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                return new Pair<>(true,"");
            }else{
                return new Pair<>(false,error); 
            }
        }catch(Exception ex){
            return new Pair<>(false,"");
        }
    } 
    
    /**
     * Preimenovanje grupe
     * @param admin administrator
     * @param stara_grupa staro ime grupe
     * @param nova_grupa novo ime grupe
     * @return rezultat
     */
    public Pair<Boolean,String> preimenovanjeGrupe(PrijavaBean admin, String stara_grupa, String nova_grupa){
        getAdapterServera().writeLine(ProtokolSKGP.SKGP_N1_ADMIN_AKTIVNOST_PREIMENOVANJE_GRUPE.toString());
        getAdapterServera().writeLine(admin.getUsername());
        getAdapterServera().writeLine(stara_grupa);
        getAdapterServera().writeLine(nova_grupa);
        try{
            String res = getAdapterServera().readLine();
            String error = getAdapterServera().readLine();
            if(res.equals(SKGP_O1_POZITIVNO.toString()))
                return new Pair<>(true,"");
            else 
                return new Pair<>(false,error);
        }catch(Exception ex){
            return new Pair<>(false,"");
        }
    }
    
    /**
     * Uclanjenje u grupu 
     * @param administrator administrator
     * @param grupa grupa
     * @return rezultat
     */
    public Pair<Boolean,String> uclanjenje(String administrator, String grupa){
        try {
            getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_KORISNIK_AKTIVNOST_UCLANJENJE.toString());
            getAdapterServera().writeLine(administrator);
            getAdapterServera().writeLine(grupa);
            String res = getAdapterServera().readLine();
            String error = getAdapterServera().readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString()))
                 return new Pair<>(true,"");
            else 
                 return new Pair<>(false,error);
            
        } catch (IOException ex) {
            return new Pair<>(false,"");
        }
    }
    
    /**
     * Isclanjenje iz grupe
     * @param administrator administrator
     * @param grupa ime_grupe
     * @return rezultat operacije
     */
    public Pair<Boolean,String> isclanjenje(String administrator, String grupa){
        try {
            getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_CLAN_AKTIVNOST_ISCLANJENJE.toString());
            getAdapterServera().writeLine(administrator);
            getAdapterServera().writeLine(grupa);
            String res = getAdapterServera().readLine();
            String error = getAdapterServera().readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString()))
                 return new Pair<>(true,"");
            else 
                 return new Pair<>(false,error);
            
        } catch (IOException ex) {
            return new Pair<>(false,"");
        }
    }
    
    /**
     * Ocitavanje korisnika grupe
     * @param za administrator 
     * @param iz ime_grupe
     * @return lista 
     */
    public ArrayList<String> listanjeKorisnikaGrupa(String za, String iz){
        try {
            ArrayList<String> lista = new ArrayList<>();
            getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_KORISNIKA_GRUPE.toString());
            getAdapterServera().writeLine(za);
            getAdapterServera().writeLine(iz);
            int n = Integer.parseInt(getAdapterServera().readLine());
            for(int i=0; i<n; i++) 
                lista.add(getAdapterServera().readLine());
            return lista; 
        } catch (IOException ex) {
            return new ArrayList<String>();
        }
    }
    
    /**
     * Listanje podataka korisnika grupe
     * @param za administrator
     * @param iz ime grupe
     * @param od prijavljeni korisnik
     * @return rezultat listanja
     */
    public ArrayList<String> listanjePodatakaKorisnikaGrupe(String za, String iz, String od){
        try {
            ArrayList<String> lista = new ArrayList<>();
            getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_KORISNIKA_GRUPE.toString());
            getAdapterServera().writeLine(za);
            getAdapterServera().writeLine(iz);
            getAdapterServera().writeLine(od); 
            for(int i=0; i<10; i++)
                lista.add(getAdapterServera().readLine());
            return lista; 
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Iskljucenje korisnika iz grupe
     * @param bean prijava 
     * @param iz ime grupe
     * @param za korisnik
     * @return rezultat grupe
     */
    public Pair<Boolean,String> iskljucenje(PrijavaBean bean, String iz, String za){
        try {
            getAdapterServera().writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_ISKLJUCIVANJE.toString());
            getAdapterServera().writeLine(bean.getUsername());
            getAdapterServera().writeLine(iz);
            getAdapterServera().writeLine(za);
            String res = getAdapterServera().readLine();
            String error = getAdapterServera().readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString()))
                 return new Pair<>(true,"");
            else 
                 return new Pair<>(false,error);
            
        } catch (IOException ex) {
            return new Pair<>(false,"");
        }
    }
}
