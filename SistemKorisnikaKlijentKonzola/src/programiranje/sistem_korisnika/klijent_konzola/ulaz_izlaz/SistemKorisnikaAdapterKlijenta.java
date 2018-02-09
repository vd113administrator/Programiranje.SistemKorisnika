/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.klijent_konzola.ulaz_izlaz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_console.klijent.io.AdapterServera;
import programiranje.baza_korisnika_console.klijent.io.UlaznaNit;
import programiranje.baza_korisnika_console.model.PrijavaBean;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGP;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati;
import programiranje.sistem_korisnika.klijent_konzola.SistemKorisnikaKlijentKonzola;

/**
 * Komunikacija i funkcionalnosti prema serveru 
 * @author Mikec
 */
public class SistemKorisnikaAdapterKlijenta{
    private AdapterServera client;
    private UlaznaNit ulaz;
    
    /**
     * Konstruktor 
     * @param klijent komunikacija ka serveru BK
     * @param input nit ocitavanja standardnog ulaza 
     */
    public SistemKorisnikaAdapterKlijenta(AdapterServera klijent, UlaznaNit input){
        client = klijent;
        ulaz = input; 
    }
    
    /**
     * Dobijanje adapterea BK klijenta 
     * @return klijent
     */
    public AdapterServera getSKServerAdapter(){
        return client;
    }
    
    /**
     * Aktivnost i komunikacija prijavljivanja grupe
     */
    public void pravljenjeGrupe(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PRAVLJENJE_GRUPE >> "+ok); 
        client.writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_PRAVLJENJE_GRUPE.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        System.out.println("Sesija "+ok+": korisnicko ime administratora >> "+administrator);
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine(); 
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> uspjesno pravljenje grupe");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacija brisanja grupe
     */
    public void brisanjeGrupe(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:BRISANJE_GRUPE");
        client.writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_BRISANJE_GRUPE.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        System.out.println("Sesija "+ok+": korisnicko ime administratora >> "+administrator);
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine(); 
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> uspjesno brisnaje grupe");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacija pri uclanjenju u grupu 
     */
    public void uclanjenje(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:UCLANJENJE");
        client.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_AKTIVNOST_UCLANJENJE.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+administrator);
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine(); 
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> uspjesno uclanjenje u grupu");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacija isclanjenja grupe
     */
    public void isclanjenje(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:ISCLANJENJE");
        client.writeLine(ProtokolSKGP.SKGP_1_CLAN_AKTIVNOST_ISCLANJENJE.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+administrator);
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine(); 
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> uspjesno isclanjenje iz grupe");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacija iskljucenja 
     */
    public void iskljucenje(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:ISKLJUCENJE");
        client.writeLine(ProtokolSKGP.SKGP_1_ADMIN_AKTIVNOST_ISKLJUCIVANJE.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        String korisnik = null; 
        System.out.println("Sesija "+ok+": korisnicko ime administratora >> "+administrator);
        System.out.print("Sesija "+ok+": korisnicko ime korisnika >> ");
        try{korisnik = ulaz.readLine();}
        catch(Exception ex){}
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(korisnik);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine(); 
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> uspjesno iskljucenje iz grupe");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacije listanja clanstva grupe
     */
    public void listaClanova(){
        ArrayList<String> grupe = new ArrayList<>();
        try {
            String ok = SistemKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:LISTA_CLANOVA");
            if(ok==null) return;
            System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+admin.getUsername());
            client.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_PRIPADNOSTI.toString());
            client.writeLine(admin.getUsername());
            int ngrupa = Integer.parseInt(client.readLine());
            for(int i=0; i<ngrupa; i++)
                grupe.add(client.readLine()); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(grupe);
        return; 
    }
    
    /**
     * Aktivnost i komunikacija listanja grupe po vlasnistvu 
     */
    public void listaVlasnistva(){
        ArrayList<String> grupe = new ArrayList<>();
        try {
            String ok = SistemKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:LISTA_VLASNISTVA");
            if(ok==null) return; 
            System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+admin.getUsername());
            client.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_LISTA_VLASNISTVA.toString());
            client.writeLine(admin.getUsername());
            int ngrupa = Integer.parseInt(client.readLine());
            for(int i=0; i<ngrupa; i++)
                grupe.add(client.readLine()); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(grupe);
        return; 
    }
    
    /**
     * Aktivnost i komunikacija listanja korisnika grupe
     */
    public void listaKorisnika(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PROVJERA_KORISNIKA");
        
        if(ok==null) return; 
        
        client.writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_KORISNIKA_GRUPE.toString());
        System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+admin.getUsername());    
        System.out.print("Sesija "+ok+": ime grupe >> "); 
       
        String grupa = null;
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        
        client.writeLine(admin.getUsername());
        client.writeLine(grupa);
        
        System.out.println();
        
        try{
            int n = Integer.parseInt(client.readLine());
            for(int i=0; i<n; i++)
                System.out.println("\t"+client.readLine()); 
        }catch(Exception ex){
        }
        
        System.out.println();
    }
    
    /**
     * Aktivnost i komunikacija  ocitavanja podataka grupe
     */
    public void podaciGrupe(){
        try{
            String ok = SistemKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PODACI_GRUPE");
            
            if(ok==null) return;
            
            client.writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_GRUPE.toString());
            System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+admin.getUsername());
            System.out.print("Sesija "+ok+": ime grupe >> ");
            
            String grupa = null;
            
            try{grupa = ulaz.readLine();}
            catch(Exception ex){}
            
            client.writeLine(admin.getUsername());
            client.writeLine(grupa);
            
            String admingrupe = client.readLine();
            String imegrupe = client.readLine();
            String idgrupe = client.readLine();
            
            System.out.println("Administrator : "+admingrupe);
            System.out.println("Identifikator : "+idgrupe);
            System.out.println("Ime grupe : "+imegrupe);
        }
        catch(IOException ex){Logger.getLogger(SistemKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aktivnost dobijanja podataka o korisniku 
     */
    public void podaciKorisnika(){
        try{
            String ok = SistemKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PODACI_KORISNIKA");
            
            if(ok==null) return;
            
            client.writeLine(ProtokolSKGP.SKGP_1_CLAN_PROVJERA_PODACI_KORISNIKA_GRUPE.toString());
            
            System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+admin.getUsername());
            System.out.print("Sesija "+ok+": ime grupe >> ");
            
            String grupa = null;
            String korisnik = null;
            
            try{grupa = ulaz.readLine();}
            catch(Exception ex){}
            
            System.out.print("Sesija "+ok+": ime trazenog korisnika >> ");
            
            try{korisnik = ulaz.readLine();}
            catch(Exception ex){}
            
            
            client.writeLine(admin.getUsername());
            client.writeLine(grupa);
            client.writeLine(korisnik);
            
            System.out.println();
           

            System.out.println("Ime :"+client.readLine());
            System.out.println("Prezime :"+client.readLine());
            System.out.println("Korisnicko ime :"+client.readLine());
            System.out.println("Adresa :"+client.readLine());
            System.out.println("Identifikacija :"+client.readLine());
            
            System.out.println("Elektronska posta :"+client.readLine());
            System.out.println("Telefon :"+client.readLine());
            System.out.println("Radno mjesto :"+client.readLine());
            System.out.println("Veb sajtovi :"+client.readLine());
            System.out.println("Tip korisnika :"+client.readLine());
        }
        catch(IOException ex){
            Logger.getLogger(SistemKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *  Aktivnost imenovanja grupa
     */
    public void imenaGrupa(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:GRUPE"); 
        
        if(ok==null) return; 
        client.writeLine(ProtokolSKGP.SKGP_1_CLAN_IMENA_GRUPA.toString());
        
        System.out.println();
        
        try{
            int n = Integer.parseInt(client.readLine());
            for(int i=0; i<n; i++)
                System.out.println("\t"+client.readLine()); 
        }catch(Exception ex){
        }
        
        System.out.println();
    }
    
    /**
     * Aktivnost provjere clanstva u grupi
     */
    public void clanstvoUGrupama(){
        String ok = SistemKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = SistemKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        System.out.println("SISTEM_KORISNIKA.GRUPE.SERVER:PROVJERA_CLANSTVA");
        client.writeLine(ProtokolSKGP.SKGP_1_KORISNIK_PROVJERA_CLANSTVA_U_GRUPI.toString());
        if(ok==null) return;
        String administrator = admin.getUsername();
        String grupa = null;
        System.out.println("Sesija "+ok+": korisnicko ime korisnika >> "+administrator);
        System.out.print("Sesija "+ok+": ime korisnicke grupe >> ");
        try{grupa = ulaz.readLine();}
        catch(Exception ex){}
        client.writeLine(administrator);
        client.writeLine(grupa);
        System.out.println();
        try{
            String res = client.readLine();
            String error = client.readLine();
            if(res.equals(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString())){
                System.out.print("Sesija "+ok+">> korisnik je clan grupe");
            }else if(error.equals("")){
                System.out.print("Sesija "+ok+">> korisnik nije clan grupe");
            }else{
                System.out.println("Sesija "+ok+": greska >> "+error);
            }
        }catch(Exception ex){
        }
        System.out.println();
    }
}
