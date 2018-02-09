package programiranje.baza_korisnika.cert.util;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Alati za rad sa stringovima.
 * @author Mikec
 */
public class StringTools implements Serializable{
	private SignatureManager manager; 
	
        /**
         * Konstruktor kojim se prosledjuje menadzer za potpisivanje
         * @param sm menadzer za potpisivanje
         */
	public StringTools(SignatureManager sm){
		manager = sm;
	}
	
        /**
         * Dobijanje menadzera za potpisivanje
         * @return menadzer za potpisivanje
         */
	public SignatureManager getSignatureManager(){
		return manager; 
	}
	
        /**
         * String u bajtove
         * @param s string 
         * @return bajtovi
         */
	public byte[] getBytes(String s){
		return s.getBytes();
	}
	
        /**
         * Slanje stringa preko menadzera potpisivanja
         * @param s string
         * @return info uspjeha
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws SignatureException 
         */
	public boolean sendDocument(String s) 
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, IOException, SignatureException, NoSuchPaddingException{
		return manager.sendDocument(s.getBytes()); 
	}
	
        /**
         * Prijem stringa preko menadzera potpisivanja
         * @return string koji je rezultat potpisivanja
         * @throws NumberFormatException
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws SignatureException 
         */
	public String receiveDocument() 
			throws NumberFormatException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, IOException, SignatureException, NoSuchPaddingException{
		return new String(manager.receiveDocument());
	}
}
