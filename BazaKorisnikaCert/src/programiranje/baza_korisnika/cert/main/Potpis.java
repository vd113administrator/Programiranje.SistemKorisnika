/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika.cert.util.SignatureManager;
import programiranje.baza_korisnika.cert.util.StringTools;

/**
 *
 * @author Mikec
 */
public class Potpis {
    private static Thread client;
    private static Thread server; 
    private static boolean serverOn;
    private static Object synchron = new Object(); 
    private static Object printSynch = new Object(); 
    
    public static void main(String ... args){
        client = new Thread(()->client(args));
        server = new Thread(()->server(args));
        server.start(); 
        client.start();
    }
    
    public static void client(String ... args){
        try {
            
            synchronized(synchron){
                if(!serverOn) synchron.wait();
            }
            
            Socket sc = new Socket(InetAddress.getByName("127.0.0.1"),9999);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()),true);
            BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            
            DigitalnaEnvelopa de = new DigitalnaEnvelopa(br,pw);
            UpravljanjeDokumentima ud = new UpravljanjeDokumentima();
            SignatureManager sm = new SignatureManager(de,ud); 
            StringTools st = sm.getStringTools();
            
            sm.initAsymetric();
            sm.initSignature();
            
            sm.sendPublicKey();
            sm.receivePublicKey();
            
            de.prijemSesijskogKljuca();
            
            String stext = st.receiveDocument()+"\n"+st.receiveDocument();
            st.sendDocument("Pozdrav sa klijentske strane ...");
            st.sendDocument("Pozdrav sa klijentske strane ...");
            
            br.close();
            pw.close();
            sc.close();
            
            synchronized(printSynch){
                System.out.println("JAVNI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getJavniKljuc());

                System.out.println("PRIVATNI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getTajniKljuc());

                System.out.println("SESIJSKI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getSesijskiKljuc());

                System.out.println("KOMUNIKACIONI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getKomunikacijskiKljuc());

                System.out.println("PRIMLJENI PODACI");
                System.out.println("========================");
                System.out.println(stext);
                
                System.out.println();
            }
        } catch (Exception ex) {
            Logger.getLogger(Potpis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void server(String ... args) {
        try {
            synchronized(synchron){serverOn=true; synchron.notify();}
            
            ServerSocket ss = new ServerSocket(9999);
            Socket sc = ss.accept();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()),true);
            BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            
            DigitalnaEnvelopa de = new DigitalnaEnvelopa(br,pw);
            UpravljanjeDokumentima ud = new UpravljanjeDokumentima();
            SignatureManager sm = new SignatureManager(de,ud);
            StringTools st = sm.getStringTools();
            
            sm.initAsymetric();
            sm.initSymetric();
            sm.initSignature();
            
            sm.receivePublicKey();
            sm.sendPublicKey();
            
            de.slanjeSesijskogKljuca();
            
            st.sendDocument("Pozdrav sa serverske strane");
            st.sendDocument("Serversko virijeme "+new Date());
            String stext = st.receiveDocument()+"\n"+st.receiveDocument();
            
            br.close();
            pw.close();
            sc.close();
            ss.close();
            
            synchronized(printSynch){
                System.out.println("JAVNI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getJavniKljuc());

                System.out.println("PRIVATNI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getTajniKljuc());

                System.out.println("SESIJSKI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getSesijskiKljuc());

                System.out.println("KOMUNIKACIONI KLJUC");
                System.out.println("========================");
                System.out.println(de.getTackaDE().getKomunikacijskiKljuc());

                System.out.println("PRIMLJENI PODACI");
                System.out.println("========================");
                System.out.println(stext);
                
                System.out.println();
            }
        } catch (Exception ex) {
            Logger.getLogger(Potpis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
