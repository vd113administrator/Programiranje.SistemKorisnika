/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.alati;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import programiranje.podaci_korisnika.alatke.SRZSerializer;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 *
 * @author Mikec
 */
public final class RegistrovanjeIsporuka {
    private RegistrovanjeIsporuka(){}
    private static String filename = "Isporuke.srz"; 
    
    public synchronized static HashMap<Pair<String,Date>,String> ocitavanjeIsporuke(){
        try{
            FileUnit funit = new FileUnit(new File(filename)).open();
            return (HashMap<Pair<String,Date>,String>) SRZSerializer.unmarshal(funit.getContent());
        }catch(Exception ex){
            return new HashMap<Pair<String,Date>,String>();
        }
    } 
    
    public synchronized static boolean dodajIsporuku(String primaoc, Date datum, String imePoruke) throws IOException{
        try{
            HashMap<Pair<String,Date>,String> m = ocitavanjeIsporuke(); 
            m.put(new Pair<>(primaoc, datum), imePoruke); 
            new FileUnit(new File(filename)).setContent(SRZSerializer.marshall(m)).save();
            return true;
        }catch(Exception ex){
            return false; 
        }
    }
}
