package programiranje.baza_korisnika.cert.alg;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <b>Instancam ove klase se prave hash za dokumente ili podatke u bajtovima</b><br><br>
 * Polja: <br>
 * algorithm - naziv algoritma za hesiranje (def. SHA-384)<br>
 * encoding - naziv sistema kodovanja sifre (def. UTF-8)<br>
 * hashRepeat - broj ponavljanja hash-a (def. 1000)<br>
 * @author Mikec
 */
public class UpravljanjeDokumentima implements Serializable{
    private String algorithm = "SHA-384";
    private String encoding = "UTF-8";
    private int hashRepeat = 100; 
    
    /**
     * Podrazumjevani konstruktor
     */
    public UpravljanjeDokumentima(){
    }
    
    /**
     * Konstruktor kojim se zadaje algoritam
     * @param algorithm  naziv algoritma
     */
    public UpravljanjeDokumentima(String algorithm){
        this.algorithm = algorithm; 
    }
    
    /**
     * Konstruktor kojim se zadaje algoritam i kodovanje algoritma
     * @param algorithm naziv algoritma 
     * @param encoding hash algoritma
     */
    public UpravljanjeDokumentima(String algorithm,String encoding){
        this.algorithm = algorithm; 
        this.encoding = encoding; 
    }
    
    /**
     * Konstruktor kojim se zadaje algoritam, kodovanje algoritma i broj ponavljanja za hes
     * @param algorithm naziv algoritma
     * @param encoding kodovanje 
     * @param hashRepeat broj ponavljanja hesa 
     */
    public UpravljanjeDokumentima(String algorithm, String encoding, int hashRepeat){
        this.algorithm = algorithm; 
        this.encoding = encoding; 
        this.hashRepeat = hashRepeat; 
    }
    
    /**
     * Dobijanje jednostrukog hash za sadrzah datoteke
     * @param file datoteka 
     * @return hash sadrzaja datoteke u bajtovima 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public byte[] getOneHash(File file)throws 
    		NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();  
        return digest.digest(Files.readAllBytes(file.toPath()));
    }
    
    /**
     * Dobijanje jednostrukog hash iz stringa
     * @param string string
     * @return hash datog stringa u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getOneHash(String string) throws 
            NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();  
        return digest.digest(string.getBytes(encoding));
    }
    
    /**
     * Dobijanje jednostrukog hash iz podataka
     * @param by podaci u bajtovima
     * @return hash datih podataka u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getOneHash(byte [] by) throws 
            NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();  
        return digest.digest(by);
    }
    
    /**
     * Dobijanje visestrukog hash-a u bajtovima
     * @param by podaci u bajtovima
     * @return hash podataka u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getHash(byte [] by) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        byte[] b = getOneHash(by);
        for(int i=0; i<hashRepeat; i++) b = getOneHash(b);
        return b; 
    }
    
    /**
     * Visestruki hes stringa
     * @param s podaci u stringovima 
     * @return hash podataka u bajtovima
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public byte[] getHash(String s) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        byte[] b = getOneHash(s);
        for(int i=0; i<hashRepeat; i++) b = getOneHash(b);
        return b; 
    }
    
    /**
     * Visestruki hash sadrzaja datoteke
     * @param file datoteka
     * @return hash sadrzaja podataka u datoteci
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public byte[] getHash(File file) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        byte[] b = getOneHash(file);
        for(int i=0; i<hashRepeat; i++) b = getOneHash(b);
        return b; 
    }
    
    /**
     * String hasha podataka dobijenih iz niza bajtova
     * @param by podaci
     * @return string datog hash-a
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String toHashString(byte[] by) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return new String(getHash(by));
    }
    
    /**
     * String hasha podataka dobijenog iz hasha
     * @param str string za hesiranje
     * @return string hasha
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String toHashString(String str) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return new String(getHash(str));
    }
    
    /**
     * String hash-a za sadrzaj datoteke
     * @param file datoteka
     * @return string hash stringa iz datoteke
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    public String toHashString(File file) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        return new String(getHash(file));
    }
    
    /**
     * Provjera podataka na hash
     * @param by podaci
     * @param hash hes
     * @return rezultat provjere
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public boolean checkHash(byte[] by, String hash) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return toHashString(by).equals(hash);
    }
    
    /**
     * Provjera hasha za string
     * @param str podaci
     * @param hash hes
     * @return rezultat provjere
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public boolean checkHash(String str, String hash) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return toHashString(str).equals(hash);
    }
    
    /**
     * Provjera hash-a za sadrzaj datoteke
     * @param file podaci
     * @param hash hes
     * @return rezultat provjere
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    public boolean checkHash(File file, String hash) 
            throws NoSuchAlgorithmException, IOException{
        return toHashString(file).equals(hash);
    }
}
