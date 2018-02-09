package programiranje.baza_korisnika.cert.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import programiranje.baza_korisnika.cert.alg.UpravljanjeDokumentima;
import programiranje.baza_korisnika.cert.tech.DigitalnaEnvelopa;

/**
 * Menadzer koji sluzi za potpisivanje<br>
 * Polja : digitalna envelopa, upravljanje dokumentima, alatke datoteka i stringova, <br>
 *         java potpisivajuci mehanizam i naziv kombinacija algoritma za potpisivanje 
 * @author Mikec
 */
public class SignatureManager implements Serializable{
	private DigitalnaEnvelopa de;
	private UpravljanjeDokumentima ud;
	private FileTools ft; 
	private StringTools st; 
        private transient Signature potpisivanje;
        private String algorithm = "SHA384withRSA"; 
        
        /**
         * Minimalan konstruktor
         * @param de digitalna envelopa 
         * @param ud upravljenje dokumentima
         */
	public SignatureManager(DigitalnaEnvelopa de, UpravljanjeDokumentima ud){
		this.de = de; 
		this.ud = ud;
		this.ft = new FileTools(this); 
		this.st = new StringTools(this);
	}
        
        /**
         * Konstruktor postavljanja kombinacije algoritama
         * @param de digitalna envelopa
         * @param ud upravljanje dokumentima
         * @param alg algoritam
         */
        public SignatureManager(DigitalnaEnvelopa de, UpravljanjeDokumentima ud, String alg){
		this.de = de; 
		this.ud = ud;
		this.ft = new FileTools(this); 
		this.st = new StringTools(this);
                this.algorithm = alg; 
	}
	
        /**
         * Dobijanje alata za datoteke 
         * @return alati datoteka
         */
	public FileTools getFileTools(){
		return ft; 
	}
	
        /**
         * Dobijanje alata za stringove 
         * @return 
         */
	public StringTools getStringTools(){
		return st;
	}
	
        /**
         * Posalji javni kljuc
         * @throws IOException 
         */
	public void sendPublicKey() throws IOException{
		de.slanjeJavnogKljuca();
	}
	
        /**
         * Prijem javnog kljuca
         * @throws IOException
         * @throws ClassNotFoundException 
         */
	public void receivePublicKey() throws IOException, ClassNotFoundException{
		de.prijemJavnogKljuca();
	}
	
        /**
         * Inicijalizacija asimetricnog algoritma
         * @throws NoSuchAlgorithmException 
         */
	public void initAsymetric() throws NoSuchAlgorithmException{
		de.getTackaDE().inicijalizacijaAsimetricnogAlgoritma();
	}
	
        /**
         * Inicijalizacija simetricnog algoritma
         * @throws NoSuchAlgorithmException 
         */
	public void initSymetric() throws NoSuchAlgorithmException{
		de.getTackaDE().inicijalizacijaSimetricnogAlgoritma();
	}
	
        /**
         * Inicijalizacija mehanizma potpisivanja 
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException 
         */
        public void initSignature() throws NoSuchAlgorithmException, 
                InvalidKeyException{
                potpisivanje = Signature.getInstance(algorithm);
        }
        
        /**
         * Dobijanje digitalne envelope
         * @return digitalna envelopa 
         */
	public DigitalnaEnvelopa getDigitalnaEnvelopa(){
		return de; 
	}
	
        /**
         * Dobijanje mehanizma za upravljanje dokumentima
         * @return upravljanje dokumentima
         */
	public UpravljanjeDokumentima getUpravljanjeDokumentima(){
		return ud;
	}
	
        /**
         * Dobijanje hesa dokumenta 
         * @param by bajtovi podatka
         * @return hes dokumenta
         * @throws NoSuchAlgorithmException
         * @throws UnsupportedEncodingException 
         */
	public byte[] getDocumentHash(byte[] by) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException{
		return ud.getHash(by);
	}
	
	
        /**
         * Dobijanje kriptovanog hesa dokumenata
         * @param by podaci u bajtovima
         * @return kriptovani hes dokumenta
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         * @throws NoSuchPaddingException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws UnsupportedEncodingException 
         */
	public byte[] cryptedDocumentHash(byte[] by) throws NoSuchAlgorithmException, 
                InvalidKeyException,
                NoSuchPaddingException,
                IllegalBlockSizeException,
                BadPaddingException,
                UnsupportedEncodingException{
            return de.getTackaDE().getSimetricniAlgoritam().symmetricEncrypt(getDocumentHash(by));
	}
	
        /**
         * Dekriptovanje kriptovanog hesa dokumenta
         * @param by kriptovani hes dokumenta
         * @return otvoreni hes dokumenta
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws UnsupportedEncodingException
         * @throws NoSuchPaddingException 
         */
	public byte[] decryptedDocumentHash(byte[] by) 
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, 
			       NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException{
		return de.getTackaDE().getSimetricniAlgoritam().symmetricDecrypt(by);
	}
	
        /**
         * Provjera hesa dokumenata
         * @param doc dokumenat 
         * @param cryptedhash kriptovani hes
         * @return rezultat provjere
         * @throws SignatureException
         * @throws InvalidKeyException 
         */
	public boolean checkDocument(byte [] doc, byte [] cryptedhash) 
                throws SignatureException,
                InvalidKeyException {
            potpisivanje.initVerify(de.getTackaDE().getKomunikacijskiKljuc());
            potpisivanje.update(doc);
            return potpisivanje.verify(cryptedhash);
	}
	
        /**
         * Dobijanje potpisanog hesa 
         * @param doc dokument
         * @return potpisani hes u bajtovima
         * @throws SignatureException
         * @throws InvalidKeyException 
         */
        public byte[] getSignatureHash(byte[] doc) throws SignatureException,
                InvalidKeyException{
            potpisivanje.initSign(de.getTackaDE().getTajniKljuc());
            potpisivanje.update(doc);
            byte [] res = potpisivanje.sign();
            return res;
        }
	
        /**
         * Prijem i validacija, te enkripcija potpisanog dokumenta
         * @return dokument u bajtovima
         * @throws NumberFormatException
         * @throws IOException
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws SignatureException 
         */
	public byte[] receiveDocument() 
			throws NumberFormatException, IOException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
                        SignatureException, NoSuchPaddingException{
		byte[] bytesOfDoc = de.prijemPodataka(); 
                int nHashChars = Integer.parseInt(de.getReader().readLine());
		byte[] bytesOfHash = new byte[nHashChars]; 
                for(int i=0; i<nHashChars; i++)
                    bytesOfHash[i] = Byte.parseByte(de.getReader().readLine());
                boolean valid = checkDocument(bytesOfDoc,bytesOfHash); 
                de.getWriter().println(valid);
		return valid?bytesOfDoc:"".getBytes();
	}
	
        private boolean rezultat = false;
        private boolean visited = false;
        private Object visitSynchron = new Object(); 
        
        /**
         * Slanje i potpisivanje podataka
         * @param doc dokument
         * @return rezultat slanja
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws SignatureException 
         */
	public boolean sendDocument(byte[] doc) 
			throws InvalidKeyException, IllegalBlockSizeException, 
			BadPaddingException, NoSuchAlgorithmException, IOException, 
                        SignatureException, NoSuchPaddingException{
		visited = false; 
                
                de.slanjePodataka(doc);
                doc = getSignatureHash(doc);
                
		de.getWriter().println(doc.length);
		for(byte b: doc)
                    de.getWriter().println(b);
                
                Timer timer = new Timer();
                Thread thread = new Thread(()->{
                    if(visited) return; 
                    try {
                        rezultat = Boolean.parseBoolean(de.getReader().readLine());
                    } catch (IOException ex) {
                    }
                    synchronized(visitSynchron){
                        visited = true;
                        timer.cancel();
                        timer.purge();
                        visitSynchron.notify();
                    }
                });
            
                
                timer.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        synchronized(visitSynchron){
                            visited = true; 
                            thread.interrupt();
                            visitSynchron.notify();
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 1000);
                
                thread.start(); 
                
                synchronized(visitSynchron){
                    try{
                        if(!visited) visitSynchron.wait();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                return rezultat;
	}
}
