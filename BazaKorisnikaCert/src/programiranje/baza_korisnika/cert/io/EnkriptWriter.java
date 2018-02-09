/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.io;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 * Klasa za citac koji kriptuje podatke, <br> 
 * a podaci se ocitavaju {@link DekriptReader} metodama <br>
 * Sadrzi digitalnu envelopu kao sredstvo kriptovanja <br>
 * Sadrzi informaciju o zatvaranju toka <br>
 * Naslednica je klase PrintWriter
 * @author Mikec
 */
public class EnkriptWriter extends PrintWriter{ 
    private DigitalnaEnvelopa de; 
    protected boolean closed; 
    
    /**
     * Konstruktor sa digitalnom envelopom kao parametrom
     * @param de digitalna envelopa
     */
    public EnkriptWriter(DigitalnaEnvelopa de) {
        super(de.getWriter());
        this.de = de; 
    }
    
    /**
     * Konstruktor sa digitalnom envelopom
     * @param de digitalna envelopa
     * @param autoflush konstruktor koji koristi sa istim argumetima od PW
     */
    public EnkriptWriter(DigitalnaEnvelopa de, boolean autoflush) {
        super(de.getWriter(), autoflush);
        this.de = de;
    }

    /**
     * Odgovarajuci naslijedjeni println
     * @param x objekat
     */
    @Override
    public void println(Object x) {
        println(x.toString());
    }

    /**
     * Odgovarajuci naslijedjeni println
     * @param x string
     */
    @Override
    public synchronized void println(String x){
        try {
            de.getTackaDE().inicijalizacijaSimetricnogAlgoritma();
            de.slanjeSesijskogKljuca();
            
            try {
                byte [] by = x.getBytes();
                de.slanjePodataka(by);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(EnkriptWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Odgovarajuci naslijedjeni println 
     * @param x niz znakova
     */
    @Override
    public void println(char[] x) {
        println(new String(x));
    }

    /**
     * Odgovarajuci naslijedjeni println 
     * @param x broj dvostruke preciznosti
     */
    @Override
    public void println(double x) {
        println(Double.toString(x));
    }

    /**
     * Odgovarajuci naslijedjeni println
     * @param x broj jednostruke preciznosti 
     */
    @Override
    public void println(float x) {
        println(Float.toString(x)); 
    }

    /**
     * Odgovarajuci naslijedjeni println
     * @param x broj tipa long
     */
    @Override
    public void println(long x) {
        super.println(Long.toString(x)); 
    }
    
    /**
     * Odgovarajuci naslijedjeni println
     * @param x broj tipa int
     */
    @Override
    public void println(int x) {
        println(Integer.toString(x));
    }
    
    /**
     * Odgovarajuci naslijedjeni println
     * @param x znak
     */
    @Override
    public void println(char x) {
        println(Character.toString(x)); 
    }

    /**
     * Odgovarajuci naslijedjeni println
     * @param x logicki podatak
     */
    @Override
    public void println(boolean x) {
        println(Boolean.toString(x));
    }

    /**
     * Odgovarajuci naslijedjeni println
     */
    @Override
    public void println() {
        println("");
    }
    
    /**
     * Redefinisana metoda za izlaz
     */
    @Override
    public synchronized void close(){
        if(!closed) {
            super.close();
            closed = true; 
        }
    }
    
    /**
     * Dobijanje digitalne envelope
     */
    public DigitalnaEnvelopa getDigitalnaEnvelopa(){
        return de;
    }   
}
