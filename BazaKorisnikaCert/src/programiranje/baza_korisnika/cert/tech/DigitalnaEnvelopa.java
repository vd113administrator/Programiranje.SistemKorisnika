/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.tech;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import programiranje.baza_korisnika.cert.alg.AsimetricniAlgoritam;
import programiranje.baza_korisnika.cert.alg.SimetricniAlgoritam;
import programiranje.baza_korisnika.cert.pki.X509Manager;

/**
 * <b>Funkcionalnosti digitalne envelope</b><br><br>
 * Polja: <br>
 *   tacka digitalne envelope - algoritmi i upravljanje <br>
 *   baferovan citac - ocitavanje <br>
 *   print pisac - upisivanje <br>
 * @author Mikec
 */
public class DigitalnaEnvelopa implements Serializable{
    private TackaDigitalneEnvelope t; 
    private transient BufferedReader br; 
    private transient PrintWriter pw; 
    private X509Manager cmg; 
    
    /**
     * Podrazumjevani konstruktor
     */
    private DigitalnaEnvelopa(){
    }
    
    /**
     * Konstruktor sa citacem i pisacem 
     * @param r citac
     * @param p pisac
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public DigitalnaEnvelopa(BufferedReader r, PrintWriter p)
            throws NoSuchAlgorithmException, NoSuchPaddingException{
        t = new TackaDigitalneEnvelope(); 
        br = r; 
        pw = p; 
    }
    
    /**
     * Konstruktor sa citacem, pisacem i tackom digitalne envelope
     * @param ta tacka digitalne envelope
     * @param r citac
     * @param p pisac
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public DigitalnaEnvelopa(TackaDigitalneEnvelope ta, BufferedReader r, PrintWriter p)
            throws NoSuchAlgorithmException, NoSuchPaddingException{
        t = ta;
        br = r;
        pw = p;
    }
    
    /**
     * Metoda za slanje javnog kljuca na drugu stranu komunikacije <br>
     * Ocitavanje je metodom {@link prijemJavnogKljuca} gdje se postavlja kao komunikacijski 
     * @throws IOException 
     */
    public void slanjeJavnogKljuca() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(t.getJavniKljuc());
        byte [] b = baos.toByteArray(); oos.close(); 
        pw.println(Base64.getEncoder().encodeToString(b));
    }
    
    /**
     * Prijem javnog kljuca koji je poslan sa prve strane na drugu metodom {@link prijemJavnogKljuca}
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void prijemJavnogKljuca() throws IOException, ClassNotFoundException{
        byte b[] = Base64.getDecoder().decode(br.readLine());
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        PublicKey k = (PublicKey) ois.readObject();
        t.getAsimetricniAlgoritam().setCommunicationKey(k);
        ois.close();
    }
    
    /**
     * Dobijanje tacke digitalne envelope
     * @return tacka digitalne envelope
     */
    public TackaDigitalneEnvelope getTackaDE(){
        return t; 
    }
    
    /**
     * Dobijanje pisaca
     * @return pisac
     */
    public PrintWriter getWriter(){
        return pw; 
    }
    
    /**
     * Dobijanje citaca
     * @return citac
     */
    public BufferedReader getReader(){
        return br;
    }
    
    /**
     * Slanje sesijskog kljuca koji se salje sa jedne na drugu stranu <br>
     * Moze postojati klijent server komunikacija, ali moze biti i lokalna
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public void slanjeSesijskogKljuca() 
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        byte [] b = t.getSesijskiKljuc().getEncoded();
        byte [] e = t.getAsimetricniAlgoritam().encryptDataForSingle(b);
        pw.println(t.getSimetricniAlgoritam().getAlgorithm());
        pw.println(Base64.getEncoder().encodeToString(e));
    }
    
    /**
     * Prijem sesijskog kljuca sadruge strane na prvu koji je poslat sa {@link slanjeSesijskogKljuca}
     * @throws IOException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException 
     */
    public void prijemSesijskogKljuca() throws IOException, InvalidKeyException, 
            IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException{
        String alg = br.readLine();
        t.setSimetricniAlgoritam(new SimetricniAlgoritam(alg));
        byte e[] = Base64.getDecoder().decode(br.readLine());
        byte [] b = t.getAsimetricniAlgoritam().decryptDataForSingle(e);
        SecretKey key = new SecretKeySpec(b,0,b.length,t.getSimetricniAlgoritam().getAlgorithm());
        t.setSesijskiKljuc(key);
    }
    
    /**
     * Slanje podataka koristeci digitalnu envelopu <br>
     * @param by podaci
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public void slanjePodataka(byte[] by) throws 
            NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        byte[] bx = t.getSimetricniAlgoritam().symmetricEncrypt(by);
        pw.println(Base64.getEncoder().encodeToString(bx));
    }
    
    /**
     * Prijem podataka poslanih metodom {@link slanjePodataka} 
     * @return originalni podaci prijemene strane
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public byte[] prijemPodataka() throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, 
            BadPaddingException{
        byte[] bx = Base64.getDecoder().decode(br.readLine());
        byte[] by = t.getSimetricniAlgoritam().symmetricDecrypt(bx);
        return by;
    }
    
    /**
     * Slanje signalne poruke
     */
    public void slanjeSignala(){
        pw.println();
    }
    
    /**
     * Prijem signalne poruke
     * @throws IOException 
     */
    public void prijemSignala() throws IOException{
        br.readLine();
    }
    
    /**
     * Kreiranje digitalne envelope sa tackom digitalne envelope i razlicitim ostalim poljima
     * @param pw novi pisac
     * @param br novi citac
     * @return kreiranja digitalna envelopa
     */
    public DigitalnaEnvelopa instancirajSaIstomTackom(PrintWriter pw, BufferedReader br){
        DigitalnaEnvelopa de = new DigitalnaEnvelopa();
        de.pw = pw;
        de.br = br;
        de.t = t; 
        return de;
    }
    
    public void clientSwapPUK() throws IOException, ClassNotFoundException{
       this.slanjeJavnogKljuca();
       this.prijemJavnogKljuca();
       rezimRada = NacinRada.UNILATERALNI; 
       if(!this.testJavnogKljucaPrijema()){
           try{this.applyCertAndCertServerCommunication();}
           catch(Exception ex){ex.printStackTrace();}
           this.slanjeJavnogKljuca();
           this.prijemJavnogKljuca();
           rezimRada = NacinRada.NESERTIFIKOVANI;
       }
    }
    
    
    public void serverSwapPUK() throws IOException, ClassNotFoundException{
        this.prijemJavnogKljuca();
        this.slanjeJavnogKljuca();
        rezimRada = NacinRada.UNILATERALNI; 
        if(!this.rezltatTestaJavnogKljucaPredaji()){
           try{this.applyCertAndCertServerCommunication();}
           catch(Exception ex){ex.printStackTrace();}
           this.prijemJavnogKljuca();
           this.slanjeJavnogKljuca();
           rezimRada = NacinRada.NESERTIFIKOVANI;
       }
    }
    
    
    public X509Manager getCertification(){
        return this.cmg; 
    }
    
    public void setCertification(X509Manager manager){
        this.cmg = manager; 
    }
    
    public void applyCertification(X509Manager manager) throws NoSuchAlgorithmException{
        if(manager != null){
            this.cmg = manager;
            t.prihvatenjaSertifikata(cmg);
        }else{
            this.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        }
    }
    
    public void applyCertAndCertServerCommunication(X509Manager manager) throws NoSuchAlgorithmException{
        if(manager!=null){
            this.cmg = manager;
            t.prihvatenjaKomunikacijeSaCertServerom(cmg);
        }
        else{
            this.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        }
    }
    
    public void applyCertification() throws NoSuchAlgorithmException{
        if(cmg != null){
            t.prihvatenjaSertifikata(cmg);
        }else{
            this.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        }
    }
    
    public void applyCertAndCertServerCommunication() throws NoSuchAlgorithmException{
        if(cmg!=null){
            t.prihvatenjaKomunikacijeSaCertServerom(cmg);
        }
        else{
            this.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        }
    }
    
    // RADI SE NAKON JAVNE RAZMJENE KLJUCEVA
    // AKO SU PRAVILNO RAZMIJENJENI VERIFIKACIJA RADI 
    // INICIRAJU GA KLIJENTI PREMA SERVEU
    public boolean testJavnogKljucaPrijema(){
        try{
            SimetricniAlgoritam randomData = new SimetricniAlgoritam();
            randomData.generateSecretKey();
            SecretKey test = randomData.getSecretKey(); 
            byte [] b = test.getEncoded(); 
            X509Certificate cert =  null; 
            PublicKey pk = pk = t.getJavniKljuc();
            if(cmg!=null && cmg.getX509Certificate()!=null && cmg.getX509Certificate().getPublicKey()!=null) 
            pk = cmg.getX509Certificate().getPublicKey(); 
            AsimetricniAlgoritam asa = new AsimetricniAlgoritam();
            asa.setCommunicationKey(pk);
            byte [] e = asa.encryptDataForSingle(b);
            pw.println(Base64.getEncoder().encodeToString(e));
            byte ee[] = Base64.getDecoder().decode(br.readLine());
            asa = new AsimetricniAlgoritam(){
                @Override
                public void setPrivateKey(PrivateKey privateKey) {
                   super.setPrivateKey(privateKey); 
                }

                public AsimetricniAlgoritam setPrivatniKljuc(PrivateKey privateKey){
                    setPrivateKey(privateKey);
                    return this;
                }
            }.setPrivatniKljuc(t.getTajniKljuc());
            byte [] bb = asa.decryptDataForSingle(ee);
            SecretKey key = new SecretKeySpec(bb,0,bb.length,t.getSimetricniAlgoritam().getAlgorithm());
            pw.println(key.equals(test));
            return key.equals(test);
        }catch(Exception ex){
            pw.println("false");
            return false; 
        } 
    }
    
    // RADI SE NAKON JAVNE RAZMJENE KLJUCEVA
    // AKO SU PRAVILNO RAZMIJENJENI VERIFIKACIJA RADI
    // REAKCIJA SERVERA NA TESTOVE TREBA DA VRATI ORIGINALAN 
    // GENERISANI POSLANI SLUCAJAN TEKST KOJI JE KRIPTOVAN JAVNIM KLJUCEM 
    // SERVERSKOG SERTIFIKATA KOD KLIJENTA 
    // SERVER TREBA TO DA POSALJE KRIPTOVANO 
    // KLIJENTSKIM JAVNIM KLJUCEM DOVIJENIM U RAZMJENI 
    public boolean rezltatTestaJavnogKljucaPredaji(){
        byte[] ee = "".getBytes();
        try{
          byte e[] = Base64.getDecoder().decode(br.readLine());
          byte []b = t.getAsimetricniAlgoritam().decryptDataForSingle(e);
          SecretKey key = new SecretKeySpec(b,0,b.length,t.getSimetricniAlgoritam().getAlgorithm());
          AsimetricniAlgoritam asa = new AsimetricniAlgoritam();
          asa.setCommunicationKey(t.getKomunikacijskiKljuc());
          byte [] bb = key.getEncoded();
          ee = asa.encryptDataForSingle(bb);
        }catch(Exception ex){
            ee = "".getBytes(); 
        }finally{
            pw.println(Base64.getEncoder().encodeToString(ee));
            try{
                return Boolean.parseBoolean(br.readLine());
            } catch(Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
    }
    
    
    public static enum NacinRada{
        BILATERALNI, UNILATERALNI, NESERTIFIKOVANI
    }
    
    private NacinRada rezimRada = NacinRada.NESERTIFIKOVANI; 
    
    public NacinRada getNacinRada(){
        return rezimRada;
    }
}
