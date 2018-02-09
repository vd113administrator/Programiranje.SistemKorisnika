/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.sertifikacija;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import programiranje.podaci_korisnika.alatke.SelekcijaDatoteka;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;

/**
 *
 * @author Mikec
 */
public class PretrazivacSertifikata {
    private File direktorijum;
    public PretrazivacSertifikata(File dir){
        direktorijum = dir; 
    }
    public List<File> getCerts(){
        List<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        files = SelekcijaDatoteka.selectBySufix(direktorijum, ".cer");
        return files; 
    }
    public List<File> getJKSs(){
        List<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        files =SelekcijaDatoteka.selectBySufix(direktorijum, ".jks");
        return files; 
    }
    
    public List<File> getCerts(String prefix){
        List<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        files = SelekcijaDatoteka.selectByPrefixAndSufix(direktorijum,prefix, ".cer");
        return files; 
    }
    
    public List<File> getJKSs(String prefix){
        List<File> files = new ArrayList<>();
        if(direktorijum==null) return files;
        if(!direktorijum.exists()) return files;
        if(!direktorijum.isDirectory()) return files;
        files = SelekcijaDatoteka.selectByPrefixAndSufix(direktorijum,prefix, ".jks");
        return files;
    }
}
