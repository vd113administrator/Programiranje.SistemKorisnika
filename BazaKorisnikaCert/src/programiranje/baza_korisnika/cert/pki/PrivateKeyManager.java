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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *
 * @author Mikec
 */
public class PrivateKeyManager implements Serializable{
    private PrivateKey privatniKljuc;  
    
    public PrivateKeyManager(PrivateKey privatniKljuc){
        this.privatniKljuc = privatniKljuc; 
    }
    
    public PrivateKeyManager(File file, String alias, String password) throws IOException, 
            NoSuchAlgorithmException, KeyStoreException,
            CertificateException, UnrecoverableKeyException{
        FileInputStream is = new FileInputStream(file);
        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        store.load(is, password.toCharArray());
        PrivateKey pkey = (PrivateKey) store.getKey(alias, password.toCharArray());
        privatniKljuc = pkey; 
        is.close();
    }

    public PrivateKeyManager(byte[] file, String alias, String password) throws IOException, 
            NoSuchAlgorithmException, KeyStoreException,
            CertificateException, UnrecoverableKeyException{
        ByteArrayInputStream is = new ByteArrayInputStream(file);
        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        store.load(is, password.toCharArray());
        PrivateKey pkey = (PrivateKey) store.getKey(alias, password.toCharArray());
        is.close();
    }
    
    public PrivateKey getPrivatniKljuc() {
        return privatniKljuc;
    }

    public void setPrivatniKljuc(PrivateKey privatniKljuc) {
        this.privatniKljuc = privatniKljuc;
    }
}
