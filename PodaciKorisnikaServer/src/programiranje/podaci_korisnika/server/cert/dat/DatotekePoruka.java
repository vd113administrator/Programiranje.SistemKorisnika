/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.cert.dat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import programiranje.podaci_korisnika.server.konfig.PodaciKorisnikaAktivniDirektorijumi;
import programiranje.podaci_korisnika.server.tekst.FilenameExtension;
import programiranje.podaci_korisnika.ui.FileTransporter;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 *
 * @author Mikec
 */
public class DatotekePoruka {
    private String primalac;
    private File direktorijum; 
    public DatotekePoruka(String primalac){
        this.primalac = primalac;
        this.direktorijum = PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama();
    }
    
    public List<File> getAll(){
        ArrayList<File> datoteke = new ArrayList<>();
        for(File file: direktorijum.listFiles()){
            datoteke.add(file);
        }
        return datoteke; 
    }
    
    public List<File> getForUser(){
        ArrayList<File> datoteke = new ArrayList<>();
        for(File file: direktorijum.listFiles()){
            String[] msg = file.getName().split("-"); 
            if(msg.length>1 && msg[1].equals(this.primalac)){
                datoteke.add(file);
            }
        }
        return datoteke; 
    }
    
    public FileTransporter getFTInboxForUser() throws IOException{
        FileTransporter ft = new FileTransporter();
        for(File f: getForUser()){
            f = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(),f.getName());
            String ftname = f.getName();
            FilenameExtension fnex= new FilenameExtension(ftname);
            String name = fnex.getFilename()+"-inbox"+fnex.getExtension();
            FileUnit pom = new FileUnit(f).open().erase();
            ft.files.put(f.getName(),new FileUnit(new File(name)).setContent(pom.getContent()));
        }
        return ft; 
    }
    
    public FileUnit getFUnitInbox(File name) throws IOException{
        if(!getAll().contains(name)) return null;
        String ftname = name.getName();
        String [] parts = ftname.split("."); 
        FileUnit fu = new FileUnit(name).open();
        FilenameExtension filex = new FilenameExtension(fu.getFile().getName());
        return new FileUnit(new File(filex.getFilename()+"-inbox"+filex.getExtension()),fu.getContent());
    }
}
