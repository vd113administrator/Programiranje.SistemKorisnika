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
 * Citac potpisanik enkriptovanih poruka<br>
 * Naslednik {@link DekriptReader}<br>
 * Ocitava poruke pisane {@link PotpisaniEnkriptWriter}<br>
 * Dodatno sadrzi polje SignatureManager koje je alat za potpisivanje. 
 * Pored tajnosti podataka pri prenosu provjerava i integritet i autorizaciju po kljucevima.
 * @author Mikec
 */
public class PotpisaniDekriptReader extends DekriptReader{
    private SignatureManager sm; 
    protected boolean closed; 
    
    /**
     * Konstruktor kojim se daje za parametar menadzer potpisivanja
     * @param sm 
     */
    public PotpisaniDekriptReader(SignatureManager sm){
        super(sm.getDigitalnaEnvelopa());
        this.sm=sm; 
    }
    
    
    /**
     * Ocitavanje linije 
     * @return poslani tekst
     * @throws IOException 
     */
    @Override
    public synchronized String readLine() throws IOException {
        try {
            sm.getDigitalnaEnvelopa().prijemSesijskogKljuca();
            return sm.getStringTools().receiveDocument();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PotpisaniDekriptReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metoda za izlaz
     * @throws IOException 
     */
    @Override
    public synchronized void close() throws IOException{
        super.close();
    }
    
    /**
     * Dobijanje menadzera potpisa
     * @return menadzer
     */
    public SignatureManager getSignatureManager(){
        return sm;
    }
}
