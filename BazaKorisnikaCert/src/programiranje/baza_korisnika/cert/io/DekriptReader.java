/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 * Citac koji koristi enkripciju i ostvaruje tajnost pri prenosu <br>
 * Ocitava podatke koji su poslani metodama klase {@link EnkriptWriter} <br>
 * Sadrzi polje digitalne envelope, a samo je citac tipa {@link BufferedReader}<br>
 * Sadrzi i podatke o zatvaranju (closed)
 * @author Mikec
 */
public class DekriptReader extends BufferedReader{
    private DigitalnaEnvelopa de;
    protected boolean closed;
    
    /**
     * Konstruktor sa digitalnom envelopom kao parametrom
     * @param de digitalna envelopa
     */
    public DekriptReader(DigitalnaEnvelopa de) {
        super(de.getReader());
        this.de = de; 
    }
      
    /**
     * Preklopljena metoda za ocitavanje linije, ali sa dekriptovanjem po digitalnoj envelopi
     * @return vrijednost ocitavanja 
     * @throws IOException 
     */
    @Override
    public synchronized String readLine() throws IOException {
        try {
            de.prijemSesijskogKljuca();
            try {
                byte[] by = de.prijemPodataka();
                return new String(by);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    /**
     * Preklopljena metoda za izlazak
     * @throws IOException 
     */
    @Override
    public synchronized void close() throws  IOException{
        if(!closed){
            super.close();
            closed = true; 
        }
    }
    
    /**
     * Dobijanje digitalne envelope
     * @return digitalna envelopa
     */
    public DigitalnaEnvelopa getDigitalnaEnvelopa(){
        return de;
    }
}
