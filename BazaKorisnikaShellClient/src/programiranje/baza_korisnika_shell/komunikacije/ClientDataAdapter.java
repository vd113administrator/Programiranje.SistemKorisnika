/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.komunikacije;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika_shell.cert.Certification;
import programiranje.baza_korisnika_shell.klijent.ApplicationMainProgram;
import static programiranje.baza_korisnika_shell.klijent.ApplicationStatics.serverControlPort;
import static programiranje.baza_korisnika_shell.klijent.ApplicationStatics.serverPort;
import programiranje.baza_korisnika_shell.klijent.GlobalApplicationCommunicator;
import programiranje.baza_korisnika_shell.klijent.vezanje.VezivanjeGlobalKomunikator;

/**
 * Komunikacija aplikacije prema serveru 
 * @author Mikec
 */
public class ClientDataAdapter {
    private String no;
    
    private Socket sock; 
    private BufferedReader read; 
    private PrintWriter write; 
    private String sessionId;
    private DigitalnaEnvelopa env; 
    
    private Socket csock; 
    private ServerCloseThread serverClose; 
    
    public static final String BKSP_CLOSE = "IZLAZ";
    public static final String BKSP_REGISTRACIJA = "REGISTRACIJA";
    public static final String BKSP_DEREGISTRACIJA = "DEREGISTRACIJA";
    public static final String BKSP_PRIJAVA = "PRIJAVA";
    public static final String BKSP_ODIJAVA = "ODJAVA";
    public static final String BKSP_PODACI = "PODACI";
    
    public static final String BKSP_SUCCESS = "USPJESNO";
    public static final String BKSP_ERROR = "GRESKA";
    
    public static final String SESSIONTYPE_BKSP_BASIC = "BASIC"; 
    public static final String SESSIONTYPE_BKSP_TIMED = "TIMED";
    
    public GlobalApplicationCommunicator komunikator;
    
    /**
     * Konstruktor
     * @param komunikator aplikaciona kontrola funkcionalnosti
     */
    public ClientDataAdapter(GlobalApplicationCommunicator komunikator) throws CertificateException{
        try {
            this.komunikator = komunikator;
            InetAddress addr = InetAddress.getByName(komunikator.getKonfiguracija().getServerAddress());
           
            sock = new Socket(addr, serverPort);
            csock = new Socket(addr, serverControlPort);
            
            BufferedReader read = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter write = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
            
            env = new DigitalnaEnvelopa(read, write);
            env.applyCertAndCertServerCommunication(null);
            
            String fileServerCert = ApplicationMainProgram
                    .getGlobalApplicationCommunicator().getKonfiguracija().getServerCertificate();
            env.applyCertAndCertServerCommunication(null);
            Certification.installServerCertificateFile(new File(fileServerCert), env);
            
            env.clientSwapPUK();
           
            serverClose = new ServerCloseThread(csock, this);
            VezivanjeGlobalKomunikator.setServerCloseThread(serverClose);
            serverClose.start();
            
            UpravljanjeDokumentima ud = new UpravljanjeDokumentima(); 
            SignatureManager sim = new SignatureManager(env, ud);
        
            try{
                sim.initSymetric();
                sim.getDigitalnaEnvelopa().prijemSesijskogKljuca();
                sim.initSignature(); 
            }catch(Exception ex){
            }
            
            this.read = new PotpisaniDekriptReader(sim);
            this.write = new PotpisaniEnkriptWriter(sim,true);
            
            writeLine(SESSIONTYPE_BKSP_BASIC);
            no = readLine();
        } catch (UnknownHostException e1) {
            ApplicationMainProgram.termiante();
        } catch (IOException e2) {
            ApplicationMainProgram.termiante();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ClientDataAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ocitavanje i dekodovanje teksta sa servera
     * @return viselinijski tekst
     * @throws IOException 
     */
    public String readLine() throws IOException{
        String msg = read.readLine();
        msg = msg.replace("#n", "\n");
        msg = msg.replace("#k", "#");
        return msg;
    }
    
    /**
     * Zapis teksta na server
     * @param msg poruka
     */
    public void writeLine(String msg){
        msg = msg.replace("#", "#k");
        msg = msg.replace("\n", "#n");
        write.println(msg);
    }
    
    /**
     * Izvrsavanje deregistracije
     * @param uname korisnicko ime 
     * @param pass sifra
     * @return rezultat
     * @throws IOException 
     */
    public boolean deregistracija(String uname, String pass) throws IOException{
        writeLine(BKSP_DEREGISTRACIJA);
        writeLine(uname);
        writeLine(pass);
        String res = readLine(); 
        if(res.equals(BKSP_ERROR))   return false; 
        sessionId = null;
        return true;
    }
    
    /**
     * Izvodjenje prijave
     * @param name korisnicko ime 
     * @param uname sifra
     * @throws IOException 
     */
    public void prijava(String name, String uname) throws IOException{
        writeLine(BKSP_PRIJAVA);
        writeLine(name);
        writeLine(uname);
        
        String ok = readLine(); 
        String msg = readLine();
        
        if(ok.equals(BKSP_SUCCESS))
            sessionId = msg; 
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Korisnik");
            alert.setHeaderText("Greska pri prijavi");
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }
    
    /**
     * Izvrsavanje odjave
     */
    public void odijava(){
        writeLine(BKSP_ODIJAVA);
        sessionId = null;
    }
    
    /**
     * Izvrsavanje registracije
     * @param id identifikator 
     * @param ime ime
     * @param prezime prezime
     * @param kime korisnicko ime
     * @param pass sifra 
     * @param addr adresa 
     * @param phones telefoni 
     * @param jobs poslovi
     * @param emails el. posta
     * @param webs sajtovi
     * @throws IOException 
     */
    public void registracija(String id, String ime, String prezime, String kime, String pass,
                             String addr, String phones, String jobs, String emails, String webs) 
                throws IOException{
        writeLine(BKSP_REGISTRACIJA);
        writeLine(id);
        writeLine(ime);
        writeLine(prezime);
        writeLine(kime);
        writeLine(pass);
        writeLine(addr);
        writeLine(phones);
        writeLine(jobs);
        writeLine(emails);
        writeLine(webs);
        String ok = readLine(); 
        String msg = readLine();
        if(ok.equals(BKSP_SUCCESS))
            sessionId = msg; 
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Korisnik");
            alert.setHeaderText("Greska pri registraciji");
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }
    
    /**
     * Izvrsavanje izlaza
     * @throws IOException 
     */
    public synchronized void close() throws IOException{
        if(sessionId != null) writeLine(BKSP_ODIJAVA);
        writeLine(BKSP_CLOSE);
        read.close();
        write.close();
        sock.close();
    }
    
    /**
     * Dobijanje konekcijskog identifikatora
     * @return konekcijski identifikator
     */
    public String getConnectionId(){
        return no; 
    }
    
    /**
     * Dobijanje sesijskog identifikatora 
     * @return sesijski identifikator 
     */
    public String getSessionId(){
        return sessionId; 
    }
    
    /**
     * Ocitavanje podataka 
     * @return ocitani podaci
     * @throws IOException 
     */
    public List<String> ocitavanjePodataka() throws IOException{
        ArrayList<String> list = new ArrayList<>(); 
        writeLine(BKSP_PODACI);
        String res = readLine();
        if(res.equals(BKSP_SUCCESS)){
            list.add("Korisnicko ime : "+ readLine());
            list.add("\nIme : "+readLine());
            list.add("\nPrezime : "+readLine());
            list.add("\nIdentifikator : "+readLine());
            String str = "";
            str = readLine();
            if(str.length()>0){
                list.add("\nAdresa: ");
                list.add(tabtext(str));
            }
            str = readLine();
            if(str.length()>0){
                list.add("\nTelefon: ");
                list.add(tabtext(str));
            }
            str = readLine();
            if(str.length()>0){
                list.add("\nPosao: ");
                list.add(tabtext(str));
            }
            str = readLine();
            if(str.length()>0){
                list.add("\nEl. posta: ");
                list.add(tabtext(str));
            }
                        str = readLine();
            if(str.length()>0){
                list.add("\nVeb sajtovi: ");
                list.add(tabtext(str));
            }
        }
        else{
            list.add(tabtext(readLine()));
        }
        return list; 
    }
    
    /**
     * Tabulacija pasusa 
     * @param text
     * @return tabuliran tekst
     */
    private String tabtext(String text){
        String txt="";
        for(String s: text.split("\n"))
            txt+="\t"+s+"\n";
        return txt; 
    }
    
    /**
     * @return digitalna envelopa  
     */
    public DigitalnaEnvelopa getDigitalnaEnvelopa(){
        return env;
    }
}
