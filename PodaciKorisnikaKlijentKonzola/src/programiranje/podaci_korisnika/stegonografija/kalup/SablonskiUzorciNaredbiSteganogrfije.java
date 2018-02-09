/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.stegonografija.kalup;

import java.util.HashMap;
import java.util.Scanner;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.konfiguracija.KonstanteSertifikataServera;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;

/**
 *
 * @author Mikec
 */
public class SablonskiUzorciNaredbiSteganogrfije {
    public final static String slika = "<slika>"; 
    public final static String sifra = "<sifra>"; 
    public final static String poruka = "<poruka>";
    public final static String podaci = "<podaci>"; 
    
    private String slikaValue = ""; 
    private String sifraValue = ""; 
    private String porukaValue = ""; 
    private String podaciValue = ""; 

    public String getSlikaValue() {
        return slikaValue;
    }

    public SablonskiUzorciNaredbiSteganogrfije setSlikaValue(String slikaValue) {
        this.slikaValue = slikaValue;
        return this; 
    }

    public String getSifraValue() {
        return sifraValue;
    }

    public SablonskiUzorciNaredbiSteganogrfije setSifraValue(String sifraValue) {
        this.sifraValue = sifraValue;
        return this; 
    }

    public String getPorukaValue() {
        return porukaValue;
    }

    public SablonskiUzorciNaredbiSteganogrfije setPorukaValue(String porukaValue) {
        this.porukaValue = porukaValue;
        return this; 
    }

    public String getPodaciValue() {
        return podaciValue;
    }

    public SablonskiUzorciNaredbiSteganogrfije setPodaciValue(String podaciValue) {
        this.podaciValue = podaciValue;
        return this; 
    }
    
    
    
    public String zamijenaUzorakaVrijednostima(String text){
        return text
                   .replaceAll(slika, slikaValue)
                   .replaceAll(sifra, sifraValue)
                   .replaceAll(podaci, podaciValue)
                   .replaceAll(poruka, porukaValue);
    }
    
    public String generateBatScriptSadrzajZaUgradnju(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/stegonografija/sabloni/poruka-ugradnja.bat.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateShellScriptSadrzajZaUgradnju(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/stegonografija/sabloni/poruka-ugradnja.sh.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateBatScriptSadrzajZaIzdvajanje(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/stegonografija/sabloni/poruka-ocitavanje.bat.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
    
    public String generateShellScriptSadrzajZaIzdvajanje(){
        String res = "";
        Scanner scan = new Scanner(getClass().getResourceAsStream("/programiranje/podaci_korisnika/stegonografija/sabloni/poruka-ocitavanje.sh.txt"));
        while(scan.hasNextLine()){
            res+=scan.nextLine()+"\n";
        }
        res = this.zamijenaUzorakaVrijednostima(res);
        scan.close();
        return res;
    }
}
