/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.komunikacije.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_shell.cert.Certification;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.model.Sesija;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;

/**
 * Komunikacijska serdstva veze sa klijentom
 * @author Mikec
 */
public class ServerDataAdapter {
    private String no; 
    
    private Socket sock; 
    private PrintWriter write; 
    private BufferedReader read; 
    
    private Socket csock; 
    private PrintWriter cwrite; 
    private BufferedReader cread;
    private DigitalnaEnvelopa env; 
    private DigitalnaEnvelopa cenv; 
    
    private static int count = 0; 
    private Sesija s; 
    private String sessionType;
    
    /**
     * Konstruktor 
     * @param sock prikljucak klijenta za podatke
     * @param csock prikljucak klijenta za kontrole
     * @throws IOException 
     */
    public ServerDataAdapter(Socket sock, Socket csock) throws IOException, CertificateException, KeyStoreException, UnrecoverableKeyException{
        try {
            no = Integer.toString(++count);
            
            this.sock = sock;
            PrintWriter write = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
            BufferedReader read = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            
            env = new DigitalnaEnvelopa(read, write);
            String fileServerCert = ServerMainProgram.getServerKomunikator().getKonfiguracija().getServerCertificate(); 
            String fileServerPK = ServerMainProgram.getServerKomunikator().getKonfiguracija().getServerPrivateKey();
            Certification.initLocalKeysWithCertification(new File(fileServerCert), new File(fileServerPK),"server","server",env);
            env.applyCertAndCertServerCommunication();
            
            env.serverSwapPUK();
            
            UpravljanjeDokumentima ud = new UpravljanjeDokumentima(); 
            SignatureManager sim = new SignatureManager(env,ud);
            
            try{
                sim.initSymetric();
                sim.getDigitalnaEnvelopa().slanjeSesijskogKljuca();
                sim.initSignature();
            }
            catch(Exception ex){ ex.printStackTrace(); }
            
            this.read = new PotpisaniDekriptReader(sim);
            this.write = new PotpisaniEnkriptWriter(sim,true);
            
            this.csock = csock;
            PrintWriter cwrite = new PrintWriter(new OutputStreamWriter(csock.getOutputStream()), true);
            BufferedReader cread = new BufferedReader(new InputStreamReader(csock.getInputStream()));
            
            cenv = env.instancirajSaIstomTackom(cwrite, cread);
            
            UpravljanjeDokumentima cud = new UpravljanjeDokumentima(); 
            SignatureManager csim = new SignatureManager(cenv,cud);
            
            try{
                csim.initSymetric();
                csim.getDigitalnaEnvelopa().slanjeSesijskogKljuca();
                csim.initSignature(); 
            }
            catch(Exception ex){ ex.printStackTrace(); }
            
            this.cread = new PotpisaniDekriptReader(csim);
            this.cwrite = new PotpisaniEnkriptWriter(csim,true);
            
            System.out.println("Client number: "+ no + " on address "+(sock.getInetAddress().getHostAddress()));
            
            
            sessionType = readLine();
            writeLine(no);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ServerDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ocitavanje i dekodovanje teksta od klijenta
     * @return ocitani i dekodovani tekst
     * @throws IOException 
     */
    public synchronized String readLine() throws IOException{
        String msg = read.readLine();
        msg = msg.replace("#n", "\n");
        msg = msg.replace("#k", "#");
        return msg;
    }
    
    /**
     * Ocitavanje i dekodovanje kontrolnog teksta od klijenta
     * @return ocitani i dekodovani tekst
     * @throws IOException 
     */
    public String cReadLine() throws IOException{
        synchronized(csock){
            String msg = cread.readLine();
            msg = msg.replace("#n", "\n");
            msg = msg.replace("#k", "#");
            return msg;
        }
    }
    
    /**
     * Ulaz podataka o registraciji 
     * @return instanca autorizacionih podataka novog korisnika
     * @throws IOException 
     */
    public AutorizacioniPodaci registracijaIn() throws IOException{
        String id = readLine(); 
        String ime = readLine();
        String prezime = readLine();
        String kime = readLine();
        String pass = readLine();
        String addr = readLine();
        String phones = readLine();
        String jobs = readLine();
        String emails = readLine();
        String webs = readLine();
        AutorizacioniPodaci ap = 
                new AutorizacioniPodaci(
                    new OpisniPodaci(id,ime,prezime,addr,phones,jobs,emails,webs), 
                    new AutentifikacioniPodaci(kime, pass)
        );
        System.out.println("Registration: "+ap+" on connection "+no);
        return ap; 
    }
    
    /**
     * Izlaz povratnih podataka za registraciju 
     * @param ok da li je prosla registracija 
     * @param msg poruka o gresci 
     */
    public void registracijaOut(String ok, String msg){
        writeLine(ok);
        writeLine(msg);
        System.out.println("Registration on connection "+no+ ": "+ok+": session "+msg);
    }
    
    /**
     * Pisanje i kodovanje teksta ka klijentu
     * @param msg tekst
     */
    public synchronized void writeLine(String msg){
        msg = msg.replace("#", "#k");
        msg = msg.replace("\n", "#n");
        write.println(msg);
    }
    
    /**
     * Pisanje i kodovanje kontrolnog teksta ka klijentu 
     * @param msg kontrolni tekst
     */
    public void cWriteLine(String msg){
        synchronized(csock){
            msg = msg.replace("#", "#k");
            msg = msg.replace("\n", "#n");
            cwrite.println(msg);
        }
    }
    
    /**
     * Izlaz
     * @throws IOException 
     */
    public void close() throws IOException{
        read.close();
        write.close();
        sock.close();
        csock.close();
        System.out.println("Closed client no: "+ no);
    }
    
    /**
     * Dobijanje konekcijskog identifikatora
     * @return konekcijski identifikator 
     */
    public String getConnectionId(){
        return no; 
    }
    
    /**
     * Dobijanje sesije kao instance
     * @return instanca sesije 
     */
    public Sesija getSesija(){
        return s;
    }
    
    /**
     * Postavljanje sesije kao instance
     * @param s instanca sesije 
     */
    public void setSesija(Sesija s){
        this.s = s;
    }
    
    /**
     * Slanje podataka prema klijentu 
     */
    public void posaljiPodatke(){
        Korisnik k = s.getKorisnik();
        writeLine(k.getAutentifikacija().getUsername());
        writeLine(k.getDeskripcija().getFirstname());
        writeLine(k.getDeskripcija().getLastname());
        writeLine(k.getDeskripcija().getIdentificator());
        writeLine(k.getDeskripcija().getAdress());
        writeLine(k.getDeskripcija().getPhones());
        writeLine(k.getDeskripcija().getJobs());
        writeLine(k.getDeskripcija().getEmails());
        writeLine(k.getDeskripcija().getWebs());
    }
    
    /**
     * Dobijanje tipa sesije (vremenski ogranicena - veb i obicna)
     * @return tip sesije 
     */
    public String getSessionType(){
        return sessionType;
    }
}
