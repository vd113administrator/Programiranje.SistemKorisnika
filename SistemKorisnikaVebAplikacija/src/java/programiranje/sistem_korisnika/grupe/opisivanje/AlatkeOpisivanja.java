/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.opisivanje;

import java.io.File;
import java.util.Date;

/**
 * Alatke za upravljanje opisima kod korisnika, grupa i uloga
 * @author Mikec
 */
public class AlatkeOpisivanja {
    private AlatkeOpisivanja(){}
    
    /**
     * Brisanje samo datoteka opisa korisnika
     * @param korisnik korisnicko ime korisnika
     */
    public static void brisanjeKorisnika(String korisnik){
        File dir = new File("./data/opisi");
        for(File file : dir.listFiles())
            if(file.getName().startsWith("korisnik.") && file.getName().endsWith("."+korisnik+".html"))
                file.delete();
    }
    
    /**
     * Brisanje samo datoteka opisa grupe
     * @param grupa ime grupe 
     */
    public static void brisanjeGrupe(String grupa){
        File dir = new File("./data/opisi");
        for(File file : dir.listFiles())
                if(file.getName().startsWith("grupa.") && file.getName().endsWith("."+grupa+".html"))
                    file.delete();
    }
    
    /**
     * Brisanje samo datoteka sopstvenog opisa uloge korisnika u grupi  
     * @param korisnik korisnicko ime korisnika
     * @param grupa ime grupe
     */
    public static void brisanjeUlogeSopstveno(String korisnik, String grupa){
        File dir = new File("./data/opisi");
        for(File file : dir.listFiles())
            if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+korisnik+"}.{"+grupa+"}.clan.html"))
                file.delete();
    }
    
    /**
     * Brisanje samo datoteka administratorovog opisa uloge korisnikai u grupi 
     * @param korisnik korisnicko ime korisnika 
     * @param grupa ime grupe 
     */
    public void brisanjeUlogeAdministrator(String korisnik, String grupa){
        File dir = new File("./data/opisi");
        for(File file : dir.listFiles())
            if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+korisnik+"}.{"+grupa+"}.administrator.html"))
                dir.delete();
    }
    
    /**
     * Brisanje samo datoteka bilo kakvih opisa uloga korisnika u grupi  
     * @param korisnik korisnicko ime korisnika 
     * @param grupa  ime grupe 
     */
    public void brisanjeUloge(String korisnik, String grupa){
        brisanjeUlogeSopstveno(korisnik,grupa);
        brisanjeUlogeAdministrator(korisnik,grupa);
    }
    
    /**
     * Preimenovanje samo datoteka koje opisuju imena korisnika
     * @param korisnik staro korisnicko ime za korisnika 
     * @param novoKime novo korisnicko ime za korisnika
     */
    public void preimenovanjeKorisnika(String korisnik, String novoKime){
        File dir = new File("./data/opisi");
        Date date = new Date(); 
        for(File file : dir.listFiles())
            if(file.getName().startsWith("korisnik.") && file.getName().endsWith("."+korisnik+".html"))
                file.renameTo(new File("korisnik."+date+"."+novoKime+".html"));
    }
    
    /**
     * Preimenovanje samo datoteka koje opisuju prezimena korisnika
     * @param grupa ime grupe
     * @param novaGrupa novo ime grupe
     */
    public void preimenovanjeGrupe(String grupa, String novaGrupa){
        File dir = new File("./data/opisi");
        Date date = new Date(); 
        for(File file : dir.listFiles())
            if(file.getName().startsWith("grupa.") && file.getName().endsWith("."+grupa+".html"))
                file.renameTo(new File("grupa."+date+"."+novaGrupa+".html"));
    }
    
    /**
     * Preimenovanje samo datoteka koje adminstratorski opisuju ulogu korisnika u grupi 
     * @param staraGrupa stara ime grupe
     * @param stariKorisnik staro korisnicko ime korisnika
     * @param novaGrupa nova ime grupe
     * @param noviKorisnik novo korisnicko ime korisnika
     */
    public void preimenovanjeUlogeAdministratora(
            String staraGrupa, String stariKorisnik, 
            String novaGrupa, String noviKorisnik){
        File dir = new File("./data/opisi");
        Date date = new Date();
        for(File file : dir.listFiles())
            if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+stariKorisnik+"}.{"+staraGrupa+"}.administrator.html"))
                file.renameTo(new File("uloga."+date+".{"+noviKorisnik+"}.{"+novaGrupa+"}.administrator.html"));
    }
    
    /**
     * Preimenovanje samo datoteka koje sopstveno opisuju ulogu korisnika u grupi 
     * @param staraGrupa stara ime grupe
     * @param stariKorisnik staro korisnicko ime korisnika
     * @param novaGrupa nova ime grupe
     * @param noviKorisnik novo korisnicko ime korisnika
     */
    public void preimenovanjeUlogeSopstveno(
            String staraGrupa, String stariKorisnik, 
            String novaGrupa, String noviKorisnik){
        File dir = new File("./data/opisi");
        Date date = new Date();
        for(File file : dir.listFiles())
            if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+stariKorisnik+"}.{"+staraGrupa+"}.clan.html"))
                file.renameTo(new File("uloga."+date+".{"+noviKorisnik+"}.{"+novaGrupa+"}.clan.html"));
    }
    
    /**
     * Preiemnovanje samo datoteka bilo kakvih opisa uloge
     * @param staraGrupa stara ime grupe
     * @param stariKorisnik staro korisnicko ime korisnika
     * @param novaGrupa nova ime grupe
     * @param noviKorisnik novo korisnicko ime korisnika
     */
    public void preimenovanjeUloge(
            String staraGrupa, String stariKorisnik, 
            String novaGrupa, String noviKorisnik){
        preimenovanjeUlogeAdministratora(staraGrupa, stariKorisnik, novaGrupa, noviKorisnik);
    }
}
