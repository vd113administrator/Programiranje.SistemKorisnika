/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.pki;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 *
 * @author Mikec
 */
public class CertificateManager  implements Serializable{
    private X509Certificate cert; 
    public CertificateManager(X509Certificate cert){
        this.cert = cert; 
    }
    public CertificateManager(File cer) 
            throws FileNotFoundException, CertificateException, IOException{
      CertificateFactory fact = CertificateFactory.getInstance("X.509");
      FileInputStream is = new FileInputStream (cer);
      cert = (X509Certificate) fact.generateCertificate(is);
      is.close();
    }
    public CertificateManager(byte[] cer) 
            throws FileNotFoundException, CertificateException, IOException{
      CertificateFactory fact = CertificateFactory.getInstance("X.509");
      ByteArrayInputStream is = new ByteArrayInputStream (cer);
      cert = (X509Certificate) fact.generateCertificate(is);
      is.close();
    }
    
    public X509Certificate getCert() {
        return cert;
    }

    public void setCert(X509Certificate cert) {
        this.cert = cert;
    }
}
