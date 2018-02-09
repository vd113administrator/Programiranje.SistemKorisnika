/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javafx.util.Pair;
import programiranje.baza_korisnika_shell.alati.server.vezivanje.VezivanjeControl;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.baza_korisnika_shell.kontrola.server.DeregistracijaControl;
import programiranje.baza_korisnika_shell.kontrola.server.OdjavaControl;
import programiranje.baza_korisnika_shell.kontrola.server.PrijavaControl;
import programiranje.baza_korisnika_shell.kontrola.server.RegistracijaControl;
import programiranje.baza_korisnika_shell.server.extensions.BKSPProtocolExtension;

/**
 * Osnovna nit za izvodjenje pojedinih klijent server komunikacija 
 * @author Mikec
 */
public class ServerMainThread extends Thread implements ServerTemplate{
    private ServerDataAdapter clientAdapter; 
    private String sessionType;
    private Timer timer; 
    private boolean closed; 
    
    public static final String BKSP_REGISTRACIJA = "REGISTRACIJA";
    public static final String BKSP_DEREGISTRACIJA = "DEREGISTRACIJA";
    public static final String BKSP_PRIJAVA = "PRIJAVA";
    public static final String BKSP_ODIJAVA = "ODJAVA";
    public static final String BKSP_CLOSE = "IZLAZ";
    public static final String BKSP_PODACI = "PODACI";
    
    public static final String BKSP_SUCCESS = "USPJESNO";
    public static final String BKSP_ERROR = "GRESKA";
    
    public static final String PBKSP_CLOSE = "IZLAZ"; 
    public static final String PBKSP_STOP = "ZAUSTAVLJANJE";
    
    public static final String BKSP_N1_VEZANJE = "VEZANJE";
    
    public static final String BKSP_N2_AKTIVNOST_PROMENA_TIPA_KORISNIKA  = "BKSP_N2_AKTIVNOST_PROMENA_TIPA_KORISNIKA"; 
    public static final String BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA = "BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA";
    public static final String BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA = "BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA";
    
    public static final String BKSP_N3_PROVJERA_SIFRE = "PROVJERA_SIFRE";
    
    public static final String SESSIONTYPE_BKSP_BASIC = "BASIC"; 
    public static final String SESSIONTYPE_BKSP_TIMED = "TIMED"; 
            
    private static List<ServerMainThread> clients = new ArrayList<>();
    private BKSPProtocolExtension extension; 
    
    /**
     * Postavljenje ekstenzije aplikacije i protokola BKSP
     * @param extension ekstenzija 
     * @return lokalna instanca
     */
    public ServerMainThread setBKSPPotocolExtension(BKSPProtocolExtension extension){
        this.extension = extension;
        return this; 
    }
    
    /**
     * Dobij ekstenziju aplikacije i protokola BKSP
     * @return ekstenzija 
     */
    public BKSPProtocolExtension getBKSPPotocolExtension(){
        return extension;
    }
    
    /**
     * Prekid svih komunikacija
     */
    public static void closeAll(){
        synchronized(clients){
            for(ServerMainThread smt: clients){
                if(!smt.closed) smt.clientAdapter.cWriteLine(PBKSP_STOP);
            }
        }
    }
    
    /**
     * Konstruktor
     * @param sock prikljucak klijenta
     * @param csock klijentov prikljucak za kontrolu
     * @throws IOException 
     */
    public ServerMainThread(Socket sock, Socket csock) 
            throws IOException, CertificateException, KeyStoreException, 
            UnrecoverableKeyException{
        clientAdapter = new ServerDataAdapter(sock, csock);
        sessionType = clientAdapter.getSessionType(); 
        synchronized(clients){ clients.add(this); }
    }
    
    /**
     * Proces komunikacije sa klijentom 
     */
    @Override
    public void run(){
        try {
            while(true){
                if(sessionType.equals(SESSIONTYPE_BKSP_TIMED)){
                    timer=new Timer();
                    ServerGlobalCommunicator komunikator = ServerMainProgram.getServerKomunikator();
                    int time = komunikator.getKonfiguracija().getTimedSessionExitPeriod();
                    timer.schedule(new TimedSessionExitTask(this,clientAdapter,timer), time);
                }
                String line = clientAdapter.readLine();
                if(sessionType.equals(SESSIONTYPE_BKSP_TIMED)){
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
                if(line.equals(BKSP_CLOSE)) {
                    closed = true; 
                    clientAdapter.cWriteLine(PBKSP_CLOSE);
                    clientAdapter.close();
                    synchronized(clients){ clients.remove(this); }
                    break; 
                }
                else if(line.equals(BKSP_REGISTRACIJA)) {
                   AutorizacioniPodaci ap = clientAdapter.registracijaIn();
                   Pair<Boolean,String> ok = RegistracijaControl.dodajKorisnika(ap);
                   if(ok.getKey()) {
                       String msg = 
                          PrijavaControl.otvoriSesiju(ap.getAutentifikacija(), clientAdapter);
                          clientAdapter.registracijaOut(BKSP_SUCCESS,msg);
                   }
                   else   clientAdapter.registracijaOut(BKSP_ERROR,ok.getValue());
                }
                else if(line.equals(BKSP_ODIJAVA)){
                    OdjavaControl.odijava(clientAdapter.getSesija());
                }
                else if(line.equals(BKSP_DEREGISTRACIJA)){
                    String uname = clientAdapter.readLine();
                    String pass  = clientAdapter.readLine();
                    boolean res = 
                    DeregistracijaControl.deregistruj(clientAdapter.getSesija(),uname,pass);
                    if(res)clientAdapter.writeLine(BKSP_SUCCESS);
                    else   clientAdapter.writeLine(BKSP_ERROR);
                }
                else if(line.equals(BKSP_PRIJAVA)){
                    String uname = clientAdapter.readLine();
                    String pass  = clientAdapter.readLine();
                    String res = 
                    PrijavaControl.otvoriSesiju(new AutentifikacioniPodaci(uname,pass), clientAdapter);
                    if(res==null){
                        clientAdapter.writeLine(BKSP_ERROR);
                        res = "Pogresna autentifikacija. \nNeuspjesna prijava.";
                        clientAdapter.writeLine(res);
                    }
                    else{
                        clientAdapter.writeLine(BKSP_SUCCESS);
                        clientAdapter.writeLine(res);
                    }
                    System.out.println("Login "+clientAdapter.getConnectionId()+" : session "+res.replace("\n", " "));
                }
                else if(line.equals(BKSP_PODACI)){
                    if(clientAdapter.getSesija()==null){
                        clientAdapter.writeLine(BKSP_ERROR);
                        clientAdapter.writeLine("Nema sesije.");
                    }
                    else{
                        clientAdapter.writeLine(BKSP_SUCCESS);
                        clientAdapter.posaljiPodatke();
                    }
                }
                else if(line.equals(BKSP_N1_VEZANJE)){
                    if(clientAdapter.getSesija()==null){
                        clientAdapter.writeLine(BKSP_ERROR);
                    }
                    else{
                        clientAdapter.writeLine(BKSP_SUCCESS); 
                        VezivanjeControl.getVezivanjeServer().poziv();
                    }
                }
                else if(line.equals(BKSP_N2_AKTIVNOST_PROMENA_PARAMETARA_KORISNIKA)){
                    if(clientAdapter.getSesija()==null){
                        clientAdapter.writeLine(BKSP_ERROR);  
                    }
                    else{
                        String oldusername = clientAdapter.readLine();
                        String newusername = clientAdapter.readLine();
                        String newname = clientAdapter.readLine();
                        String newsurname = clientAdapter.readLine(); 
                        String newaddress = clientAdapter.readLine();
                        String newjobs = clientAdapter.readLine(); 
                        String newemails = clientAdapter.readLine(); 
                        String newwebs = clientAdapter.readLine();
                        String newtelephone = clientAdapter.readLine();
                        boolean res = RegistracijaControl.promenaParametara(
                                oldusername, newusername, 
                                newname, newsurname, 
                                newaddress, newtelephone,
                                newemails, newwebs, newjobs);
                        if(res) System.out.println("Promena parametara korisnika.");
                        else System.out.println("Neuspesna promena parametara korisnika.");
                        if(res) clientAdapter.writeLine(BKSP_SUCCESS); 
                        else clientAdapter.writeLine(BKSP_ERROR);  
                    }
                }
                else if(line.equals(BKSP_N2_AKTIVNOST_PROMENA_SIFRE_KORISNIKA)){
                    if(clientAdapter.getSesija()==null){
                        clientAdapter.writeLine(BKSP_ERROR);
                    }
                    else{
                        String oldusername = clientAdapter.readLine(); 
                        String newpassword = clientAdapter.readLine();
                        boolean res = RegistracijaControl.promenaSifre(oldusername, newpassword);
                        if(res) System.out.println("Promena sifre korisnika.");
                        else System.out.println("Neuspesna promena sifre korisnika.");
                        if(res) clientAdapter.writeLine(BKSP_SUCCESS);
                        else clientAdapter.writeLine(BKSP_ERROR);
                    }  
                }
                else if(line.equals(BKSP_N2_AKTIVNOST_PROMENA_TIPA_KORISNIKA)){
                    if(clientAdapter.getSesija()==null){
                        clientAdapter.writeLine(BKSP_ERROR);
                    }
                    else{
                        String oldusername = clientAdapter.readLine(); 
                        String newtip = clientAdapter.readLine();
                        boolean res = RegistracijaControl.promenaTipa(oldusername, newtip);
                        if(res) System.out.println("Promena tipa korisnika.");
                        else System.out.println("Neuspesna promena tipa korisnika.");
                        if(res) clientAdapter.writeLine(BKSP_SUCCESS);
                        else clientAdapter.writeLine(BKSP_ERROR);
                    }
                }
                else if(line.equals(BKSP_N3_PROVJERA_SIFRE)){
                    String uname = clientAdapter.getSesija().getKorisnik().getAutentifikacija().getUsername();
                    String pass  = clientAdapter.readLine();
                    boolean res = RegistracijaControl.proveraSifre(uname, pass);
                    if(res) System.out.println("Provera sifre - sifra je vazeca za "+uname);
                    else System.out.println("Provera sifre - sifra nije vazeca za "+uname);
                    clientAdapter.writeLine(Boolean.toString(res));
                }
                else if(this.extension!=null){
                    extension.run(line, clientAdapter, ServerMainProgram.getUserDatabaseAdapter());
                }
            }
        } catch (IOException ex) {
            if(clientAdapter.getSesija()!=null)
                OdjavaControl.odijava(clientAdapter.getSesija());
            System.out.println("Prekid komunikacije : " + clientAdapter.getConnectionId()); 
        }
    }
    
    /**
     * Dobijanje sesijskog tipa 
     * @return sesijski tip 
     */
    public String getSessionType(){
        return sessionType;
    }
    
    /**
     * Ugasenost
     * @return da li je ugasena nit
     */
    public boolean isClosed(){
        return closed;
    }
}
