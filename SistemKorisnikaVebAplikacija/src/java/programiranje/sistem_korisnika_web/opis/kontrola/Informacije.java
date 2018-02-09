/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.opis.kontrola;

import java.io.IOException;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.sistem_korisnika.grupe.veb.ulaz_izlaz.SKServerAdapter;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;

/**
 * Mjesto za informacije
 * @author Mikec
 */
public class Informacije {
    private AdapterServera serverBK;
    private SKServerAdapter serverSK; 
    
    /**
     * Konstruktor
     */
    public Informacije(){
        serverBK = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter(); 
        if(serverBK!=null) serverSK = serverBK.getSKServerAdapter();
    }
    
    /**
     * Dobijanje konekcijskog kljuca
     * @return konekcijski kljuc 
     */
    public String getKonekcijskiId(){
        if(serverBK==null) return null;
        if(serverSK==null) return null;
        return serverBK.getSesijskiObjekti().getConnectionId();
    }
    
    /**
     * Dobijanje konekcijskog kljuca
     * @return konekcijski kljuc
     */
    public String getSesijskiId(){
        if(serverBK==null) return null;
        if(serverSK==null) return null;
        return serverBK.getSesijskiObjekti().getSessionId();
    }
    
    /**
     * Dobijanje prijavljenog korisnika
     * @return prijavljeni korisnik 
     * @throws IOException 
     */
    public String getPrijavljeniKorisnik() throws IOException{
        if(serverBK==null) return null;
        if(serverSK==null) return null;
        return serverBK.getSesijskiObjekti().getLoggedInUsername(); 
    }
    
    /**
     * Dobijanje BK komunikacije
     * @return adapter
     */
    public AdapterServera getServerBK(){
        return serverBK; 
    }
    
    /**
     * Dobijanje SK komunikacije
     * @return adapter
     */
    public SKServerAdapter getServerSK(){
        return serverSK; 
    }
    
    /**
     * Dobijanje ciljne grupe
     * @return ciljna grupa
     */
    public String getCiljnaGrupa(){
        try{
            return SKGlobalneFunkcije.getImeCiljneGrupe();
        }catch(Exception ex){
            return null;
        }
    }
    
    /**
     * Dobijanje ciljnog korisnika
     * @return ciljni korisnik
     */
    public String getCiljniKorisnik(){
        try{
            return SKGlobalneFunkcije.getImeCiljnogKorisnika();
        }catch(Exception ex){
            return null;
        }
    }
    
    /**
     * Dobijanje ciljnog administratora 
     * @return informacija iz JSF konteksta
     */
    public String getCiljniAdmin(){
        try{
            return SKGlobalneFunkcije.getImeCiljnogAdministratora();
        }catch(Exception ex){
            return null;
        }
    }
    
    /**
     * Informacija o prijavljenom korisniku 
     * @return informacija iz JSF konteksta 
     * @throws IOException 
     */
    public boolean prijavljenAdmin() throws IOException{
        String a1 = this.getPrijavljeniKorisnik(); 
        String a2 = this.getCiljniAdmin();
        if(a1==null) return false;
        if(a2==null) return false;
        return a1.equals(a2);
    }
    
    /**
     * Prijavljeni korisnik
     * @return informacija
     * @throws IOException 
     */
    public boolean prijavljenKorisnik() throws IOException{
        String a1 = this.getPrijavljeniKorisnik(); 
        String a2 = this.getCiljniKorisnik();
        if(a1==null) return false;
        if(a2==null) return false;
        return a1.equals(a2);
    }
    
    /**
     * Provjera da li je ciljni administrator prijavljeni korisnik
     * @return informacija
     * @throws IOException 
     */
    public boolean administratorKorisnik() throws IOException{
        String a1 = this.getCiljniAdmin(); 
        String a2 = this.getCiljniKorisnik();
        if(a1==null) return false;
        if(a2==null) return false;
        return a1.equals(a2);
    }
    
    /**
     * Provjera da li je ikoji korisnik prijavljen
     * @return informacija
     */
    public boolean imaPrijavljenKorisnik(){
        try{
            return this.getPrijavljeniKorisnik()!=null;
        }catch(Exception ex){
            return false;
        }
    } 
}
