/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.io;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.util.SignatureManager;

/**
 * Tok koji kriptuje i potpisuje podatke
 * Slicno klasi {@link PotpisaniDekriptReader}
 * @author Mikec
 */
public class PotpisaniEnkriptWriter extends EnkriptWriter{
    private SignatureManager sm; 

    /**
     * Konstruktor sa SignatureManager-om 
     * @param sm menadzer potpisivanja
     */
    public PotpisaniEnkriptWriter(SignatureManager sm){
        super(sm.getDigitalnaEnvelopa());
        this.sm = sm; 
    }
    
    /**
     * Konstruktor sa SignatureManager-om
     * @param sm menadzer potpisivanja
     * @param autoflush parametar za PW konstrukcije
     */
    public PotpisaniEnkriptWriter(SignatureManager sm, boolean autoflush){
        super(sm.getDigitalnaEnvelopa(), autoflush);
        this.sm = sm; 
    }

    /**
     * Preklopljena metoda
     * @param x string
     */
    @Override
    public synchronized void println(String x) {
        try {
            sm.getDigitalnaEnvelopa().getTackaDE().inicijalizacijaSimetricnogAlgoritma();
            sm.getDigitalnaEnvelopa().slanjeSesijskogKljuca();
            sm.getStringTools().sendDocument(x);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PotpisaniEnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Preklopljena metoda 
     * @param x objekat
     */
    @Override
    public void println(Object x) {
        println(x.toString());
    }
    
    /**
     * Preklopljena metoda
     * @param x niz karaktera  
     */
    @Override
    public void println(char[] x) {
        println(new String(x));
    }

    /**
     * Preklopljena metoda
     * @param x broj dvostruke preciznosti
     */
    @Override
    public void println(double x) {
        println(Double.toString(x));
    }
    
    /**
     * Preklopljena metoda 
     * @param x broj jednostruke preciznosti
     */
    @Override
    public void println(float x) {
        println(Float.toString(x)); 
    }

    /**
     * Preklopljena metoda 
     * @param x podatak tipa long
     */
    @Override
    public void println(long x) {
        super.println(Long.toString(x)); 
    }

    /**
     * Preklopljena metoda
     * @param x podatak tipa int
     */
    @Override
    public void println(int x) {
        println(Integer.toString(x));
    }

    /**
     * Preklopljena metoda
     * @param x podatak tipa char
     */
    @Override
    public void println(char x) {
        println(Character.toString(x)); 
    }

    /**
     * Preklopljena metoda 
     * @param x podatak tipa  boolean 
     */
    @Override
    public void println(boolean x) {
        println(Boolean.toString(x));
    }

    /**
     * Preklopljena metoda 
     */
    @Override
    public void println() {
        println(""); 
    }
    
    /**
     * Metoda za izlaz 
     */
    @Override
    public synchronized void close(){
        super.close();
    }
    
    /**
     * Dobijanje menadzera za potpisivanje
     * @return menadzer za potpisivanje
     */
    public SignatureManager getSignatureManager(){
        return sm;
    }
}
