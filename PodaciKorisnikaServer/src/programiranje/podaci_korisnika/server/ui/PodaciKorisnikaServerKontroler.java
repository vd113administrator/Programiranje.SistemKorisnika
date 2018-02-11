/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.AsimetricniAlgoritam;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.pki.CertificateManager;
import programiranje.baza_korisnika.cert.pki.X509Manager;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.tech.TackaDigitalneEnvelope;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.model.Sesija;
import programiranje.podaci_korisnika.alatke.Base64Swapper;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.konfiguracija.KonstanteSertifikataServera;
import programiranje.podaci_korisnika.sertifikacija.ImenovanjeSertifikata;
import programiranje.podaci_korisnika.sertifikacija.PretrazivacSertifikata;
import programiranje.podaci_korisnika.server.alati.RegistrovanjeIsporuka;
import programiranje.podaci_korisnika.server.baza_podataka.PodaciKorisnikaAdapterBaze;
import programiranje.podaci_korisnika.server.cert.dat.DatotekePoruka;
import programiranje.podaci_korisnika.server.cert.dat.DatotekeSertifikata;
import programiranje.podaci_korisnika.server.konfig.PodaciKorisnikaAktivniDirektorijumi;
import programiranje.podaci_korisnika.server.model.RezultatListanjaKorisnika;
import programiranje.podaci_korisnika.server.model.RezultatPodaciKorisnika;
import programiranje.podaci_korisnika.server.sabloni.kalup.IzvrsneDatotekeSertifikovanja;
import programiranje.podaci_korisnika.server.sabloni.kalup.SablonskiUzorciNaredbiSertifikovanja;
import programiranje.podaci_korisnika.server.standard.CertificationConstants;
import programiranje.podaci_korisnika.standard.NaredbeProtokolaPROPIS;
import programiranje.podaci_korisnika.ui.FileTransporter;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 * Kontrolne funkcije za funkcionisanje servera koji korisnte adapter 
 * @author Mikec
 */
public class PodaciKorisnikaServerKontroler {
    private PodaciKorisnikaServerAdapter server; 
    private PodaciKorisnikaAdapterBaze base; 
    /**
     * Konstruktor
     * @param adapter komunikacija na nivou sistema korisnika
     * @param dbadapter  komunikacija prema bazi podataka 
     */
    public PodaciKorisnikaServerKontroler(PodaciKorisnikaServerAdapter adapter, 
                                          UserDatabaseAdapter dbadapter){
        server = adapter;
        base = new PodaciKorisnikaAdapterBaze(dbadapter); 
    }
    
    /**
     * Konstruktor
     * @param adapter komunikacija na nivou baze korisnika 
     * @param dbadapter  komunikacija prema bazi
     */
    public PodaciKorisnikaServerKontroler(ServerDataAdapter adapter, 
                                          UserDatabaseAdapter dbadapter){
        server = new PodaciKorisnikaServerAdapter(adapter);
        base = new PodaciKorisnikaAdapterBaze(dbadapter);
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za klijenta na nivou sistema korisnika
     * @return adapter 
     */
    public PodaciKorisnikaServerAdapter getSKServerGrupaAdapter(){
        return server;
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za klijenta na nivou baze korisnika
     * @return adapter 
     */
    public ServerDataAdapter getBKServerShellAdapter(){
        return server.getBKShellServerAdapter(); 
    }
    
    /**
     * Dobijanje komunikacijskog adaptera za bazu podataka na nivou sistema korisnika
     * @return adapter 
     */
    public PodaciKorisnikaAdapterBaze getPKAdapterDatabase(){
        return base;
    }
    
    /**
     * Aktivnost listanja grupe korisnika
     */
    public void listaKorisnika(){
        try {
            System.out.println();
            System.out.println(">><< OSNOVNI PODACI KORISNIKA >><<");
            System.out.println(">><< PODACI O KORISNICIMA >><<");
            System.out.println(">><< "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA+" >><<");
            System.out.println();
            RezultatListanjaKorisnika rk = new RezultatListanjaKorisnika(base.listaKorisnika()); 
            server.toClientListanjeKorisnika(rk);
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    /**
     * Aktivnost listanja podataka korisnika 
     */
    public void podaciKorisnika(){
        try {
            System.out.println(">><< OSNOVNI PODACI KORISNIKA >><<");
            System.out.println(">><< PODACI O KORISNICIMA >><<");
            System.out.println(">><< "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_PODACIMA+" >><<");
            String admin = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija().getAutentifikacija().getUsername();
            RezultatPodaciKorisnika rbean = base.podaciOKorisniku(admin);
            server.toClientPodaciKorisnika(rbean);
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaVlastitimSertifikatom(){
        try {
            System.out.println();
            System.out.println("[::] OSNOVNI PODACI KORISNIKA [::]");
            System.out.println("[::] SERTIFIKOVANJE [::]");
            System.out.println("[::] "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_SERTIFIKATOM+" [::]");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
            
            RezultatPodaciKorisnika rbean = base.podaciOKorisniku(admin);
            
            SablonskiUzorciNaredbiSertifikovanja sadrzaj = new SablonskiUzorciNaredbiSertifikovanja();
            IzvrsneDatotekeSertifikovanja skripte = new IzvrsneDatotekeSertifikovanja(sadrzaj);
            sadrzaj.setUsernameValue(admin).setCertnameValue();
            sadrzaj.setImeIPrezimeValue(rbean.getName(), rbean.getSurname());
            sadrzaj.setLocationValue(rbean.getAddress());
            sadrzaj.setOrganizationValue(rbean.getEmail()); 
                       
            DatotekeSertifikata serts = new DatotekeSertifikata(admin);
            FileTransporter sertifikacioniPaket = new FileTransporter(); 
            
            File cer = serts.zadnjiCerSertifikat(); 
            File jks = serts.zadnjiJksSertifikat();
            File scer = new File(PodaciKorisnikaAktivniDirektorijumi.getSertifikati(),
            KonstanteSertifikataServera.serverPotpisaniSertifikat);
            
            sertifikacioniPaket.files.put("subjectcer", new FileUnit(cer));
            sertifikacioniPaket.files.put("subjectjks", new FileUnit(jks));
            sertifikacioniPaket.files.put("issunercer", new FileUnit(scer));
            
            boolean sertifikovan = serts.prolazak().imaSertifikat();
            
            if(sertifikovan){
                sertifikacioniPaket.openAll();
                server.toClientPodaciKorisnika(sertifikovan,sertifikacioniPaket);
                skripte.brisanjeDatotekeZaLinux();
                skripte.brisanjeDatotekeZaWindows();
            }else{
                server.toClientPodaciKorisnika(sertifikovan,null);
                skripte.kreiranjeDatotekeZaWindows();
                skripte.kreiranjeDatotekeZaLinux();
            }
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void podaciDrugogKorisnika(){
        try {
            System.out.println(">><< OSNOVNI PODACI KORISNIKA >><<");
            System.out.println(">><< PODACI O KORISNICIMA >><<");
            System.out.println(">><< "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_PODACIMA+" >><<");
            String admin = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija().getAutentifikacija().getUsername();
            RezultatPodaciKorisnika rbean = base.podaciOKorisniku(server.fromClientPodaciKorisnika());
            server.toClientPodaciKorisnika(rbean);
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaSertifikatomKorisnika(){
        try{
            System.out.println();
            System.out.println("[::] OSNOVNI PODACI KORISNIKA [::]");
            System.out.println("[::] SERTIFIKOVANJE [::]");
            System.out.println("[::] "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_SERTIFIKATOM+" [::]");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
            
            
            DatotekeSertifikata serts = new DatotekeSertifikata(server.fromClientPodaciKorisnika());
            FileTransporter sertifikacioniPaket = new FileTransporter(); 
            
            File cer = serts.zadnjiCerSertifikat(); 
            sertifikacioniPaket.files.put("subjectcer", new FileUnit(cer));
            boolean sertifikovan = serts.prolazak().imaSertifikat();
            
            if(sertifikovan){
                sertifikacioniPaket.openAll();
                server.toClientPodaciKorisnika(sertifikovan,sertifikacioniPaket);
            }else{
                server.toClientPodaciKorisnika(sertifikovan,null);
            }
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void informacijaOBrojuSertifikata(){
        try{
            System.out.println();
            System.out.println("[::] OSNOVNI PODACI KORISNIKA [::]");
            System.out.println("[::] SERTIFIKOVANJE [::]");
            System.out.println("[::] "+NaredbeProtokolaPROPIS.PROPIS_INFORMACIJA_O_BROJU_SERTIFIKATA+" [::]");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
            
            RezultatPodaciKorisnika rbean = base.podaciOKorisniku(server.fromClientPodaciKorisnika());
            
            DatotekeSertifikata serts = new DatotekeSertifikata(admin);
            server.getBKShellServerAdapter().writeLine(Integer.toString(serts.getPs().getCerts().size()));
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void listanjeSertifikovanihKorisnika(){
        try{
            System.out.println();
            System.out.println("[::] OSNOVNI PODACI KORISNIKA [::]");
            System.out.println("[::] SERTIFIKOVANJE [::]");
            System.out.println("[::] "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA_SERTIFIKOVANIH_KORISNIKA+" [::]");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
            DatotekeSertifikata serts = new DatotekeSertifikata(admin);
            ArrayList<String> users = new ArrayList<String>();
            for(File file: serts.getPs().getCerts())
                users.add(file.getName().split("-")[0]);
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode(users));
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listanjeImenaSetifikata(){
        try{
            System.out.println();
            System.out.println("[::] OSNOVNI PODACI KORISNIKA [::]");
            System.out.println("[::] SERTIFIKOVANJE [::]");
            System.out.println("[::] "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_SERTIFIKATA_KORISNIKA+" [::]");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
      
            DatotekeSertifikata serts = new DatotekeSertifikata(admin);
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode((Serializable)serts.getPs().getCerts()));
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void postavljanjePorukeUSlici(){
        try {
            System.out.println();
            System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_POSTAVLJANJE_PORUKE_U_SLICI+" <<<<");
            System.out.println();
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija();
            String admin = korisnik.getAutentifikacija().getUsername();
            String podaci = server.getBKShellServerAdapter().readLine();
            FileTransporter ftrans = (FileTransporter) Base64Swapper.decodeToSerializable(podaci);
            File info = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(),
                    ftrans.files.get("msg").getFile().getName());
            DatotekeSertifikata ds = new DatotekeSertifikata(admin);
            File fcert = ds.zadnjiCerSertifikat();
            new FileUnit(info,ftrans.files.get("msg").getContent()).save();
            if(fcert == null){
                info.delete();
                server.getBKShellServerAdapter().writeLine("false");
                server.getBKShellServerAdapter().writeLine("Server ne poznaje za sertifikat korisnika.");
                return; 
            }
            if(!info.getName().startsWith(admin+"-") || info.getName().split("-").length<2){
                server.getBKShellServerAdapter().writeLine("false");
                server.getBKShellServerAdapter().writeLine("Nije dobar format imena.");
                return; 
            }
            server.getBKShellServerAdapter().writeLine("true");
            server.getBKShellServerAdapter().writeLine("");
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listanjePorukaUSlikama(){
        System.out.println();
        System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA+" <<<<");
        System.out.println();
        AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
        String admin = korisnik.getAutentifikacija().getUsername();
        
        DatotekePoruka dp = new DatotekePoruka(admin); 
        try {
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode((Serializable)dp.getAll()));
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listanjePorukaUSlikamaZaKorisnika(){
        System.out.println();
        System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA_ZA_KORISNIKA+" <<<<");
        System.out.println();
        AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
        String admin = korisnik.getAutentifikacija().getUsername();
        
        DatotekePoruka dp = new DatotekePoruka(admin); 
        try {
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode((Serializable)dp.getForUser()));
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void preizimanjeSvihSlikaSPorukamaZaKorisnika(){
        System.out.println();
        System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_PREUZIMANJE_SVIH_SLIKA_S_PORUKAMA_ZA_KORISNIKA+" <<<<");
        System.out.println(); 
        AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
        String admin = korisnik.getAutentifikacija().getUsername();
        DatotekePoruka dp = new DatotekePoruka(admin);
        try {
            FileTransporter ft = dp.getFTInboxForUser();
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode((Serializable)ft));
            Date date = new Date();
            for(FileUnit f: ft.files.values()){
                RegistrovanjeIsporuka.dodajIsporuku(admin, date, f.getFile().getName());
            }
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void preuzimanjeCiljaneSlikeSPorukom(){
        System.out.println();
        System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_PORUKOM_U_SLICI+" <<<<");
        System.out.println();
        AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija();
        String admin = korisnik.getAutentifikacija().getUsername();
        DatotekePoruka dp = new DatotekePoruka(admin);
        try {
            String line = server.getBKShellServerAdapter().readLine();
            File file = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(),line.trim());
            if(!file.exists()) {
                server.getBKShellServerAdapter().writeLine("false"); return;
            }
            server.getBKShellServerAdapter().writeLine("true");
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode((Serializable)dp.getFUnitInbox(file)));
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaMapomIsporuka(){
        try {
            System.out.println();
            System.out.println(">>>> OSNOVNI PODACI KORISNIKA <<<<");
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_SPISKOM_ISPORUKA+" <<<<");
            System.out.println();
            server.getBKShellServerAdapter().writeLine(Base64Swapper.encode(RegistrovanjeIsporuka.ocitavanjeIsporuke()));
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaPovlacenjemSertifikata(){
        try {
            System.out.println();
            System.out.println("<< OSNOVNI PODACI KORISNIKA >>");
            System.out.println("<< OSTALE FUNKCIONOLNOSTI >>");
            System.out.println("<< "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_POVLACENJEM_SERTIFIKATA+" >>");
            System.out.println();
            
            Sesija s = server.getBKShellServerAdapter().getSesija();
            if(s==null) return; 
            Korisnik k = s.getKorisnik();
            if(k==null) return; 
            
            AutorizacioniPodaci korisnik = server.getBKShellServerAdapter().getSesija().getKorisnik().getAutorizacija(); 
            String admin = korisnik.getAutentifikacija().getUsername();
            
            RezultatPodaciKorisnika rbean = base.podaciOKorisniku(admin);
            
            SablonskiUzorciNaredbiSertifikovanja sadrzaj = new SablonskiUzorciNaredbiSertifikovanja();
            IzvrsneDatotekeSertifikovanja skripte = new IzvrsneDatotekeSertifikovanja(sadrzaj);
            sadrzaj.setUsernameValue(admin).setCertnameValue();
            sadrzaj.setImeIPrezimeValue(rbean.getName(), rbean.getSurname());
            sadrzaj.setLocationValue(rbean.getAddress());
            sadrzaj.setOrganizationValue(rbean.getEmail()); 
            
            
            DatotekeSertifikata serts = new DatotekeSertifikata(admin);
            FileTransporter sertifikacioniPaket = new FileTransporter(); 
            
            File cer = serts.zadnjiCerSertifikat(); 
            File jks = serts.zadnjiJksSertifikat();
            File scer = new File(PodaciKorisnikaAktivniDirektorijumi.getSertifikati(),
            KonstanteSertifikataServera.serverPotpisaniSertifikat);
            
            
            sertifikacioniPaket.files.put("subjectcer", new FileUnit(cer));
            sertifikacioniPaket.files.put("subjectjks", new FileUnit(jks));
            sertifikacioniPaket.files.put("issunercer", new FileUnit(scer));
            
            boolean sertifikovan = serts.prolazak().imaSertifikat();
            
            if(sertifikovan){
                String [] strs = cer.getName().split("-");
                sadrzaj.setCertnameValue(strs[0]+"-"+strs[1]);
                skripte.kreiranjeDatotekePovlacenjaZaWindows();
                skripte.kreiranjeDatotekePovlacenjaZaLinux();
            }else{
                skripte.brisanjeDatotekePovlacenjaZaLinux();
                skripte.brisanjeDatotekePovlacenjaZaWindows();
            }
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaServerKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaCRLListom(){
        System.out.println();
        System.out.println("<< OSNOVNI PODACI KORISNIKA >>");
        System.out.println("<< OSTALE FUNKCIONOLNOSTI >>");
        System.out.println("<< "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_CRL_LISTOM+" >>");
        System.out.println();
        File file = new File(KonstanteDirektorijuma.arhivapovucenikcertifikata+File.separator,"sertifikacija.der.crl");
        try{
            FileUnit crl = new FileUnit(file).openIfExists();
            String str = Base64Swapper.encode(crl.getContent());
            server.getBKShellServerAdapter().writeLine("true");
            server.getBKShellServerAdapter().writeLine(str);
        }catch(Exception ex){
            server.getBKShellServerAdapter().writeLine("false");
        }
    }
}
