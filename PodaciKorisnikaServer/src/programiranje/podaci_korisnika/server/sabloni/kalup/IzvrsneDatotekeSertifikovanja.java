/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.sabloni.kalup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import programiranje.podaci_korisnika.server.konfig.PodaciKorisnikaAktivniDirektorijumi;

/**
 *
 * @author Mikec
 */
public class IzvrsneDatotekeSertifikovanja {
    private SablonskiUzorciNaredbiSertifikovanja sadrzaj; 

    public SablonskiUzorciNaredbiSertifikovanja getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(SablonskiUzorciNaredbiSertifikovanja sadrzaj) {
        this.sadrzaj = sadrzaj;
    }
    
    public IzvrsneDatotekeSertifikovanja(SablonskiUzorciNaredbiSertifikovanja uzorci){
        sadrzaj = uzorci; 
    }
    
    public String generisanjeImenaIzvrsnjeDatoteke(){
        return sadrzaj.getUsernameValue()+".sertifikacija"; 
    }
    
    public String generisanjeImenaIzvrsnjeDatotekeZaLinux(){
        return sadrzaj.getUsernameValue()+".sertifikacija.sh"; 
    }
    
    public String generisanjeImenaIzvrsnjeDatotekeZaWindows(){
        return sadrzaj.getUsernameValue()+".sertifikacija.bat"; 
    }
    
    public File generisanjeFileObjektaDatotekeZaLinux(){
        return new File(PodaciKorisnikaAktivniDirektorijumi.getSertifikacija(), generisanjeImenaIzvrsnjeDatotekeZaLinux());
    }
    
    public File generisanjeFileObjektaDatotekeZaWindows(){
        return new File(PodaciKorisnikaAktivniDirektorijumi.getSertifikacija(), generisanjeImenaIzvrsnjeDatotekeZaWindows());
    }
    
    public void kreiranjeDatotekeZaLinux() throws IOException{
        File file = generisanjeFileObjektaDatotekeZaLinux();
        if(!file.exists()) {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            pw.println(sadrzaj.generateShellScriptSadrzaj());
            pw.close();
        }else{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            pw.println(sadrzaj.generateShellScriptSadrzaj());
            pw.close();
        }
    }
    
    public void kreiranjeDatotekeZaWindows() throws IOException{
        File file = generisanjeFileObjektaDatotekeZaWindows();
        if(!file.exists()) {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            pw.println(sadrzaj.generateBatScriptSadrzaj());
            pw.close();
        }
        else{
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            pw.println(sadrzaj.generateBatScriptSadrzaj());
            pw.close();
        }
    }
    
    public void brisanjeDatotekeZaLinux() throws IOException{
        File file = generisanjeFileObjektaDatotekeZaLinux();
        if(file.exists()) file.delete(); 
    }
    
    public void brisanjeDatotekeZaWindows() throws IOException{
        File file = generisanjeFileObjektaDatotekeZaWindows();
        if(file.exists()) file.delete(); 
    }
}
