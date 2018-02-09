/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.klijent.io;

import programiranje.baza_korisnika_console.staticno.ProtokolBKSP;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_console.klijent.IzlaznaNit;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_DEREGISTRACIJA;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_N3_PROVJERA_SIFRE;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_ODIJAVA;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_PODACI;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_PRIJAVA;
import static programiranje.baza_korisnika_console.staticno.ProtokolBKSP.BKSP_REGISTRACIJA;
import programiranje.baza_korisnika_console.staticno.StatickiPodaci;
import programiranje.baza_korisnika_shell.cert.Certification;

/**
 * Kontrola komunikacije sa serverom
 * @author Mikec
 */
public class AdapterServera{
    private Socket server;
    private Socket controlServer;
    private PotpisaniDekriptReader secureReader;
    private PotpisaniEnkriptWriter secureWriter;
    private PotpisaniDekriptReader controlSecureReader;
    private PotpisaniEnkriptWriter controlSecureWriter;
    private DigitalnaEnvelopa envelop; 
    private DigitalnaEnvelopa controlEnvelop; 
    private BufferedReader reader;
    private PrintWriter writer; 
    private BufferedReader controlReader;
    private PrintWriter controlWriter;
    private IzlaznaNit izlaznaNit; 
    
    /**
     * Konstruktor 
     * @param serv prikljucak na server za podatke  
     * @param kontrolniServer prikljucak na server za kontrolu
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws ClassNotFoundException 
     */
    public AdapterServera(Socket serv, Socket kontrolniServer) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException, CertificateException{
       server = serv;
       controlServer = kontrolniServer;
       
       reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
       writer = new PrintWriter(new OutputStreamWriter(server.getOutputStream()),true);
       controlReader = new BufferedReader(new InputStreamReader(controlServer.getInputStream()));
       controlWriter = new PrintWriter(new OutputStreamWriter(controlServer.getOutputStream()),true);
       
       envelop = new DigitalnaEnvelopa(reader,writer);
       
       String fileServerCert = StatickiPodaci.getCertificatePath();      
       envelop.applyCertAndCertServerCommunication(null);
       Certification.installServerCertificateFile(new File(fileServerCert), envelop);
 
       envelop.clientSwapPUK();

       controlEnvelop = envelop.instancirajSaIstomTackom(controlWriter, controlReader);
       
       UpravljanjeDokumentima ud = new UpravljanjeDokumentima(); 
       UpravljanjeDokumentima cud = new UpravljanjeDokumentima();
       SignatureManager sim = new SignatureManager(envelop, ud);
       SignatureManager csim = new SignatureManager(controlEnvelop, cud);
       
       try{
           sim.initSymetric();
           csim.initSymetric(); 
           sim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
           csim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
           sim.initSignature();
           csim.initSignature();
       } catch(Exception ex){
           ex.printStackTrace();
       }
       
       secureWriter = new PotpisaniEnkriptWriter(sim,true);
       secureReader = new PotpisaniDekriptReader(sim);
       controlSecureWriter = new PotpisaniEnkriptWriter(csim, true); 
       controlSecureReader = new PotpisaniDekriptReader(csim); 
       
       izlaznaNit = new IzlaznaNit(this);
       izlaznaNit.start();
    }
    
    /**
     * Dobijanje serverskog prikljucka
     * @return serverski prikljucak
     */
    public Socket getServerS(){
        return server; 
    }
    
    /**
     * Dobijanje kontrolnog serverskog prikljucka
     * @return prikljucak
     */
    public Socket getServerCS(){
        return controlServer; 
    }
    
    /**
     * Dobijanje pisaca na server
     * @return pisac
     */
    public PrintWriter getServerPW(){
        return writer;
    }
    
    /**
     * Dobijanje citaca sa servera 
     * @return citac
     */
    public BufferedReader getServerBR(){
        return reader; 
    }
    
    /**
     * Dobijanje potpisani enkriptovani pisac na server
     * @return pisac
     */
    public PotpisaniEnkriptWriter getServerEW(){
        return secureWriter; 
    } 
    
    /**
     * Dobijanje potpisanog dekriptovanog citaca sa servera  
     * @return citac
     */
    public PotpisaniDekriptReader getServerDR(){
        return secureReader;
    }
    
    /**
     * Dobijanje kontrolnog potpidano pisaca na server
     * @return pisac
     */
    public PrintWriter getServerCPW(){
        return controlWriter;
    }
    
    /**
     * Dobijanje serverskog kontrolnog citaca 
     * @return citaca 
     */
    public BufferedReader getServerCBR(){
        return controlReader; 
    }
    
    /**
     * Dobijanje servera za enkripcijski prisac
     * @return pisac
     */
    public PotpisaniEnkriptWriter getServerCEW(){
        return controlSecureWriter; 
    }
    
    /**
     * Dobijanje servera za dekripcijski citac
     * @return citac
     */
    public PotpisaniDekriptReader getServerCDR(){
        return controlSecureReader;
    }
    
    /**
     * Funkcija za ocitavanje kodovanog teksta koji moze biti viselinijski  
     * @return ocitan i prevedeni tekst 
     * @throws IOException 
     */
    public String readLine() throws IOException{ 
        synchronized(server){
            String msg = secureReader.readLine();
            msg = msg.replace("#n", "\n");
            msg = msg.replace("#k", "#");
            return msg;
        }
    }
    
    /**
     * Funkcija za ocitavanje kodovanog kontrolnog teksta koji moze biti viselinijski
     * @return ocitan i preveden kontrolni tekst 
     * @throws IOException 
     */
    public String cReadLine() throws IOException{
        synchronized(controlServer){
            String msg = controlSecureReader.readLine();
            msg = msg.replace("#n", "\n");
            msg = msg.replace("#k", "#");
            return msg;
        }
    }
    
    /**
     * Zapisivanje i kodovanje teksta koji mozebiti viselnijski 
     * @param msg 
     */
    public void writeLine(String msg){
        synchronized(server){
            msg = msg.replace("#", "#k");
            msg = msg.replace("\n", "#n");
            secureWriter.println(msg);
        }
    }
    /**
     * Zapisivanje i kodovanje kontrolnog teksta koji moze biti viselinijski 
     * @param msg 
     */
    public void cWriteLine(String msg){
        synchronized(controlServer){
            msg = msg.replace("#", "#k");
            msg = msg.replace("\n", "#n");
            controlSecureWriter.println(msg);
        }
    }
    
    /**
     * Funkcija koja se koristi za gasenje servera adaptera
     * @throws IOException 
     */
    public void izlaz() throws IOException{
        secureWriter.close();
        secureReader.close();
    }
   
    /**
     * Slanje podataka za registraciju
     * @param lista lista podataka 
     * @throws IOException 
     */
    public void registracijaOut(List<String> lista) throws IOException{
        writeLine(BKSP_REGISTRACIJA);
        for(String s: lista){
            writeLine(s);
        }
    }
    
    /**
     * Prijem povratnih podataka za registraciju 
     * @return Ocitavanje ulaznih podataka
     * @throws IOException 
     */
    public Pair<String,String> registracijaIn() throws IOException{
        String ok = readLine(); 
        String msg = readLine();
        return new Pair<String,String>(ok,msg);
    }
    
    /**
     * Zapis podataka za deregistraciju 
     * @param lista lista podataka
     * @throws IOException 
     */
    public void deregistracijaOut(List<String> lista) throws IOException{
        writeLine(BKSP_DEREGISTRACIJA);
        for(String s: lista)
            writeLine(s); 
    }
    
    /**
     * Ocitvanje povratnih podataka iz deregistracije
     */
    public String deregistracijaIn() throws IOException{
        return readLine();
    }
    
    /**
     * Slanje podataka za prijavu
     * @param lista podaci za prijavu 
     * @throws IOException 
     */
    public void prijavaOut(List<String> lista) throws IOException{
        writeLine(BKSP_PRIJAVA);
        for(String s: lista)
            writeLine(s); 
    }
    
    /**
     * Prijem povratnih podataka za prijavu
     * @return podaci za prijavu
     * @throws IOException 
     */
    public Pair<String,String> prijavaIn() throws IOException{
        String a = readLine(); 
        String b = readLine();
        return new Pair<String,String>(a,b);
    }
    
    /**
     * Dobijanje podataka o prijavljenom korisniku 
     * @return dobijanje podataka o korisniku 
     * @throws IOException 
     */
    public Pair<String, List<String>> podaciIn() throws IOException{
        writeLine(BKSP_PODACI);
        String res = readLine(); 
        
        ArrayList<String> alist = new ArrayList<>(); 
        if(res.equals(ProtokolBKSP.BKSP_SUCCESS)){
            for(int i=0; i<9; i++)
               alist.add(readLine());
        }
      
        if(res.equals(ProtokolBKSP.BKSP_ERROR)){
            alist.add(readLine()); 
        }
       
        return new Pair<String,List<String>>(res,alist);
    }
    
    /**
     * Prijem informacija o vezivanju aplikacija 
     * @return informacija
     * @throws IOException 
     */
    public String vezivanjeIn() throws IOException{
        return readLine();
    }
    
    /**
     * Ocitavanje informacija o izlazu i prekidu komunikacije
     * @return informacija 
     * @throws IOException 
     */
    public String izlazIn() throws IOException{
        return readLine();
    }
    
    /**
     * Dobijanje izlazne niti (koja kontrolise prekide)
     * @return izlazna nit
     */
    public IzlaznaNit getIzlaznaNit(){
        return izlaznaNit; 
    }
    
    /**
     * Slanje podataka za odjavu 
     */
    public void odijavaOut(){
        writeLine(BKSP_ODIJAVA);
    }
    
    public void proveraSifreOut(String password){
        writeLine(BKSP_N3_PROVJERA_SIFRE);
        writeLine(password);
    }
    
    public boolean proveraSifreIn() throws IOException{
        return (boolean) Boolean.parseBoolean(readLine());
    }
}
