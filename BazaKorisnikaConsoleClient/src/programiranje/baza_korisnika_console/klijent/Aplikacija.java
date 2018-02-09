/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.klijent;

import programiranje.baza_korisnika_console.klijent.io.AdapterServera;
import programiranje.baza_korisnika_console.klijent.io.GlobalniObjekti;
import programiranje.baza_korisnika_console.staticno.ProtokolBKSP;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import programiranje.baza_korisnika_console.ekstenzije.BKSPEkstenzije;
import programiranje.baza_korisnika_console.klijent.io.UlaznaNit;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_CLOSE;
import programiranje.baza_korisnika_console.model.PrijavaBean;
import programiranje.baza_korisnika_console.model.RegistracijaBean;

/**
 * Klasa aplikacije
 * @author Mikec
 */
public class Aplikacija {    
    static{GlobalniObjekti.setShellServer();}
    
    protected static String sesija;
    protected static AdapterServera server = GlobalniObjekti.getShellServerAdapter();
    protected static UlaznaNit inputThread = new UlaznaNit(); 
    protected static PrijavaBean prijavaBean; 
    protected static BKSPEkstenzije ext;
    
    protected static boolean closed = false; 
    
    /**
     * Dobijanje ekstenzije
     * @return ekstenzija
     */
    public static BKSPEkstenzije getEkstenzije(){
        return ext; 
    }
    
    /**
     * Postavljanje ekstenzije 
     * @param ex ekstenzija aplikacije
     */
    public static void setEkstenzije(BKSPEkstenzije ex){
        if(ext!=null) return; 
        ext = ex;
    }
    
    /**
     * Dobijanje niti za regulisanje ulaza aplikacije 
     * @return nit kojom se regulise ulaz
     */
    public static UlaznaNit getInputThread(){
        return inputThread; 
    }
    
    /**
     * Informacija da li je zatvorena aplikacija
     * @return data informacija
     */
    public static boolean isClosed(){
        return closed;
    }
    
    /**
     * Postavi status da izaslo
     */
    public static void setClosed(){
        closed=true; 
    }
    
    
    /**
     * Funkcija odjave
     */
    public static void odjava(){
        sesija = null;
        server.odijavaOut();
    }
    
    /**
     * Dobijanje identifikatora sesije 
     * @return sesija
     */
    public static String getSesija(){
        return sesija;
    }
    
    /**
     * Ulazna tacka u program
     * @param args argumenti 
     */
    public static void main(String ... args){
        if(server==null) System.out.println("Nema povezanosti sa serverom.");
        if(server==null) {
            inputThread.zaustavljanje();
            return;
        }
        
        do{menu(); 
        }while(branch());
        
        synchronized(Aplikacija.class){
           inputThread.zaustavljanje();
           if(!closed){
            if(sesija!=null) odjava();
            try{server.writeLine(BKSP_CLOSE); }
            catch(Exception ex){ex.printStackTrace();}
            try{server.izlaz(); }
            catch(Exception ex){ex.printStackTrace();}
            try{server.getServerCS().close();}
            catch(Exception ex){ex.printStackTrace();}
            try{server.getServerS().close();}
            catch(Exception ex){ex.printStackTrace();}
           }
        }
        
        System.out.println("Aplikacija zatvorena.");
    }
    
    /**
     * Glavni meni aplikacije
     */
    private static void menu(){
        System.out.println();
        System.out.println("BAZA_KORISNIKA.MENI");
        System.out.println("\t0. Izlaz");
        System.out.println("\t1. Prijava korisnika");
        System.out.println("\t2. Odjava korisnika");
        System.out.println("\t3. Registracija korisnika");
        System.out.println("\t4. Deregistracija korinika");
        System.out.println("\t5. Podaci o prijavljenom korisniku");
        if(ext!=null) ext.meni();
        System.out.println();
    }
    
    /**
     * Biranje opcije
     * @return izbor
     */
    private static boolean branch(){
        int izbor = -1;
        System.out.println("BAZA_KORISNIKA.IZBOR");
        System.out.print("\tBroj ispred opcije : ");
        try{ izbor = inputThread.readInteger(); }
        catch(Exception ex){izbor=-1;}
        
        if(closed) return false;
        
        System.out.println();
        System.out.println("BAZA_KORISNIKA.REZULTAT");
        
        switch(izbor){
            case 0:
                return false;
            case 1:
                prijavaKorisnika();
                return true;
            case 2:
                odijavaKorisnika();
                return true;
            case 3:
                registracijaKorisnika();
                return true;
            case 4:
                deregistracijaKorisnika(); 
                return true;
            case 5: 
                podaciKorisnika();
                return true;
            default:
                if(ext!=null){
                    boolean res = ext.run(izbor);
                    if(res) return true; 
                }
        }
        pogresanIzbor();
        return true;
    }
    
    /**
     * Funkcija prijave korisnika
     */
    private synchronized static void prijavaKorisnika(){
        System.out.println("\tPrijava korisnika");
        System.out.println();
        
        if(sesija!=null){
            System.out.println("Greska - postoji prijavljen klijent.");
            return;
        }
        
        PrijavaBean bean = new PrijavaBean(); 
        RegistracijaBean rbean = new RegistracijaBean();
        
        String username = ""; 
        String password = "";
        
        try {
            System.out.print("Korisnicko ime : ");
            username = inputThread.readLine();
            if(closed) return;
            System.out.print("Sifra : ");
            password = inputThread.readPassword();
            if(closed) return;
        } catch (InterruptedException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }

        bean.setUsername(username);
        bean.setPassword(password);
        
        prijavaBean = bean;
        
        try{
            server.prijavaOut(bean.asList());
            Pair<String,String> res = server.prijavaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                List<String> l = server.podaciIn().getValue();
                rbean.fromSignInList(l);
                sesija = res.getValue();
                System.out.println("Prijava uspjesna.");
                System.out.println("Sesija : "+sesija);
            }
            else {
                System.out.println("Greska\n"+res.getValue()); 
            }
        } catch (Exception ex){
            System.out.println("Nema povezanosti sa serverom.");
        }
    }
    

    /**
     *  Funkcija za odjavu korisnika
     */
    private synchronized static void odijavaKorisnika(){
        System.out.println("\tOdijava korisnika");
        System.out.println(); 
        if(sesija == null){
            System.out.println("Nema prijavljenog korisnika.");
            return;
        }
        System.out.println("Sesija : "+sesija);
        
        sesija = null;
        server.odijavaOut();
        
        prijavaBean = null;
        
        System.out.println("Odjava uspjesna.");
    }

    /**
     *  Funkcija za registraciju korisnika
     */
    private synchronized static void registracijaKorisnika(){
        System.out.println("\tRegistracija korisnika");
        System.out.println("\tPrijava korisnika");
        System.out.println();
        
        if(sesija!=null){
            System.out.println("Greska - postoji prijavljen klijent.");
            return;
        }
        
        PrijavaBean bean = new PrijavaBean();
        RegistracijaBean rbean = new RegistracijaBean();
        
        String username = ""; 
        String password = "";
        String firstname = ""; 
        String secondname = "";
        String address = ""; 
        String place = "";
        String job = ""; 
        String web = "";
        String email = ""; 
        String id = "";
        String telephone = "";
        
        try {
            if(closed) return;
            System.out.print("Ime : ");
            firstname = inputThread.readLine();
            if(closed) return;
            System.out.print("Prezime : ");
            secondname = inputThread.readLine(); 
            if(closed) return;
            System.out.print("Identifikator : ");
            id = inputThread.readLine();
            if(closed) return;
            System.out.print("Korisnicko ime : ");
            username = inputThread.readLine();
            if(closed) return;
            System.out.print("Sifra : ");
            password = inputThread.readPassword();
            if(closed) return;
            System.out.print("Adresa: ");
            address = inputThread.readLine();
            if(closed) return;
            System.out.print("Posao : ");
            email = inputThread.readLine(); 
            if(closed) return; 
            System.out.print("Telefon : ");
            telephone = inputThread.readLine();
            if(closed) return; 
            System.out.print("E-posta : ");
            job = inputThread.readLine(); 
            if(closed) return; 
            System.out.print("Veb : ");
            web = inputThread.readLine();
            if(closed) return; 
        } catch (InterruptedException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }

        bean.setUsername(username);
        bean.setPassword(password);
        
        rbean.setAddress(address);
        rbean.setEmail(email);
        rbean.setName(firstname);
        rbean.setSurname(secondname);
        rbean.setTelephone(telephone);
        rbean.setUsername(username);
        rbean.setWebs(web);
        rbean.setWorkplaces(place);
        rbean.setIdentificator(id);
        rbean.setWorkplaces(job);
        rbean.setPassword(password);
        
        prijavaBean = bean; 
        
        try{
            server.registracijaOut(rbean.asSignUpList());
            Pair<String,String> res = server.registracijaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                sesija = res.getValue();
                System.out.println("Registracija uspjesna.");
                System.out.println("Sesija : "+sesija);
            }
            else {
                System.out.println("Greska\n"+res.getValue()); 
            }
        } catch (Exception ex){
            System.out.println("Nema povezanosti sa serverom.");
        }
    }
    
    /**
     * Funkcija deregistracije korisnika
     */
    private synchronized static void deregistracijaKorisnika(){
        System.out.println("\tDeregistracija korisnika");
        if(sesija == null){
            System.out.println("\n\tNema prijavljenog korisnika.");
            return;
        }
       
        try{
            server.deregistracijaOut(prijavaBean.asList());
            String res = server.deregistracijaIn();
            if(res.equals(ProtokolBKSP.BKSP_SUCCESS)){
                System.out.println("Deregistracija uspjesna.");
                System.out.println("Sesija : "+sesija);
                sesija = null;
                prijavaBean = null;
            }
            else {
                System.out.println("Greska\n"+res); 
            }
        } catch (Exception ex){
            System.out.println("Nema povezanosti sa serverom.");
        }
    }
    
    /**
     * Funkcija u slucaju pogresnog izbora 
     */
    private synchronized static void pogresanIzbor(){
        System.out.println("\tPogresan izbor"); 
    }
    
    /**
     * Funkcija kojom se ocituju podaci korisnika
     */
    private synchronized static void podaciKorisnika(){
        System.out.println("\tPodaci korisnika"); 
        try { 
            System.out.println(server.podaciIn().getValue());
        } catch (IOException ex) {
            Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
