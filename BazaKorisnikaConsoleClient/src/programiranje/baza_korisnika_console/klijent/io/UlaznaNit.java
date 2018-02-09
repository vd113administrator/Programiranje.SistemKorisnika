/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_console.klijent.io;

import java.io.Console;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Nit kojom se u konzolu unose podaci sa standardnog ulaza <br>
 * Ulaz se moze i otkazati
 * @author Mikec
 */
public class UlaznaNit extends Thread{
    private AdapterServera adapter;

    private Scanner scanner;
    private Console console;
    
    private String line;
    private String password;
    private Integer integer;
    
    private Object cekanjeNaNitskeZadatke = new Object();
    private Object cekanjeNaAktivneZadatke = new Object();
    private Object zaustavljanjePetlje = new Object();
    
    private Runnable aktivnost; 
    
    private int brojacZadataka = 0; 
    private final Runnable unosLinije = ()->{
        scanner = new Scanner(System.in);
        console = System.console();
        
        if(console==null){
            line = scanner.nextLine();
        }else{
            line = console.readLine(); 
        }
    }; 
    private final Runnable unosSifre = ()->{
        scanner = new Scanner(System.in);
        console = System.console();
        
        if(console==null){
            password = scanner.nextLine(); 
        }else{
            password = new String(console.readPassword());
        }
    }; 
    private final Runnable unosIntegera = ()->{
        scanner = new Scanner(System.in);
        console = System.console();
        
        try{
            if(console==null){
               integer = scanner.nextInt(); 
            }else{
               integer = Integer.parseInt(console.readLine()); 
            }
        }catch(Exception ex){
            integer = -1; 
        }
    };
    
    private boolean zaustavljeno; 
    private boolean iskljuceno;
    private boolean uAktivnosti; 
    
    /**
     * Konstrukcija ulazne niti
     */
    public UlaznaNit(){
        scanner = new Scanner(System.in);
        console = System.console();
        start();
    }
    
    /**
     * Aktivnost ulazne niti
     */
    @Override
    public void run(){
        while(true){
            if(zaustavljeno) return; 
            if(brojacZadataka==0){
                synchronized(zaustavljanjePetlje){
                    try {
                        zaustavljanjePetlje.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } 
            if(zaustavljeno) return;
            uAktivnosti = true; 
            aktivnost.run();
            uAktivnosti = false;
            brojacZadataka--; 
            
            if(zaustavljeno) return;
            
            synchronized(cekanjeNaNitskeZadatke){
                cekanjeNaNitskeZadatke.notify();
            }
            
            synchronized(cekanjeNaAktivneZadatke){
                cekanjeNaAktivneZadatke.notify();
            }
            
            if(zaustavljeno) return;
        }
    }
    
    /**
     * Ocitavanje linije sa ulaza
     * @return ocitana linija 
     * @throws InterruptedException 
     */
    public synchronized String readLine() throws InterruptedException{
        if(zaustavljeno) return null;
        brojacZadataka++; 
        if(brojacZadataka>1)
            synchronized(cekanjeNaNitskeZadatke){
                try {
                    cekanjeNaNitskeZadatke.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); 
                }
            }
     
        aktivnost = unosLinije; 
        synchronized(zaustavljanjePetlje){
            zaustavljanjePetlje.notify(); 
        }
        synchronized(cekanjeNaAktivneZadatke){
            cekanjeNaAktivneZadatke.wait();
        }
        synchronized(cekanjeNaNitskeZadatke){
            cekanjeNaNitskeZadatke.notify();
        }
        return line;
    }
    
    /**
     * Ocitavanje sifre
     * @return sifra
     * @throws InterruptedException 
     */
    public synchronized String readPassword() throws InterruptedException{
        if(zaustavljeno) return null; 
        brojacZadataka++;
        
        if(brojacZadataka>1)
            synchronized(cekanjeNaNitskeZadatke){
                try {
                    cekanjeNaNitskeZadatke.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); 
                }
            }
        
        aktivnost = unosSifre; 
        
        synchronized(zaustavljanjePetlje){
            zaustavljanjePetlje.notify(); 
        }
        synchronized(cekanjeNaAktivneZadatke){
            cekanjeNaAktivneZadatke.wait();
        }
        synchronized(cekanjeNaNitskeZadatke){
            cekanjeNaNitskeZadatke.notify();
        }
        return password;
    }
    
    /**
     * Ocitavanje cijelog broja (radi npr. izbora kod menija)
     * @return ocitani broj (trosi cijelu liniju)
     * @throws InterruptedException 
     */
    public synchronized Integer readInteger() throws InterruptedException{
        if(zaustavljeno) return null;
        brojacZadataka++; 
        if(brojacZadataka>1)
            synchronized(cekanjeNaNitskeZadatke){
                try {
                    cekanjeNaNitskeZadatke.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); 
                }
            }
        
        aktivnost = unosIntegera; 
        
        synchronized(zaustavljanjePetlje){
            zaustavljanjePetlje.notify(); 
        }
        synchronized(cekanjeNaAktivneZadatke){
            cekanjeNaAktivneZadatke.wait();
        }
        synchronized(cekanjeNaNitskeZadatke){
            cekanjeNaNitskeZadatke.notify();
        }
        return integer; 
    }
    
    /**
     *  Funkcija zaustavljanja 
     */
    public synchronized void zaustavljanje(){
        zaustavljeno = true;
        brojacZadataka = 0; 
        synchronized(cekanjeNaAktivneZadatke){cekanjeNaAktivneZadatke.notifyAll();}
        synchronized(cekanjeNaNitskeZadatke){cekanjeNaNitskeZadatke.notifyAll();}
        synchronized(zaustavljanjePetlje){zaustavljanjePetlje.notifyAll();}
    }
    
    /**
     * Ranije vrseno testiranje niti 
     * @param args podaci
     */
    public static void glavna(String ... args){
        UlaznaNit it = new UlaznaNit(); 
        try { 
            System.out.println(it.readInteger());
            System.out.println(it.readLine());
            System.out.println(it.readPassword());
            it.zaustavljanje();
        } catch (InterruptedException ex) {
            Logger.getLogger(UlaznaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
