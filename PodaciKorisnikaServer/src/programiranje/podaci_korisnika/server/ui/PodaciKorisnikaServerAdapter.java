/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import programiranje.baza_korisnika_shell.komunikacije.server.ServerDataAdapter;
import programiranje.podaci_korisnika.alatke.Base64Swapper;
import programiranje.podaci_korisnika.server.model.RezultatListanjaKorisnika;
import programiranje.podaci_korisnika.server.model.RezultatPodaciKorisnika;

/**
 * Prosirenje komunikacije, ka klijentu, baze korisnika na sistem korisnika
 * @author Mikec
 */
public class PodaciKorisnikaServerAdapter {
    private static ServerDataAdapter server;
    
    /**
     * Konstruktor 
     * @param adapter komunikacije klijenta - baza korisnika 
     */
    public PodaciKorisnikaServerAdapter(ServerDataAdapter adapter){
       server = adapter;
    }
    
    /**
     * Dobijanje komunikacije baze_korisnika prema kiljentima
     * @return komunikacija
     */
    public ServerDataAdapter getBKShellServerAdapter(){
        return server;
    }
    
    
    /**
     * Slanje rezultata listanja korisnika klijentu 
     * @param x parametrizovani rezultat
     */
    public void toClientListanjeKorisnika(RezultatListanjaKorisnika x){
        List<String> lista = x.getImenaClanova();
        server.writeLine(Integer.toString(lista.size()));
        for(String str: lista)
           server.writeLine(str);
    }
    
    
    /**
     * Slanje rezultata ocitavanja parametara korisnika ka klijentu 
     * @param r parametrizovani rezultati
     */
    public void toClientPodaciKorisnika(RezultatPodaciKorisnika r){
        server.writeLine(r.getName());
        server.writeLine(r.getSurname());
        server.writeLine(r.getUsername());
        server.writeLine(r.getAddress());
        server.writeLine(r.getIdentificator());
        server.writeLine(r.getWorkplaces());
        server.writeLine(r.getTelephone());
        server.writeLine(r.getEmail());
        server.writeLine(r.getWebs());
        server.writeLine(r.getType());
    }
    
    public void toClientPodaciKorisnika(boolean sertifikovan, Serializable sertifikat){
        try {
            server.writeLine(Boolean.toString(sertifikovan));
            if(sertifikovan) server.writeLine(Base64Swapper.encode(sertifikat));
        } catch (IOException ex) {
            server.writeLine("");
            Logger.getLogger(PodaciKorisnikaServerAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String fromClientPodaciKorisnika() throws IOException{
        String username = server.readLine();
        return username; 
    }
}
