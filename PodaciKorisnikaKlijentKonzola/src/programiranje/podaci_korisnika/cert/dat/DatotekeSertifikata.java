/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.cert.dat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import programiranje.podaci_korisnika.konfig.PodaciKorisnikaAktivniDirektorijumi;
import programiranje.podaci_korisnika.sertifikacija.ImenovanjeSertifikata;
import programiranje.podaci_korisnika.sertifikacija.PretrazivacSertifikata;

/**
 *
 * @author Mikec
 */
public class DatotekeSertifikata {
    private PretrazivacSertifikata ps;
    private ImenovanjeSertifikata imsJKS;
    private ImenovanjeSertifikata imsCER;
    private String user; 
    private List<File> cers = new ArrayList<>(); 
    private List<File> jkss = new ArrayList<>();
    
    public DatotekeSertifikata(String username){
        user = username;
        imsJKS = new ImenovanjeSertifikata(user, ImenovanjeSertifikata.TipSertifikata.jks);
        imsCER = new ImenovanjeSertifikata(user, ImenovanjeSertifikata.TipSertifikata.cer);
        ps = new PretrazivacSertifikata(PodaciKorisnikaAktivniDirektorijumi.getSertifikati());
        prolazak();
    }

    public PretrazivacSertifikata getPs() {
        return ps;
    }

    public ImenovanjeSertifikata getImsJKS() {
        return imsJKS;
    }

    public ImenovanjeSertifikata getImsCER() {
        return imsCER;
    }

    public String getUser() {
        return user;
    }

    public List<File> getCers() {
        return new ArrayList<File>(cers);
    }

    public List<File> getJkss() {
        return new ArrayList<File>(jkss);
    }
    
    public DatotekeSertifikata prolazak(){
        cers.clear();
        jkss.clear();
        for(File f: ps.getCerts(user)){
            String cerName = f.getName();
            String [] parts = cerName.split("-");
            String jksName = parts[0]+"-privatni_sertifikat.jks";
            File f2 = new File(f.getParent(),jksName);
            if(f.exists()) cers.add(f);
            if(f2.exists()) jkss.add(f2);
        }
        Collections.sort(cers, (a,b)->{ return -a.getName().compareTo(b.getName());});
        Collections.sort(jkss, (a,b)->{ return -a.getName().compareTo(b.getName());});
        return this;
    }
    
    public boolean imaSertifikat(){
        return cers.size() != 0;
    }
    
    public boolean imaPrivatniKljuc(){
        return jkss.size() != 0; 
    }
    
    public File zadnjiCerSertifikat(){
        if(!imaSertifikat()) return null;
        return cers.get(0); 
    }
    
    public File zadnjiJksSertifikat(){
        if(!imaPrivatniKljuc()) return null;
        return jkss.get(0); 
    }
}
