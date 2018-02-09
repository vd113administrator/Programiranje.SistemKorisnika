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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.io.DekriptReader;
import programiranje.baza_korisnika.cert.io.EnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 *
 * @author Mikec
 */
public class ClientIO {
    public static void main(String ... args) throws UnknownHostException, IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Socket sc = new Socket(InetAddress.getByName("127.0.0.1"),9999);
        
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()),true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        
        DigitalnaEnvelopa de = new DigitalnaEnvelopa(br,pw); 
        de.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
        
        de.slanjeJavnogKljuca();
        de.prijemJavnogKljuca();
        
        EnkriptWriter ew = new EnkriptWriter(de,true);
        DekriptReader dr = new DekriptReader(de);
       
        ew.println("Pozdrav od klijenta 1 ...");
        ew.println("Pozdrav od klijenta 2 ...");
        ew.println("Pozdrav od klijenta 3 ...");
        System.out.println(dr.readLine());
        System.out.println(dr.readLine());
        System.out.println(dr.readLine());
        
        de.slanjeSignala();

        br.close(); 
        pw.close(); 
        sc.close();
      
    }
}
