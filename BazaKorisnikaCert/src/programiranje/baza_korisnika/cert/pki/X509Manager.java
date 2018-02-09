/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.pki;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import javax.security.auth.x500.X500Principal;

/**
 * @author Mikec
 */
public class X509Manager implements Serializable{
    protected CertificateManager sertifikat;
    protected PrivateKeyManager privatniKljuc; 
    protected CertificateManager potpisnikovSertifikat;
            
    public X509Manager(){
    }

    public CertificateManager getSertifikat() {
        return sertifikat;
    }
    
    public CertificateManager getSertifikatPotpisnika() {
        return potpisnikovSertifikat;
    }

    public X509Manager setSertifikat(CertificateManager sertifikat) {
        this.sertifikat = sertifikat;
        return this; 
    }
    
    public X509Manager setSertifikatPotpisnika(CertificateManager sertifikat) {
        this.potpisnikovSertifikat = sertifikat;
        return this; 
    }
    
    public PrivateKeyManager getPrivatniKljuc() {
        return privatniKljuc;
    }

    public X509Manager setPrivatniKljuc(PrivateKeyManager privatniKljuc) {
        this.privatniKljuc = privatniKljuc;
        return this; 
    }
    
    public X509Manager setSertifikat(X509Certificate cert) {
        this.sertifikat = new CertificateManager(cert);
        return this; 
    }
    
    public X509Manager setSertifikatPotpisnika(X509Certificate cert) {
        this.potpisnikovSertifikat = new CertificateManager(cert);
        return this; 
    }

    public X509Manager setSertifikat(File cert) throws CertificateException, IOException {
        this.sertifikat = new CertificateManager(cert);
        return this; 
    }
    
    public X509Manager setSertifikatPotpisnika(File cert) throws CertificateException, IOException {
        this.potpisnikovSertifikat = new CertificateManager(cert);
        return this; 
    }
    
    public X509Manager setPrivatniKljuc(PrivateKey key) {
        this.privatniKljuc = new PrivateKeyManager(key);
        return this; 
    }
    
    public X509Manager setPrivatniKljuc(File key, String alias, String password) 
            throws IOException, UnrecoverableKeyException, CertificateException, 
            KeyStoreException, NoSuchAlgorithmException {
        this.privatniKljuc = new PrivateKeyManager(key,alias,password);
        return this; 
    }
    
    public PrivateKey getPrivateKey(){
        if(privatniKljuc==null) return null; 
        return privatniKljuc.getPrivatniKljuc();
    }
    
    public X509Certificate getX509Certificate(){
        if(sertifikat==null) return null; 
        return sertifikat.getCert();
    }
    
    public X509Certificate getX509CertificateOfSignature(){
        if(potpisnikovSertifikat==null) return null; 
        return potpisnikovSertifikat.getCert();
    }
    
    public PublicKey getPublicKey(){
        X509Certificate cert = getX509Certificate(); 
        if(cert == null) return null; 
        else return cert.getPublicKey(); 
    }
    
    public PublicKey getPublicKeyOfSignature(){
        X509Certificate cert = getX509CertificateOfSignature(); 
        if(cert == null) return null; 
        else return cert.getPublicKey(); 
    }
    
    public X500Principal getSubjectInfo(){
        X509Certificate cert = getX509Certificate(); 
        if(cert == null) return null; 
        else return cert.getSubjectX500Principal(); 
    }
    
    public X500Principal getIssuerInfo(){
        X509Certificate cert = getX509Certificate(); 
        if(cert == null) return null; 
        else return cert.getIssuerX500Principal(); 
    }
}
