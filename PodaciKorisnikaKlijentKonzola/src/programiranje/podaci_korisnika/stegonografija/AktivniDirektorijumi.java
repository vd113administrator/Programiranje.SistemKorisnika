/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.stegonografija;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import programiranje.podaci_korisnika.konfig.PodaciKorisnikaAktivniDirektorijumi;

/**
 *
 * @author Mikec
 */
public class AktivniDirektorijumi {
    public List<File> ocitajImenaSlika(){
        ArrayList<File> slike = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getSlikeBezPoruka(); 
        for(File file: dir.listFiles()){
            slike.add(file); 
        }
        return slike; 
    }
    
    public List<File> ocitajImenaSlikaSaPorukama(){
        ArrayList<File> slike = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(); 
        for(File file: dir.listFiles()){
            slike.add(file);
        }
        return slike; 
    }
    
    
    
    public List<File> ocitajImenaSertifikata(){
        ArrayList<File> sertifikati = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getSertifikati();
        for(File file: dir.listFiles()){
            sertifikati.add(file);
        }
        return sertifikati;
    } 
    
    public List<File> ocitajEnkriptovanePoruke(){
        ArrayList<File> poruke = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(); 
        for(File file: dir.listFiles()){
            if(file.getName().endsWith(".enc")) 
                poruke.add(file);
        }
        return poruke; 
    }
    
    
    public List<File> ocitajKontrolnePoruke(){
        ArrayList<File> poruke = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(); 
        for(File file: dir.listFiles()){
            if(file.getName().endsWith(".msg")) 
                poruke.add(file);
        }
        return poruke; 
    }
    
    public List<File> ocitajOtvorenePoruke(){
        ArrayList<File> poruke = new ArrayList<>();
        File dir = PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(); 
        for(File file: dir.listFiles()){
            if(file.getName().endsWith(".txt")) 
                poruke.add(file);
        }
        return poruke; 
    }
}
