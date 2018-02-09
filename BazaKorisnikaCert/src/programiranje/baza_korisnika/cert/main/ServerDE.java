/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 *
 * @author Mikec
 */
public class ServerDE {
    public static void main(String ... args) throws IOException, NoSuchAlgorithmException, 
            NoSuchPaddingException, ClassNotFoundException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException{
        ServerSocket ss = new ServerSocket(9999);
        Socket sc = ss.accept();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()),true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        
        DigitalnaEnvelopa de = new DigitalnaEnvelopa(br,pw); 
        de.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        de.getTackaDE().inicijalizacijaSimetricnogAlgoritma();
        
        de.prijemJavnogKljuca();
        de.slanjeJavnogKljuca();
        
        de.slanjeSesijskogKljuca();
        
        byte[] text = "Pozdravni tekst sa servera ...".getBytes();
        de.slanjePodataka(text);
        
        byte[] text2 = de.prijemPodataka(); 
        String stext = new String(text2); 
        
        de.prijemSignala();
        
        br.close(); 
        pw.close(); 
        sc.close();
        ss.close();
        
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
    }
}
