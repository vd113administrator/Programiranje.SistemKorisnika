/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.konzola.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import programiranje.baza_korisnika.cert.alg.AsimetricniAlgoritam;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.pki.CertificateManager;
import programiranje.baza_korisnika.cert.pki.PrivateKeyManager;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.tech.TackaDigitalneEnvelope;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_console.klijent.io.AdapterServera;
import programiranje.baza_korisnika_console.klijent.io.UlaznaNit;
import programiranje.baza_korisnika_console.model.PrijavaBean;
import programiranje.podaci_korisnika.alatke.Base64Swapper;
import programiranje.podaci_korisnika.cert.dat.DatotekeSertifikata;
import programiranje.podaci_korisnika.konfig.PodaciKorisnikaAktivniDirektorijumi;
import programiranje.podaci_korisnika.konfiguracija.KonstanteDirektorijuma;
import programiranje.podaci_korisnika.konfiguracija.KonstanteSertifikataServera;
import programiranje.podaci_korisnika.konzola.PodaciKorisnikaKlijentKonzola;
import programiranje.podaci_korisnika.standard.CertificationConstants;
import programiranje.podaci_korisnika.standard.NaredbeProtokolaPROPIS;
import programiranje.podaci_korisnika.stegonografija.AktivniDirektorijumi;
import programiranje.podaci_korisnika.stegonografija.ImenovanjaDatoteka;
import programiranje.podaci_korisnika.stegonografija.kalup.SablonskiUzorciNaredbiSteganogrfije;
import programiranje.podaci_korisnika.tekst.FormatiranjeDatuma;
import programiranje.podaci_korisnika.ui.FileTransporter;
import programiranje.podaci_korisnika.ui.FileUnit;

/**
 * Komunikacija i funkcionalnosti prema serveru 
 * @author Mikec
 */
public class PodaciKorisnikaAdapterKlijenta{
    private AdapterServera client;
    private UlaznaNit ulaz;
    
    /**
     * Konstruktor 
     * @param klijent komunikacija ka serveru BK
     * @param input nit ocitavanja standardnog ulaza 
     */
    public PodaciKorisnikaAdapterKlijenta(AdapterServera klijent, UlaznaNit input){
        client = klijent;
        ulaz = input; 
    }
    
    /**
     * Dobijanje adapterea BK klijenta 
     * @return klijent
     */
    public AdapterServera getSKServerAdapter(){
        return client;
    }
    
    /**
     * Aktivnost i komunikacija listanja korisnika grupe
     */
    public void listaKorisnika(){
        String ok = PodaciKorisnikaKlijentKonzola.getSesija();
        PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
        
        System.out.println(">><< KORISNICKI PODACI : "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA+" >><< "); 
        if(ok==null) return; 
        
        client.writeLine(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA.toString());
       
        
        System.out.println("Korisnicka imena : ");
        try{
            int n = Integer.parseInt(client.readLine());
            for(int i=0; i<n; i++)
                System.out.println("\t"+client.readLine()); 
        }catch(Exception ex){
        }
        
        System.out.println();
    }
    
    
    /**
     * Aktivnost dobijanja podataka o korisniku 
     */
    public void podaciKorisnika(){
        try{
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            System.out.println(">><< KORISNICKI PODACI : "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_PODACIMA + " >><< ");
            
            if(ok==null) return;
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_PODACIMA.toString());
            
            System.out.println("Podaci o prijavljenom korisniku :");
            
            System.out.println("\tIme :"+client.readLine());
            System.out.println("\tPrezime :"+client.readLine());
            System.out.println("\tKorisnicko ime :"+client.readLine());
            System.out.println("\tAdresa :"+client.readLine());
            System.out.println("\tIdentifikacija :"+client.readLine());
            
            System.out.println("\tElektronska posta :"+client.readLine());
            System.out.println("\tTelefon :"+client.readLine());
            System.out.println("\tRadno mjesto :"+client.readLine());
            System.out.println("\tVeb sajtovi :"+client.readLine());
            System.out.println("\tTip korisnika :"+client.readLine());
            System.out.println();
        }
        catch(IOException ex){
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaVlastitimSertifikatom(){
        try{
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
          
            System.out.println("[::] SERTIFIKACIJA : "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_SERTIFIKATOM + " [::] ");
            
            if(ok==null) return;
            
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_VLASTITIM_SERTIFIKATOM.toString());
            
            boolean certExists = new Boolean(client.readLine());
            
            
            if(certExists) System.out.println("[::] Korisnik je sertifikovan [::]");
            else System.out.println("[::] Korisnik nije sertifikovan, upucen je zahtijev [::]");
            
            if(certExists) {
                
                Serializable sertifikati = Base64Swapper.decodeToSerializable(client.readLine());
                FileTransporter ftrans = (FileTransporter) sertifikati;
                ftrans.setDirectoryAll(new File(KonstanteDirektorijuma.arhivacertifikata));
                
                System.out.print("[::] Unesi korisnicku sifru radi sifrovanja privatnog kljuca: ");
                
                Scanner scan = new Scanner(System.in);
                String pkpass = scan.nextLine();
                
                client.proveraSifreOut(pkpass);
                boolean res = client.proveraSifreIn();
                if(!res){
                    System.out.println("[::] Sifra je nevazeca, sertifikati nece biti preuzeti.[::]");
                    System.out.println();
                    return;
                }
                
                String subjectcer[] = ftrans.files.get("subjectcer").getFile().getName().split("-");
                String subjectjks[] = ftrans.files.get("subjectjks").getFile().getName().split("-");
                
                ftrans.files.get("subjectcer").setFileName(subjectcer[0]+"-"+subjectcer[3]);
                ftrans.files.get("subjectjks").setFileName(subjectjks[0]+"-"+subjectjks[3]);
                
                
                ByteArrayInputStream is = new ByteArrayInputStream(ftrans.files.get("subjectjks").getContent());
                KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
                String password = CertificationConstants.defaultPassword; 
                String alias = CertificationConstants.defaultAlias;
                store.load(is, password.toCharArray());
                PrivateKey pkey = (PrivateKey) store.getKey(admin.getUsername(), password.toCharArray());
                X509Certificate cert = new CertificateManager(ftrans.files.get("subjectcer").getContent()).getCert();
                is.close();
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                store = KeyStore.getInstance(KeyStore.getDefaultType());
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                store.load(null,pkpass.toCharArray());
                store.setKeyEntry(admin.getUsername(),pkey, pkpass.toCharArray(),new Certificate[]{cert});
                store.store(baos, pkpass.toCharArray());
                ftrans.files.get("subjectjks").setContent(baos.toByteArray());
                baos.close();
                
                ftrans.saveAll();
               
                System.out.println("[::] Pristupni sertifikat servera, vlastiti pristupni sertifikat i privatni kljuc dobavljeni. [::]");                
                System.out.println();
            }
            
        }
        catch(IOException ex){
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void podaciDrugogKorisnika(){
        try{
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            System.out.println(">><< KORISNICKI PODACI : "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_PODACIMA + " >><< ");
            
            if(ok==null) return;
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_PODACIMA.toString());
            
            System.out.print(">><< Korisnicko ime za pretragu osnovnih podataka : ");
            Scanner scan = new Scanner(System.in);
            String uname = scan.nextLine();
            
            client.writeLine(uname);
            
            System.out.println("Podaci o prijavljenom korisniku :");
            
            System.out.println("\tIme :"+client.readLine());
            System.out.println("\tPrezime :"+client.readLine());
            System.out.println("\tKorisnicko ime :"+client.readLine());
            System.out.println("\tAdresa :"+client.readLine());
            System.out.println("\tIdentifikacija :"+client.readLine());
            
            System.out.println("\tElektronska posta :"+client.readLine());
            System.out.println("\tTelefon :"+client.readLine());
            System.out.println("\tRadno mjesto :"+client.readLine());
            System.out.println("\tVeb sajtovi :"+client.readLine());
            System.out.println("\tTip korisnika :"+client.readLine());
            System.out.println();
        }
        catch(IOException ex){
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zahtijevZaSertifikatomDrugogKorisnika(){
        try{
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
          
            System.out.println("[::] SERTIFIKACIJA : "+NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_SERTIFIKATOM + " [::] ");
            
            if(ok==null) return;
          
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_KORISNICKIM_SERTIFIKATOM.toString()); 
            System.out.print("[::] Unesi korisnicko ime korisnika ciji je potreban certifikat : ");
            Scanner scan = new Scanner(System.in);
            String naziv = scan.nextLine(); 
            client.writeLine(naziv);
            
            boolean certExists = new Boolean(client.readLine());
            
            if(certExists) System.out.println("[::] Korisnik je sertifikovan [::]");
            else System.out.println("[::] Korisnik nije sertifikovan. [::]");
            
            if(certExists) {                
                Serializable sertifikati = Base64Swapper.decodeToSerializable(client.readLine());
                FileTransporter ftrans = (FileTransporter) sertifikati;
                ftrans.setDirectoryAll(new File(KonstanteDirektorijuma.arhivacertifikata));
                
                String subjectcer[] = ftrans.files.get("subjectcer").getFile().getName().split("-");
                ftrans.files.get("subjectcer").setFileName(subjectcer[0]+"-"+subjectcer[3]);
                ftrans.saveAll();
               
                System.out.println("[::] Korisnicki pristupni sertifikat je dobavljen. [::]");                
                System.out.println();
            }
            
        }
        catch(IOException ex){
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaSertifikata(){
        try {
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            System.out.println("[::] SERTIFIKATI : "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_SERTIFIKATA_KORISNIKA+ "[::]");
            if(ok==null) return;
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_SERTIFIKATA_KORISNIKA.toString());
            List<File> files = (List<File>) Base64Swapper.decodeToSerializable(client.readLine());
            for(File file: files){
                System.out.println("\t"+file.getName());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listaSertifikovanih(){
        try {
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            System.out.println("[::] SERTIFIKATI : "+NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA_SERTIFIKOVANIH_KORISNIKA+ "[::]");
            if(ok==null) return;
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_KORISNICKIH_IMENA_SERTIFIKOVANIH_KORISNIKA.toString());
            List<String> files = (List<String>) Base64Swapper.decodeToSerializable(client.readLine());
            for(String file: files){
                if(!file.equals(KonstanteSertifikataServera.serverPotpisaniSertifikat))
                System.out.println("\t"+file);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void listaSlikaBezPoruke(){
        AktivniDirektorijumi ad = new AktivniDirektorijumi();
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> LISTA SLIKA BEZ PORUKE <<<<");
        for(File f: ad.ocitajImenaSlika()){
            System.out.println("\t"+f.getName());
        }
    }
    public void listaProcitanihPoruka(){
        AktivniDirektorijumi ad = new AktivniDirektorijumi();
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> LISTA OTVORENIH PORUKA <<<<");
        for(File f: ad.ocitajOtvorenePoruke()){
            System.out.println("\t"+f.getName());
        }
    }
    public void listaProcitanihEnkriptovanihPoruka(){
        AktivniDirektorijumi ad = new AktivniDirektorijumi();
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> LISTA ZATVORENIH PORUKA <<<<");
        for(File f: ad.ocitajEnkriptovanePoruke()){
            System.out.println("\t"+f.getName());
        }
    }
    public void listaNeprocitanihPoruka(){
        AktivniDirektorijumi ad = new AktivniDirektorijumi();
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> LISTA NEPROCITANIH PORUKA UPAKOVANIH U SLIKE <<<<");
        for(File f: ad.ocitajImenaSlikaSaPorukama()){
            System.out.println("\t"+f.getName());
        }
    }
    public FileTransporter pripremaKreiranjaSkripte() {
        try{
            FileTransporter zaUgradjivanje = new FileTransporter();
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            if(ok==null) return zaUgradjivanje;
            Scanner scaner = new Scanner(System.in);
            System.out.print("Unesi ime datoteke slike u koju ce se pakovati poruka : ");
            String slika = scaner.nextLine(); 
            System.out.print("Unesi jednolinijsku poruku koja ce se pakovati : ");
            String podaci = scaner.nextLine(); 
            System.out.print("Unesi korisnicko ime kojem se poruka salje : ");
            String korisnik = scaner.nextLine(); 
            String password = korisnik; 
            ImenovanjaDatoteka ida = new ImenovanjaDatoteka(admin.getUsername(),korisnik);
            SablonskiUzorciNaredbiSteganogrfije sns = new SablonskiUzorciNaredbiSteganogrfije();
            DatotekeSertifikata ds = new DatotekeSertifikata(korisnik);
            DatotekeSertifikata ads = new DatotekeSertifikata(admin.getUsername());
            ida.setEncryptedText();
            File datotekaSertifikataPrimaoca = ds.zadnjiCerSertifikat();
            File datotekaPrivatnogSertifikataPosiljaoca = ads.zadnjiJksSertifikat();
            File datotekaPristupnogSertifikataPosiljaoca = ads.zadnjiCerSertifikat();
            if(datotekaSertifikataPrimaoca == null || datotekaPrivatnogSertifikataPosiljaoca==null) {
                System.out.println("Primalac poruke ili ne postoji ili nije sertifikovan.");
                System.out.println("Ili pak ne postoji vlasnikov sertifikat. Procjeri sertifikat.");
                System.out.println("Posalji zahtijev za svoj sertifikat i sertifikat korisnika.");
                System.out.println("Poruka se ne moze kriptovati, pa ni skipta kreirati.");
                return zaUgradjivanje; 
            }
            if(datotekaPristupnogSertifikataPosiljaoca ==null){
                System.out.println("Izostaje sertifikat prijavljenog korisnika.");
                System.out.println("MSG datoteka poruke se ne moze kreirati.");
                System.out.println("To znaci da korisnik nece vise moci provjeriti poruku.");
            }
            System.out.print("Unesi sifru naloga radi kriptovanja poruke : ");
            String keypassword = scaner.nextLine();
            while(true){
                client.proveraSifreOut(keypassword);
                boolean valid =  client.proveraSifreIn();
                if(valid) break;
                String izbor = "";
                System.out.print("Novi pokusaj unosa sifre [da/ne] ? ");
                izbor=scaner.nextLine(); 
                if(!izbor.trim().toLowerCase().equals("da")) {keypassword=null; break;} 
                System.out.print("Ponovni unos sifre naloga : ");
                keypassword = scaner.nextLine();
            }
            
            if(keypassword==null){
                System.out.println("Odstupanje od kreiranja skripti.");
                return new FileTransporter(); 
            }
            
            String msgdata = ""; 
            
            CertificateManager cmg = new CertificateManager(datotekaSertifikataPrimaoca);
            AsimetricniAlgoritam alg = new AsimetricniAlgoritam();
            alg.setCommunicationKey(cmg.getCert().getPublicKey());
           
            msgdata = podaci;
            podaci = Base64.getEncoder().encodeToString(alg.encryptDataForSingle(podaci.getBytes()));
            
            PrivateKeyManager pkmg = new PrivateKeyManager(datotekaPrivatnogSertifikataPosiljaoca,admin.getUsername(),keypassword);

            alg = new AsimetricniAlgoritam(){
                public AsimetricniAlgoritam setPrivatniKljuc(PrivateKey pk){
                    super.setPrivateKey(pk);
                    return this;
                }
            }.setPrivatniKljuc(pkmg.getPrivatniKljuc());

            
            TackaDigitalneEnvelope tde = new TackaDigitalneEnvelope(alg,null);
            DigitalnaEnvelopa de = new DigitalnaEnvelopa(tde,null,null); 
            SignatureManager sm = new SignatureManager(de, new UpravljanjeDokumentima());
            sm.initSignature();

            String date = "Datum generisanja skripte : " + ida.getDatum();
            String userinfo = Base64.getEncoder().encodeToString(sm.getSignatureHash((admin.getUsername()+date+podaci).getBytes()));
            String encconent = admin.getUsername()+"\n"+date+"\n"+userinfo + "\n"+podaci; 
            FileUnit funit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(),ida.getFullFilename()));
            funit.setContent(encconent.getBytes()); 
            
            if(datotekaPristupnogSertifikataPosiljaoca != null){
                CertificateManager cmg2 = new CertificateManager(datotekaPristupnogSertifikataPosiljaoca);
                AsimetricniAlgoritam alg2 = new AsimetricniAlgoritam();
                alg2.setCommunicationKey(cmg2.getCert().getPublicKey());
                ImenovanjaDatoteka ida2 = new ImenovanjaDatoteka(ida).setExtension(".msg");
                msgdata = Base64.getEncoder().encodeToString(alg2.encryptDataForSingle(msgdata.getBytes()));
                FileUnit mfunit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(),ida2.getFullFilename()));
                mfunit.setContent(msgdata.getBytes());
                zaUgradjivanje.files.put("msg", mfunit);
            }
            
            zaUgradjivanje.files.put("enc", funit);
            
            String[] eparts = slika.split("[.]");
            String ext = ""; 
            if(eparts.length>=2) 
                ext = "."+eparts[eparts.length-1]; 
            
            sns.setSifraValue(password);
            sns.setSlikaValue(slika);
            sns.setPodaciValue(funit.getFile().getName());
            sns.setPorukaValue(ida.setExtension(ext).setOutbox().getFullFilename());
            
            funit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getStegonografija(),
                    ida.resetOutbox().generateBatFilenameForEmneding()));
            funit.setContent(sns.generateBatScriptSadrzajZaUgradnju().getBytes());
            
            zaUgradjivanje.files.put("bat", funit);
           
                        
            funit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getStegonografija(),
                    ida.resetOutbox().generateShellFilenameForEmneding()));
            funit.setContent(sns.generateShellScriptSadrzajZaUgradnju().getBytes());
            
            zaUgradjivanje.files.put("sh", funit);
            
            return zaUgradjivanje;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new FileTransporter();
    }
    
    public void stvaranjeSkripteZaUgradjivanje() {
        try{
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> STVARANJE SKRIPTI ZA UGRADJIVANJE <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            if(ok==null) return;
                    
            int count = pripremaKreiranjaSkripte().saveAll().files.size(); 
            if(count>0)
                System.out.println("Skripte i potrebna ENC/MSG datoteke su kreirane.");
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void stvaranjeSkripteZaIzdvajanje(){
        try{
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> STVARANJE SKRIPTI ZA IZDVAJANJE <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return; 
            
            FileTransporter zaIzdvajanje = new FileTransporter();
            
            Scanner scaner = new Scanner(System.in);
            String password = admin.getUsername();
            System.out.print("Unesi puno ime datoteke slike u kojoj je poruka : ");
            String poruka = scaner.nextLine(); 
            
            ImenovanjaDatoteka ida = new ImenovanjaDatoteka();
            ida.parseFullFilename(poruka);
            SablonskiUzorciNaredbiSteganogrfije sns = new SablonskiUzorciNaredbiSteganogrfije();
            
            File file = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(), poruka);
            if(!file.exists()) {
                System.out.println("Datoteka slike sa porukom ne postoji.");
                System.out.println("Kreiranje skripti za izdvajanje poruke je obustavljeno.");
                return; 
            }
            
            ida.resetInbox().resetOutbox();
            ida.setEncryptedText();
            String podaci = ida.getFullFilename(); 
            
            sns.setSifraValue(password);
            sns.setPodaciValue(podaci);
            sns.setPorukaValue(poruka);
            
            FileTransporter ftrans = new FileTransporter();
            
            FileUnit funit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getStegonografija(),
                    ida.generateBatFilenameForExtracting()));
            funit.setContent(sns.generateBatScriptSadrzajZaIzdvajanje().getBytes());
            
            ftrans.files.put("bat", funit);
            
            funit = new FileUnit(new File(PodaciKorisnikaAktivniDirektorijumi.getStegonografija(),
                    ida.generateShellFilenameForExtracting()));
            funit.setContent(sns.generateShellScriptSadrzajZaIzdvajanje().getBytes());
            
            ftrans.files.put("sh", funit);
            
            ftrans.saveAll();
            
            System.out.println("Skripte za izdvajanje poruka iz slike su kreirane.");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void ocitavanjeEnkriptovanePoruke(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> DEKRIPCIJA I OCITAVANJE ENKRIPTOVANIH SKRIPTI <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            System.out.print("Unesi ime datoteke sa kriptovanim tekstom : ");
            Scanner scan = new Scanner(System.in);
            String text = scan.nextLine();
            File info = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(),text);
            ImenovanjaDatoteka ida = new ImenovanjaDatoteka();
            ida.parseFullFilename(info.getName());
            
            if(!info.exists()){
                System.out.println("Datoteka za ocitavanje poruke ne postoji.");
                System.out.println("Nije mogice ocitati poruku.");
                return;
            }
            if(!ida.isEncryptedText()) {
                System.out.println("Datoteka nije odgovarajuceg formata.");
                System.out.println("Nije moguce ocitati poruku.");
                return;
            }
            DatotekeSertifikata ds = new DatotekeSertifikata(admin.getUsername());
            DatotekeSertifikata ads = new DatotekeSertifikata(ida.getUsernameSender());
            File privatniKljuc = ds.zadnjiJksSertifikat();
            File sertifikatPosiljaoca = ads.zadnjiCerSertifikat();
            
            if(!ida.isEncryptedText()) {
                System.out.println("Sertifikat posiljaoca nedostaje,");
                System.out.println("ili privatni kljuc primaoca nedostaje.");
                System.out.println("Nije moguce ocitati poruku.");
                return;
            }
            
            CertificateManager cmg = new CertificateManager(sertifikatPosiljaoca);
            AsimetricniAlgoritam alg = new AsimetricniAlgoritam();
            alg.setCommunicationKey(cmg.getCert().getPublicKey());
            
            System.out.print("Unesi sifru naloga radi dekriptovanja poruke : ");
            String keypassword = scan.nextLine();
            while(true){
                client.proveraSifreOut(keypassword);
                boolean valid =  client.proveraSifreIn();
                if(valid) break;
                String izbor = "";
                System.out.print("Novi pokusaj unosa sifre [da/ne] ? ");
                izbor=scan.nextLine(); 
                if(!izbor.trim().toLowerCase().equals("da")) { keypassword=null; break; }  
                System.out.print("Ponovni unos sifre naloga : ");
                keypassword = scan.nextLine();
            }
            
            if(keypassword==null) {
                System.out.println("Odstupanje od ocitavanja poruke.");
                return; 
            }
            PrivateKeyManager pkmg = new PrivateKeyManager(privatniKljuc,admin.getUsername(),keypassword);

            TackaDigitalneEnvelope tde = new TackaDigitalneEnvelope(alg,null);
            DigitalnaEnvelopa de = new DigitalnaEnvelopa(tde,null,null);
            SignatureManager sm = new SignatureManager(de, new UpravljanjeDokumentima());
            sm.initSignature();
            
            alg = new AsimetricniAlgoritam(){
                public AsimetricniAlgoritam setPrivatniKljuc(PrivateKey pk){
                    super.setPrivateKey(pk);
                    return this;
                }
            }.setPrivatniKljuc(pkmg.getPrivatniKljuc());
            
            List<String> lines = Files.readAllLines(info.toPath());
            if(lines.size()<4){
                System.out.println("Tekst enkriptovane datoteke nije pravilno formatiran. ");
                System.out.println("Ocitavanje poruke se otkazije. ");
                return; 
            }
            String txt = lines.get(0)+lines.get(1)+lines.get(3);
            byte [] txtb = txt.getBytes();
            byte [] txtd = Base64.getDecoder().decode(lines.get(2));
            byte [] txtm = Base64.getDecoder().decode(lines.get(3));
            boolean potpis = sm.checkDocument(txtb,txtd);
            if(!potpis){
                System.out.println("Potpis nije validan.");
                System.out.println("Otkazuje se ocitavanje poruke.");
                return; 
            }
            try{
                String str = new String(alg.decryptDataForSingle(txtm));
                if(str == null){
                    System.out.println("Poruka se ne moze enkriptovati.");
                    System.out.println("Poruku nije moguce procitati.");
                    return; 
                }else{
                    String date = new Date().toString(); 
                    System.out.println(">>>>PORUKA<<<<");
                    System.out.println(str);
                    System.out.println(">>>>POSILJALAC<<<<");
                    System.out.println(lines.get(0));
                    System.out.println(">>>>REGISTROVANI POSLILJALAC<<<<");
                    System.out.println(ida.getUsernameSender());
                    System.out.println(">>>>DATUM KREIRANJA PORUKE<<<<");
                    System.out.println(lines.get(1).substring("Datum generisanja skripte : ".length()));
                    System.out.println(">>>>DATUM PRIJEMA PORUKE<<<<");
                    System.out.println(ida.getDatum()); 
                    System.out.println(">>>>DATUM OCITAVANJA PORUKE<<<<");
                    System.out.println(date);
                    System.out.print("Da li ce se poruka sacuvati u otvorenom obliku [da/ne]? ");
                    String izbor = scan.nextLine(); 
                    if(izbor.trim().toLowerCase().equals("da")){
                        FileUnit f = new FileUnit(
                                new File(
                                        PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(),
                                        ida.setText().getFullFilename()));
                        String s= "Datum kreiranja poruke : "+ lines.get(1).substring("Datum generisanja skripte : ".length())+"\n";
                        s+= "Datum prijema poruke : "+ida.getUsernameSender()+"\n";
                        s+= "Datum ocitavanja poruke : " + date+"\n";
                        s+= "Posiljalac : " + lines.get(0)+"\n"; 
                        s+= "Primalac : "+ admin.getUsername()+"\n"; 
                        s+= "Poruka : "+ str; 
                        f.setContent(s.getBytes()).save();
                    }
                }
            }catch(Exception ex){
                System.out.println("Poruka se ne moze enkriptovati.");
                System.out.println("Poruku nije moguce procitati.");
            }
        } catch (ParseException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void slanjePorukeUSlici(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> SLANJE PORUKE U SLICI NA SERVER <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            System.out.print("Unesi ime datoteke slike sa porukom koja ce se slati : ");
            Scanner scan = new Scanner(System.in);
            String name = scan.nextLine();
            File info = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama(),name);
            if(!info.exists() || !info.isFile()) {
                System.out.println("Unijeta datoteka ne postoji.");
                System.out.println("Slanje se otkazuje.");
                return;
            }
            ImenovanjaDatoteka ida = new ImenovanjaDatoteka();
            ida.parseFullFilename(info.getName());
            String fullname = ida.getFullFilename();
            if(fullname==null || !ida.isOutbox()){
                System.out.println("Greska u formatu imena datoteke.");
                System.out.println("Slanje se otkazuje.");
                return;
            }
            ida.resetOutbox();
            ida.setDatum(new Date());
            FileTransporter ftrans = new FileTransporter();
            FileUnit funit = new FileUnit(info); 
            funit.open().setFileName(ida.getFullFilename());
            ftrans.files.put("msg",funit);
            String line = Base64Swapper.encode(ftrans);
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_POSTAVLJANJE_PORUKE_U_SLICI.toString());
            client.writeLine(line);
            boolean res = Boolean.parseBoolean(client.readLine());
            String msg = client.readLine(); 
            if(!res){
                System.out.println("Poruka nije poslata.");
                System.out.println(msg);
            }else{
                System.out.println("Poruka je uspjesno poslana na server.");
            }
        } catch (Exception ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void listaNeprimljenihPorukaServera(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> LISTANJE SVIH PORUKA NA SERVERU <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA.toString());
            List<File> imenaDatoteka = (List<File>) Base64Swapper.decodeToSerializable(client.readLine());
            
            System.out.println(">>>> Sve datoteke : ");
            for(File datoteka: imenaDatoteka){
                System.out.println("\t"+datoteka.getName());
            }
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_LISTANJE_IMENA_SLIKA_SA_PORUKAMA_ZA_KORISNIKA.toString());
            imenaDatoteka = (List<File>) Base64Swapper.decodeToSerializable(client.readLine());
            
            System.out.println(">>>> Datoteke namijenjene prijavljenom korisniku : ");
            for(File datoteka: imenaDatoteka){
                System.out.println("\t"+datoteka.getName());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void prijemSvihPorukaSaServera(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> PRIJEM SVIH PORUKA SA SERVERA ZA PRIJAVLJENOG KORISNIKA <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_PREUZIMANJE_SVIH_SLIKA_S_PORUKAMA_ZA_KORISNIKA.toString());
            FileTransporter ftrans = (FileTransporter) Base64Swapper.decodeToSerializable(client.readLine()); 
            ftrans.setDirectoryAll(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama()).saveAll(); 
            
            if(ftrans.files.values().size()==0) {
                System.out.println("Nema neprimljenih poruka.");
                return; 
            }
            
            System.out.println("Preuzete su poruke za korisnika sa servera.");
            for(FileUnit fu: ftrans.files.values()){
                System.out.println("\t"+fu.getFile().getName());
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void prijemPorukeSaServera(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> PRIJEM BILO KOJE CILJANE PORUKE SA SERBERA<<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_PORUKOM_U_SLICI.toString());
            
            Scanner scan = new Scanner(System.in);
            System.out.print("Unesi ime datoteke slike sa porukom koja se preuzima : ");
            client.writeLine(scan.nextLine());
            boolean res = Boolean.parseBoolean(client.readLine()); 
            if(!res){
                System.out.println("Datoteka ne postoji na serveru. ");
                return;
            }
            
            FileUnit funit = (FileUnit) Base64Swapper.decodeToSerializable(client.readLine());
            funit.setDirectory(PodaciKorisnikaAktivniDirektorijumi.getPorukeUSlikama()).save(); 
            System.out.println("Preuzeta  poruka za korisnika sa servera.");
            System.out.println("\t"+funit.getFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ocitavanjeKontrolnePoruke(){
        try {
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> KONTROLNE OCITAVANJE PORUKE PRIPREMLJENE ZA U SKRIPTU <<<<");
            String ok = PodaciKorisnikaKlijentKonzola.getSesija();
            PrijavaBean admin = PodaciKorisnikaKlijentKonzola.getPrijavljeniKorisnik();
            
            if(ok==null) return;
            
            System.out.print("Unesi ime datoteke sa kontrolnim tekstom : ");
            Scanner scan = new Scanner(System.in);
            String text = scan.nextLine();
            File info = new File(PodaciKorisnikaAktivniDirektorijumi.getPorukeIzSlika(),text.trim());
            ImenovanjaDatoteka ida = new ImenovanjaDatoteka();
            ida.parseFullFilename(info.getName());
            
           
            if(!info.exists()){
                System.out.println("Datoteka za ocitavanje poruke ne postoji.");
                System.out.println("Nije moguce ocitati poruku.");
                return;
            }
            if(!info.getName().trim().toLowerCase().endsWith(".msg")){
                System.out.println("Datoteka nije odgovarajuceg formata.");
                System.out.println("Nije moguce ocitati poruku.");
                return;
            }
            DatotekeSertifikata ds = new DatotekeSertifikata(admin.getUsername());
            File privatniKljuc = ds.zadnjiJksSertifikat();
           
            
            System.out.print("Unesi sifru naloga radi dekriptovanja poruke : ");
            String keypassword = scan.nextLine();
            while(true){
                client.proveraSifreOut(keypassword);
                boolean valid =  client.proveraSifreIn();
                if(valid) break;
                String izbor = "";
                System.out.print("Novi pokusaj unosa sifre [da/ne] ? ");
                izbor=scan.nextLine(); 
                if(!izbor.trim().toLowerCase().equals("da")) { keypassword=null; break; }  
                System.out.print("Ponovni unos sifre naloga : ");
                keypassword = scan.nextLine();
            }
            
            if(keypassword==null) {
                System.out.println("Odstupanje od ocitavanja poruke.");
                return; 
            }
            PrivateKeyManager pkmg = new PrivateKeyManager(privatniKljuc,admin.getUsername(),keypassword);
            
            AsimetricniAlgoritam alg = new AsimetricniAlgoritam(){
                public AsimetricniAlgoritam setPrivatniKljuc(PrivateKey pk){
                    super.setPrivateKey(pk);
                    return this;
                }
            }.setPrivatniKljuc(pkmg.getPrivatniKljuc());
            
            FileUnit unit = new FileUnit(info).open();
            
            byte [] txtb = Base64.getDecoder().decode(unit.getContent());
            try{
                String str = new String(alg.decryptDataForSingle(txtb));
                if(str == null){
                    System.out.println("Poruka se ne moze dekriptovati.");
                    System.out.println("Poruku nije moguce procitati.");
                    return;
                }else{
                    System.out.println(">>>> PORUKA <<<<");
                    System.out.println(str);
                }
            }catch(Exception ex){
                System.out.println("Poruka se ne moze enkriptovati.");
                System.out.println("Poruku nije moguce procitati.");
            }
        } catch (ParseException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void listaKontrolnihPoruka(){
        AktivniDirektorijumi ad = new AktivniDirektorijumi();
        System.out.println(">>>> STEGANOGRAFIJA <<<<");
        System.out.println(">>>> LISTA KONTROLNIH PORUKA <<<<");
        for(File f: ad.ocitajKontrolnePoruke()){
            System.out.println("\t"+f.getName());
        }
    }
    public void mapaIsporuka(){
        try {
            AktivniDirektorijumi ad = new AktivniDirektorijumi();
            System.out.println(">>>> STEGANOGRAFIJA <<<<");
            System.out.println(">>>> MAPA ISPORUKA, DATUMA I PRIMALACA SA SERVERA <<<<");
            client.writeLine(NaredbeProtokolaPROPIS.PROPIS_ZAHTIJEV_ZA_SPISKOM_ISPORUKA.toString());
            System.out.println(Base64Swapper.decodeToSerializable(client.readLine()));
        } catch (IOException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciKorisnikaAdapterKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void informacijeOSlikama(){
    }
    
    public void zahtijevZaPovlacenjeSertifikata(){
    }
    
    public void zahtijevZaCRLListu(){
    }
    
    public void ocitavanjeCRLListe(){
    }
}
