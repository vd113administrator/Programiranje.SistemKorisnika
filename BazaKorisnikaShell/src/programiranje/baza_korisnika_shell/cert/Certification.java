/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.cert;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import programiranje.baza_korisnika.cert.pki.CertificateManager;
import programiranje.baza_korisnika.cert.pki.PrivateKeyManager;
import programiranje.baza_korisnika.cert.pki.X509Manager;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 *
 * @author Mikec
 */
public final class Certification {
    private Certification(){
    }
    
    public static void initCommunicationWithCertificateFile(File certfile, DigitalnaEnvelopa de) 
            throws CertificateException, IOException, NoSuchAlgorithmException{
        try{
            CertificateManager cmg = new CertificateManager(certfile);
            de.getTackaDE().getAsimetricniAlgoritam().setCommunicationKey(cmg);
        }catch(Exception ex){
        }
    }
    
    public static void installServerCertificateFile(File certfile, DigitalnaEnvelopa de) 
            throws CertificateException, IOException, NoSuchAlgorithmException{
        try{
            CertificateManager cmg = new CertificateManager(certfile);
            X509Manager mgr = new X509Manager().setSertifikat(cmg);
            de.setCertification(mgr);
        }catch(Exception ex){
        }
    }
    
    public static void initLocalKeysWithCertification(
            File cerfile, File jksFile, String user, String password, DigitalnaEnvelopa de)
            throws CertificateException, IOException, NoSuchAlgorithmException,
            KeyStoreException, UnrecoverableKeyException{
        try{
            CertificateManager cmg = new CertificateManager(cerfile);
            PrivateKeyManager pkmg = new PrivateKeyManager(jksFile, user, password);
            X509Manager mgr = new X509Manager().setPrivatniKljuc(pkmg).setSertifikat(cmg);
            de.setCertification(mgr);
        }catch(Exception ex){
        }
    }
}
