/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.io.PotpisaniDekriptReader;
import programiranje.baza_korisnika.cert.io.PotpisaniEnkriptWriter;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;
import programiranje.baza_korisnika_web.global.SesijskiObjekti;

/**
 * Klasa koja bi trebala biti radna nit servera
 * @author Mikec
 */
public class RadnaNit extends Thread{
    private AdapterServera server;  
    private SesijskiObjekti sesijskiObjekti; 
    
    /**
     * Konstruktor radne niti 
     * @param server komunikacija sa serverom BK
     */
    public RadnaNit(AdapterServera server){
        this.server = server; 
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
     * @param sesija sesija 
     */
    public void setSesijskiObjekti(SesijskiObjekti sesija){
        sesijskiObjekti = sesija; 
    }
}
