package programiranje.baza_korisnika.cert.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import java.io.ObjectOutputStream; 
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream; 
import java.security.SignatureException;
import javax.crypto.NoSuchPaddingException;

/**
 * Klasa instance alata za rad sa datotekama<br>
 * Ima polje menadera potpisa 
 * @author Mikec
 */
public class FileTools implements Serializable{
	private SignatureManager manager; 
	
        /**
         * Menadzer potpisa <br>
         * @param sm 
         */
	public FileTools(SignatureManager sm){
		manager = sm;
	}
	
        /**
         * Dobijanje menadzera potpisivanja <br>
         * @return menadzer
         */
	public SignatureManager getSignatureManager(){
		return manager;
	}
	
        /**
         * Ocitavanje sadrzaja datoteke kao niza bajtova 
         * @param file datoteka
         * @return niz bajtova koji je sadrzaj 
         * @throws IOException 
         */
	public byte[] getContentBytes(File file) throws IOException{
		return Files.readAllBytes(file.toPath());
	}
	
        /**
         * Pakovanje sadrzaja i niza bajtova datoteku
         * @param file ime fajla
         * @return niz bajtova koji je sadrzaj
         * @throws IOException 
         */
	public byte[] fromDescContentBytes(File file) throws IOException{
		FileContent fc = new FileContent(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(fc);
		oos.close();
		return baos.toByteArray();
	}
	
        /**
         * Pakovanje sadrzaja i niza bajtova datoteku
         * @param file podaci fajla sa sadrzajem u jednom objektu
         * @return  niz bajtova kojima je serijalizovan parametarski objekat
         * @throws IOException 
         */
	public byte[] fromDescContentBytes(FileContent file) throws IOException{
		FileContent fc = file;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(fc);
		oos.close();
		return baos.toByteArray();
	}
	
        /**
         * Ocitavanje sadrzaja i informacija datoteke iz serijalizovanih bajtova 
         * @param by bajtovi
         * @return objekat sadrzaja i informacija datoteke
         * @throws IOException
         * @throws ClassNotFoundException 
         */
	public FileContent toDescContentBytes(byte[] by) throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(by));
		return (FileContent) ois.readObject(); 
	}
	
        /**
         * Klasa ciji su atributi informacije o datoteci (File) <br>
         * i sadrzaj datoteke u bajtovima
         */
	public class FileContent  implements Serializable{
		private static final long serialVersionUID = 1L;
		private File file;
		private byte[] content; 
                
                /**
                 * Konstruktor koji za parametar ima informacije o datoteki 
                 * @param datoteka
                 * @throws IOException 
                 */
		private FileContent(File datoteka) throws IOException{
			file = datoteka; 
			content = Files.readAllBytes(file.toPath());
		}
                /**
                 * Dobijanje informacija o datoteki
                 * @return informacije o datoteki
                 */
		public File getFile(){
			return file; 
		}
		
                /**
                 * Dobijanje sadrzaja datoteke 
                 * @return sadrzaj u bajtovima 
                 */
		public byte[] getContent(){
			return content; 
		}
		
                /**
                 * Postavljanje drugih informacija o datoteki 
                 * @param file 
                 */
		public void setFile(File file){
			this.file = file; 
		}
		
                /**
                 * Postavljanje drugog sadrzaja
                 * @param b 
                 */
		public void setContent(byte [] b){
			this.content= b; 			
		}
		
                /**
                 * Upis datih podataka u datu datoteku
                 * @throws IOException 
                 */
		public void write() throws IOException{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content);
			fos.close();
		}
	
                /**
                 * Ocitavanje datoteke
                 * @throws IOException 
                 */
		public void read() throws IOException{
			content = Files.readAllBytes(file.toPath());
		}
	}
	
        /**
         * Slanje datoteke preko menadzera potpisivanja (enkripcija i potpis)
         * @param f sadrzaj i informacije datoteke 
         * @return poruka o uspjehu 
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws SignatureException 
         */
	public boolean sendDocument(FileContent f) 
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, IOException, SignatureException, NoSuchPaddingException{
		return manager.sendDocument(fromDescContentBytes(f)); 
	}
	
        /**
         * Slanje datoteke preko menadzera potpisivanja (enkripcija i potpis)
         * @param f informacije datoteke
         * @return poruka o uspjehu 
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws SignatureException 
         */
	public boolean sendDocument(File f) 
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, IOException, SignatureException, NoSuchPaddingException{
		return manager.sendDocument(fromDescContentBytes(f)); 
	}
	
        /**
         * Prijem datoteke 
         * @return informacije o datoteki i sadrzaj 
         * @throws NumberFormatException
         * @throws InvalidKeyException
         * @throws IllegalBlockSizeException
         * @throws BadPaddingException
         * @throws NoSuchAlgorithmException
         * @throws IOException
         * @throws ClassNotFoundException
         * @throws SignatureException 
         */
	public FileContent receiveDocument() 
			throws NumberFormatException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, IOException, ClassNotFoundException, SignatureException, NoSuchPaddingException{
		return toDescContentBytes(manager.receiveDocument());
	}
}
