/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.kontrola.server;

import java.util.HashSet;
import javafx.util.Pair;
import programiranje.baza_korisnika_shell.baza_podataka.ProvjeraImena;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseThread;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.server.ServerGlobalCommunicator;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;

/**
 * Kontrolne funkcije i tacka za sistem registrovanja i pamcenje korisnika
 * @author Mikec
 */
public final class RegistracijaControl {
    private RegistracijaControl(){
    }
   
    private static HashSet<Korisnik> korisnici = new HashSet<>(); 
    private static ServerGlobalCommunicator komunikator; 
    private static UserDatabaseThread dbthread; 
    
    static{
        komunikator = ServerMainProgram.getServerKomunikator();
        dbthread = komunikator.getDatabaseThread();
    }
    
    /**
     * Provjera registrovanosti po identifikatoru 
     * @param id identifikator
     * @return rezultat provjere
     */
    private static synchronized boolean provjeriPoIdentifikatoru(String id){
        for(Korisnik k: korisnici)
            if(k.getDeskripcija().getIdentificator().equals(id)) return true;
        return false; 
    }
    
    /**
     * Provjera registrovanosti po korisnickom imenu
     * @param uname korisnicko ime
     * @return korisnicko ime
     */
    private static synchronized boolean provjeriPoKorisnickomImenu(String uname){
        for(Korisnik k: korisnici)
            if(k.getAutentifikacija().getUsername().equals(uname)) return true;
        return false; 
    }
   
    /**
     * Autorizacioni podaci
     * @param ap autorizacioni podaci
     * @return rezultat i poruka
     */
    public static synchronized Pair<Boolean,String> dodajKorisnikaBezBaze(AutorizacioniPodaci ap){
        Pair<Boolean,String> provera = AutorizacijaControl.provjeraAutorizacijePoHash(ap);
        if(provjeriPoKorisnickomImenu(ap.getAutentifikacija().getUsername())) return new Pair<>(false, "Duplikat korisnickog imena.");
        if(provjeriPoIdentifikatoru(ap.getOpis().getIdentificator()))         return new Pair<>(false, "Duplikat identifikatora.");
        if(provera.getKey()){
            Korisnik k = new Korisnik(ap);
            korisnici.add(k);
        }
        return provera;
    }
    
    /**
     * Aktiviost dodavanja novog korisnika - registrovanje
     * @param ap autorizacioni podaci 
     * @return rezultat i poruka
     */
    public static synchronized Pair<Boolean,String> dodajKorisnika(AutorizacioniPodaci ap){
        Pair<Boolean,String> provera = AutorizacijaControl.provjeraAutorizacije(ap);
        if(!ProvjeraImena.provjeraVSMSBDCM(ap.getAutentifikacija())) return new Pair<>(false, "Nevazeci karakteri.");
        if(!ProvjeraImena.provjeraVSMSBDCM(ap.getOpis())) return new Pair<>(false, "Nevazeca karakteri.");
        if(provjeriPoKorisnickomImenu(ap.getAutentifikacija().getUsername())) return new Pair<>(false, "Duplikat korisnickog imena.");
        if(provjeriPoIdentifikatoru(ap.getOpis().getIdentificator()))         return new Pair<>(false, "Duplikat identifikatora.");
        if(provera.getKey()){
            Korisnik k = new Korisnik(ap);
            korisnici.add(k);
            dbthread.dodaj(k);
        }
        return provera; 
    }
    
    /**
     * Pronalazenje instance autorizacionih podataka u instancu korisnika
     * @param ap autorizacioni podaci 
     * @return korisnik
     */
    public static synchronized  Korisnik getKorisnik(AutentifikacioniPodaci ap){
        for(Korisnik k : korisnici)
            if(k.getAutentifikacija().fullequals(ap)) return k; 
        return null; 
    }
    
    /**
     * Dobijanje korisnika po korisnickom imenu u zavisnosti od registrovanosti 
     * @param un korisncko ime
     * @return korisnik
     */
    public static synchronized  Korisnik getKorisnikPoUsername(String un){
        for(Korisnik k : korisnici)
            if(k.getAutentifikacija().getUsername().equals(un)) return k; 
        return null; 
    }
    
    /**
     * Obrisi korisnika - deregistracija 
     * @param ap autentifikacioni podaci
     * @return rezultat autentifikacionih podataka 
     */
    public static synchronized boolean obrsisiKorisnika(AutentifikacioniPodaci ap){
        Korisnik k = getKorisnik(ap);
        if(k==null) return false; 
        boolean res = korisnici.remove(k);
        if(res) dbthread.obrisi(k);
        return res; 
    }
    
    /**
     * Promjene parametara korisnika
     * @param oldUsername staro korisnicko ime
     * @param newUsername novo korisnicko ime
     * @param newName novo ime 
     * @param newSurname novo prezime 
     * @param newAddress nova adresa 
     * @param newTelephone novi telefon
     * @param newEmails novi emailovi
     * @param newWebs novi sajtovi
     * @param newJobs novi poslovi
     * @return rezultat promjene parametara
     */
    public static synchronized boolean promenaParametara(String oldUsername, String newUsername, String newName,
                String newSurname, String newAddress, String newTelephone, String newEmails, String newWebs, String newJobs){
        if(!ProvjeraImena.provjeraVSMSBDCM(oldUsername,newName,newSurname, newAddress, newTelephone, newEmails, newWebs, newJobs)) return false;
        Korisnik k = getKorisnikPoUsername(oldUsername);
        Korisnik k1 = getKorisnikPoUsername(newUsername);
        if(!oldUsername.equals(newUsername) && k1!=null) return false;
        if(k==null) return false; 
        boolean res = korisnici.remove(k);
        if(res) {
            AutentifikacioniPodaci ap = new AutentifikacioniPodaci(newUsername,k.getAutentifikacija().getPassword());
            OpisniPodaci op = new OpisniPodaci(k.getDeskripcija().getIdentificator(), newName, newSurname, 
                              newAddress, newTelephone, newJobs, newEmails, newWebs);
            AutorizacioniPodaci az = new AutorizacioniPodaci(op,ap);
            Korisnik k2 = new Korisnik(az);
            korisnici.add(k2);
            dbthread.promenaParametara(oldUsername, newUsername, op);
        }
        return res; 
    }
    
    /**
     * Promjena sifre
     * @param oldUsername staro korisnicko ime
     * @param newPassword nova sifra 
     * @return rezultat promjene
     */
    public static synchronized boolean promenaSifre(String oldUsername, String newPassword){
        if(!ProvjeraImena.provjeraVSMSBDCM(oldUsername, newPassword)) return false;
        Korisnik k = getKorisnikPoUsername(oldUsername);
        if(k==null) return false; 
        boolean res = korisnici.remove(k);
        if(res) {
            AutentifikacioniPodaci ap = new AutentifikacioniPodaci(oldUsername, newPassword);
            ap.gotoHashMode(komunikator.getSaltManager(), komunikator.getUpravljanjeSiframa());
            OpisniPodaci op = k.getDeskripcija();
            AutorizacioniPodaci az = new AutorizacioniPodaci(op,ap);
            Korisnik k2 = new Korisnik(az);
            korisnici.add(k2);
            dbthread.promenaSifre(oldUsername, ap);
        }
        return true; 
    }
    
    /**
     * Promjena tipa korisnika
     * @param oldUsername staro korisnicko ime
     * @param newType novi tip 
     * @return rezultat promjene tipa
     */
    public static synchronized boolean promenaTipa(String oldUsername, String newType){
        if(!ProvjeraImena.provjeraVSMSBDCM(oldUsername, newType)) return false;
        Korisnik k = getKorisnikPoUsername(oldUsername);
        if(k==null) return false; 
        boolean res = korisnici.remove(k);
        if(res) {
            dbthread.promenaTipa(oldUsername, newType);
        }
        return true; 
    }
    
    public static synchronized boolean proveraSifre(String oldUsername, String candidatePassword){
        if(!ProvjeraImena.provjeraVSMSBDCM(oldUsername, candidatePassword)) return false;
        Korisnik k = getKorisnikPoUsername(oldUsername);
        if(k==null) return false; 
        AutentifikacioniPodaci ap = new AutentifikacioniPodaci(oldUsername, candidatePassword);
        ap.setHashManagment(komunikator.getSaltManager(), komunikator.getUpravljanjeSiframa());
        return dbthread.proveraSifre(oldUsername, ap);
    }
}
