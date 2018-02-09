/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.control;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_web.global.StatickiPodaci;
import programiranje.baza_korisnika_shell.cert.Certification;
import programiranje.baza_korisnika_web.global.SesijskiObjekti;
import programiranje.baza_korisnika_web.global.ProtokolBKSP;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_DEREGISTRACIJA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_ERROR;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_ODIJAVA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_PODACI;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_PRIJAVA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_REGISTRACIJA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_SUCCESS;
import programiranje.sistem_korisnika.grupe.veb.ulaz_izlaz.SKServerAdapter;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGP;
import programiranje.sistem_korisnika_web.driver.BazaKorisnikaDriver;
import programiranje.sistem_korisnika_web.driver.DriverManager;
import programiranje.sistem_korisnika_web.driver.SistemKorisnikaDriver;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;

/**
 * Komunikacija prema sesveru 
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
    private RadnaNit radnaNit; 
    private SesijskiObjekti sesijskiObjekti; 
    private SKServerAdapter skServerAdapter;
    private DriverManager driverManager; 
    
    /**
     * Konstruktor adaptera servera 
     * @param serv serverski prikljucak  
     * @param kontrolniServer prikljucak kontrolnog servera
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws ClassNotFoundException 
     */
    public AdapterServera(Socket serv, Socket kontrolniServer) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException{
       try{
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
               csim.initSymetric();
               sim.initSymetric();
               csim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
               sim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
               csim.initSignature();
               sim.initSignature();
           }catch(Exception ex){
               ex.printStackTrace();
           }
           secureWriter = new PotpisaniEnkriptWriter(sim,true);
           secureReader = new PotpisaniDekriptReader(sim);
           controlSecureWriter = new PotpisaniEnkriptWriter(csim,true);
           controlSecureReader = new PotpisaniDekriptReader(csim);
           skServerAdapter = new SKServerAdapter(this);
           izlaznaNit = new IzlaznaNit(this);
           izlaznaNit.start();
           radnaNit = new RadnaNit(this);
           radnaNit.start();
           driverManager = SKGlobalneFunkcije.getDriverManager();
       }catch(CertificateException ex){
            Logger.getLogger(AdapterServera.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    /**
     * Dobijanje menadzera dogadjajas
     * @return menadzer dogadjaja 
     */
    public DriverManager getDriverManager(){
        return driverManager; 
    }
    
    /**
     * Dobijanje prikljucka servera
     * @return prikljucak servera 
     */
    public Socket getServerS(){
        return server; 
    }
    
    /**
     * Dobijanje kontrolnog prikljucka servera 
     * @return kontrolni prikljucak servera
     */
    public Socket getServerCS(){
        return controlServer; 
    }
    
    /**
     * Dobijanje serverskog pisaca 
     * @return serverski pisac 
     */
    public PrintWriter getServerPW(){
        return writer;
    }
    
    /**
     * Dobijanje serverskog citaca
     * @return serverski citacs
     */
    public BufferedReader getServerBR(){
        return reader; 
    }
    
    /**
     * Dobijanje serverskog potpisujuceg enkriptujuceg pisaca 
     * @return pisac
     */
    public PotpisaniEnkriptWriter getServerEW(){
        return secureWriter; 
    } 
    
    /**
     * Dobijanje klijentskog potpisujuceg dekriptujuceg citaca 
     * @return citac
     */
    public PotpisaniDekriptReader getServerDR(){
        return secureReader;
    }
    
    /**
     * Dobijanje serverskog kontrolnog pisaca
     * @return pisac
     */
    public PrintWriter getServerCPW(){
        return controlWriter;
    }
    
    /**
     * Dobijanje serverskog kontrolnog citaca 
     * @return citac 
     */
    public BufferedReader getServerCBR(){
        return controlReader; 
    }
    
    /**
     * Dobijanje kontrolnog serverskog enkriptujuceg potpisujuceg pisaca 
     * @return pisaca
     */
    public PotpisaniEnkriptWriter getServerCEW(){
        return controlSecureWriter; 
    }
    
    /**
     * Dobijanje kontrolnog serverskoh dekripcijskog potpisujuceg citaca 
     * @return citac
     */
    public PotpisaniDekriptReader getServerCDR(){
        return controlSecureReader;
    }
    
    /**
     * Ocitavanje teksta  
     * @return ocitan tekst
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
     * Ocitavanje teksta s kontrolnog toka 
     * @return ocitan tekst
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
     * Pisanje teksta na server
     * @param msg tekst
     */
    public void writeLine(String msg){
        driverManager.go(BazaKorisnikaDriver.class, msg);
        driverManager.go(SistemKorisnikaDriver.class, msg);
        synchronized(server){
            msg = msg.replace("#", "#k");
            msg = msg.replace("\n", "#n");
            secureWriter.println(msg);
        }
    }
    
    /**
     * Pistanje teksta na kontrolni tok servera
     * @param msg server
     */
    public void cWriteLine(String msg){
        driverManager.go(BazaKorisnikaDriver.class, msg);
        driverManager.go(SistemKorisnikaDriver.class, msg);
        synchronized(controlServer){
            msg = msg.replace("#", "#k");
            msg = msg.replace("\n", "#n");
            controlSecureWriter.println(msg);
        }
    }
    
    /**
     * Izlaz
     * @throws IOException 
     */
    public void izlaz() throws IOException{
        secureWriter.close();
        secureReader.close();
        controlSecureWriter.close(); 
        controlSecureReader.close(); 
    }
   
    /**
     * Davanje podataka za registraciju na server 
     * @param lista podaci
     * @throws IOException 
     */
    public void registracijaOut(List<String> lista) throws IOException{
        writeLine(BKSP_REGISTRACIJA);
        for(String s: lista){
            writeLine(s);
        }
    }
    
    /**
     * Prijem rezultata registracije sa servera 
     * @return rezultati 
     * @throws IOException 
     */
    public Pair<String,String> registracijaIn() throws IOException{
        String ok = readLine(); 
        String msg = readLine();
        return new Pair<String,String>(ok,msg);
    }
    
    /**
     * Slanje podataka za deregistraciju na server s
     * @param lista podaci 
     * @throws IOException 
     */
    public void deregistracijaOut(List<String> lista) throws IOException{
        writeLine(BKSP_DEREGISTRACIJA);
        for(String s: lista)
            writeLine(s); 
    }
    
    /**
     * Prijem rezultata registracije sa servera s 
     * @return rezultati 
     * @throws IOException 
     */
    public String deregistracijaIn() throws IOException{
        return readLine();
    }
    
    /**
     * Slanje podataka za prijavu na server 
     * @param lista podaci
     * @throws IOException 
     */
    public void prijavaOut(List<String> lista) throws IOException{
        writeLine(BKSP_PRIJAVA);
        for(String s: lista)
            writeLine(s); 
    }
    
    /**
     * Prijem rezultata prijave sa servera 
     * @return rezultati prijave 
     * @throws IOException 
     */
    public Pair<String,String> prijavaIn() throws IOException{
        String a = readLine();
        String b = readLine();
        return new Pair<String,String>(a,b);
    }
    
    /**
     * Ocitavanje podataka o korisniku sa servera 
     * @return podaci o korisniku 
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
     * Ocitavanje podataka o vezivanju sa servera 
     * @return naredba za vezivanje 
     * @throws IOException 
     */
    public String vezivanjeIn() throws IOException{
        return readLine();
    }
    
    /**
     * Ocitavanje podataka o izlazu/ prekidu servera 
     * @return naredba izlaza
     * @throws IOException 
     */
    public String izlazIn() throws IOException{
        return readLine();
    }
    
    /**
     * Dobijanje izlazne niti 
     * @return izlazna nit
     */
    public IzlaznaNit getIzlaznaNit(){
        return izlaznaNit; 
    }
    
    /**
     * Dobijanje radne niti
     * @return radna nit
     */
    public RadnaNit getRadnaNit(){
        return radnaNit; 
    }
    
    /**
     * Slanje podataka o odjavi na server 
     */
    public void odijavaOut(){
        writeLine(BKSP_ODIJAVA);
    }
    
    /**
     * Dobijanje sesijskih objekata 
     * @return sesijski objekti 
     */
    public SesijskiObjekti getSesijskiObjekti(){
        return sesijskiObjekti;
    }
    
    /**
     * Postavljanje sesijskih objekata 
     * @param sesija novi sesisjki objekti 
     */
    public void setSesijskiObjekti(SesijskiObjekti sesija){
        sesijskiObjekti = sesija; 
        izlaznaNit.setSesijskiObjekti(sesija);
        radnaNit.setSesijskiObjekti(sesija);
    }
    
    /**
     * Dobijanje komunikacije sa serverom na nivou SK
     * @return adapter
     */
    public SKServerAdapter getSKServerAdapter(){
        return skServerAdapter; 
    }
    
    /**
     * Slanje podataka za promjenu podataka korisnika
     * @param oldusername staro korisnicko ime 
     * @param newusername novo korisnicko ime 
     * @param newname novo ime 
     * @param newsname novo prezime 
     * @param newaddress nova adresa
     * @param newjobs novi poslovi
     * @param newtelephone novi telefon 
     * @param newemails nove eposte
     * @param newwebs novi sajtovi
     */
    public void promenaParametaraOut(
            String oldusername, String newusername, 
            String newname, String newsname, String newaddress, 
            String newjobs, String newtelephone, String newemails, String newwebs){
        writeLine(BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA);
        writeLine(oldusername);
        writeLine(newusername);
        writeLine(newname);
        writeLine(newsname);
        writeLine(newaddress);
        writeLine(newjobs);
        writeLine(newemails);
        writeLine(newwebs);
        writeLine(newtelephone);
    }
    
    /**
     * Ocitavanje rezultata promjene parametara sa servera 
     * @return rezultati
     * @throws IOException 
     */
    public boolean promenaParametaraIn() throws IOException{
        String res = readLine();
        if(res.equals(BKSP_SUCCESS)){
            return true; 
        }else if(res.equals(BKSP_ERROR)){
            return false; 
        }
        return false; 
    }
   
    /**
     * Slanje podataka za novu sifru na server 
     * @param oldusername staro korisnicko ime 
     * @param newpassword nova sifra
     */
    public void promenaSifreOut(
            String oldusername, String newpassword){
        writeLine(BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA);
        writeLine(oldusername);
        writeLine(newpassword);
    }
    
    /**
     * Ocitavanje rezultata za promjenu sifre
     * @return rezultat
     * @throws IOException 
     */
    public boolean promenaSifreIn() throws IOException{
        String res = readLine();
        if(res.equals(BKSP_SUCCESS)){
            return true; 
        }else if(res.equals(BKSP_ERROR)){
            return false; 
        }
        return false; 
    }
}
