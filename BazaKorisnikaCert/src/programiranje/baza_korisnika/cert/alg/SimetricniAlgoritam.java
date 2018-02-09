/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.alg;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * <b>Klasa cijim instancama se barata sa simetricnim algoritmima.</b><br><br>
 * Polja su: <br>
 *  - naziv za simetricni algoritam (podrazumjevano DES) : symetricAlgorithm <br>
 *  - kljuc simetricnog algoritma : symetricKey <br>
 * @author Mikec
 */
public class SimetricniAlgoritam implements Serializable{
	private String symmetricAlgorithm = "DES";
	private SecretKey symmetricKey;
        
        /**
         *  Podrazumjevani konstruktor i podrazumjevani simetricni algoritam 
         */
        public SimetricniAlgoritam() {
		super();
	}
        
        /**
         * Konstruktor sa zadatim simetricnim algoritmom
         * @param symmetricAlgorithm naziv simetricnog algoritma
         * @throws NoSuchAlgorithmException 
         */
	public SimetricniAlgoritam(String symmetricAlgorithm) throws NoSuchAlgorithmException {
		boolean ok = true; 
                if(!ok)
                    throw new NoSuchAlgorithmException("Specified symmetric algorithm " + symmetricAlgorithm + " not supported.");
		if(ListeAlgoritama.getAlgorithm(symmetricAlgorithm)==null)
                    throw new NoSuchAlgorithmException("Specified symmetric algorithm " + symmetricAlgorithm + " not supported.");
                this.symmetricAlgorithm = symmetricAlgorithm;
	}
        
        /**
         * Dobijanje naziva algoritma
         * @return naziv algoritma
         */
        public String getAlgorithm(){
            return symmetricAlgorithm;
        }
        
        /**
         * Dobijanje kljuca
         * @return kljuc simetricnog algoritma
         */
        public SecretKey getSecretKey(){
            return symmetricKey;
        }
        
        /**
         * Generisanje kljuca algoritma
         * @throws NoSuchAlgorithmException 
         */
	public void generateSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(symmetricAlgorithm);
		symmetricKey = keygen.generateKey();
	}

        /**
         * Kriptovanje kljucem po simetricnom algoritmu
         * @param input otvorena poruka u bajtovima 
         * @return kriptovana poruka u bajtovima
         * @throws NoSuchAlgorithmException
         * @throws NoSuchPaddingException
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException 
         */
	public byte[] symmetricEncrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte output[] = null;
		Cipher cipher = Cipher.getInstance(symmetricAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
		output = cipher.doFinal(input);
		return output;
	}

        /**
         * Dekriptovanje simetricnim algoritmom
         * @param input kriptovana poruka u bajtovima
         * @return otvorena poruka
         * @throws NoSuchAlgorithmException
         * @throws NoSuchPaddingException
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException 
         */
	public byte[] symmetricDecrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte output[] = null;
		Cipher cipher = Cipher.getInstance(symmetricAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, symmetricKey);
		output = cipher.doFinal(input);
		return output;
	}
        /**
         * Postavljenje tajnog kljuca. U nekim slucajevima kad npr. treba kopirati instancu ove klase.
         * @param key tajni kljuc
         */
        public void setSecretKey(SecretKey key){
            symmetricKey = key; 
        }
}