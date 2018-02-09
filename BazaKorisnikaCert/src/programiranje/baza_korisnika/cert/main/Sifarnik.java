/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika.cert.main;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import programiranje.baza_korisnika.cert.alg.UpravljanjeSiframa;
import programiranje.baza_korisnika.cert.util.SaltManager;

/**
 *
 * @author Mikec
 */
public class Sifarnik {
    private static UpravljanjeSiframa passmgr = new UpravljanjeSiframa(); 
    private static SaltManager saltmgr = new SaltManager();
    private static HashMap<String,String> sifre = new HashMap<String,String>(); 
    private static HashMap<String,String> hsifre = new HashMap<String,String>();
    
    public static void main(String ... args) throws NoSuchAlgorithmException, 
            UnsupportedEncodingException{
        while(menu());
    }
    
    public static boolean menu() throws NoSuchAlgorithmException, 
            UnsupportedEncodingException{
        System.out.println("=============================");
        System.out.println("0. Izlaz");
        System.out.println("1. Unos novog korisnika");
        System.out.println("2. Brisanje postojeceg korisnika");
        System.out.println("3. Brisanje postojeceg salta");
        System.out.println("4. Lista korisnika sifara, saltova i heseva");
        System.out.println("5. Prvjera sifre za korisnika");
        System.out.println("=============================");
        System.out.print("Izbor : ");
        Scanner scan = new Scanner(System.in);
        int izbor = -1; 
        try{izbor = scan.nextInt();} catch(Exception ex){ pogresanIzbor(); return true; }
        switch(izbor){
            case 0: 
                return false;
            case 1: 
                unosKorisnika();
                return true; 
            case 2: 
                brisanjeKorisnika();
                return true; 
            case 3: 
                brisanjeSalta();
                return true; 
            case 4:
                listaKorisnika();
                return true; 
            case 5: 
                provjeraSifre(); 
                return true; 
            default: 
                pogresanIzbor();
                return true; 
        } 
    }
    
    public static void pogresanIzbor(){
        System.out.println("Pogresan izbor");
    }
   
    public static void unosKorisnika() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Scanner scan = new Scanner(System.in);
        System.out.print("Korisnik : ");
        String user = scan.nextLine(); 
        System.out.print("Sifra : ");
        String pass = scan.nextLine(); 
        System.out.print("Salt : ");
        String salt = saltmgr.generateSalt();
        System.out.println(salt); 
        boolean x = saltmgr.add(user, salt);
        if(!x) return;
        sifre.put(user,pass);
        String hpass = passmgr.toHashString(pass, salt);
        hsifre.put(user,hpass);
    }
    
    public static void brisanjeKorisnika(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Korisnik : ");
        String user = scan.nextLine(); 
        saltmgr.removeKey(user);
    }
    
    public static void brisanjeSalta(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Salt : ");
        String salt = scan.nextLine(); 
        saltmgr.removeSalt(salt);
    }
    
    public static void listaKorisnika(){
        System.out.println("-------------------------------");
        for(String korisnik: saltmgr.getCopyMap().keySet()){
            System.out.println("KORISNIK   : "+korisnik);
            System.out.println("SIFRA      : "+sifre.get(korisnik));
            System.out.println("SALT       : "+saltmgr.getSalt(korisnik));
            System.out.println("HASH SIFRE : "+hsifre.get(korisnik));
            System.out.println("-------------------------------");
        }
    }
    
    public static void provjeraSifre() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        Scanner scan = new Scanner(System.in);
        System.out.print("Korisnik : ");
        String user = scan.nextLine(); 
        System.out.print("Sifra : ");
        String pass = scan.nextLine(); 
        System.out.print("Salt : ");
        String salt = saltmgr.getSalt(user);
        if(salt==null) return; 
        System.out.println(salt); 
        System.out.println("Provjera : "+passmgr.checkHash(pass, salt, hsifre.get(user)));
    }
}
