/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.podaci_korisnika.server.baza_podataka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.podaci_korisnika.baza_podataka.KarakteristikeKorisnika;
import programiranje.podaci_korisnika.server.model.RezultatPodaciKorisnika;

/**
 * Osnovna komunikacija prema bazi <br>
 * Komunikacija koja je javna za cijeli sistem korisnika 
 * @author Mikec
 */

public class PodaciKorisnikaAdapterBaze{
    private UserDatabaseAdapter adapter;
    private Connection konekcija;
    
    /**
     * Konstruktor koji dobija komunikaciju iz baze korisnika 
     * @param adapter komunikacija iz baze podataka 
     */
    public PodaciKorisnikaAdapterBaze(UserDatabaseAdapter adapter){
        this.adapter=adapter;
        this.konekcija = adapter.getKonekcija();
        initData();
    }
    
    /**
     * Inicijalizacija podataka 
     */
    private void initData(){
    }
    
    /**
     * @return komunikacija prema bazi podataka iz sistema baze korisnika
     */
    public UserDatabaseAdapter getAdapterBKShellServerBaze(){
        return adapter; 
    }
    
    /**
     * Dobijanje konekcija prema bazi podataka 
     * @return konekcija 
     */
    public Connection getKonekcija(){
        return konekcija;
    }
   
    /**
     * Lista korisnika
     * @param grupa ime grupe 
     * @param admin administrator te grupe
     * @return lista korisnicko po korisnickim imenima 
     * @throws SQLException 
     */
    public List<String> listaKorisnika() throws SQLException{
        ArrayList<String> korisnici = new ArrayList<>();
        PreparedStatement sta = konekcija.prepareStatement(
                "SELECT korisnicko_ime_korisnika FROM bk_programiranje_data");
        
        ResultSet rs = sta.executeQuery();
        while(rs.next()){
            korisnici.add(rs.getString(1));
        }
        rs.close();
        sta.close();
        return korisnici;
    }
    
    
    /**
     * Dobijanje podataka o korisniku u zavisnosti od clanstva u grupi 
     * @param admin administrator grupe 
     * @param grupa ime grupe 
     * @param korisnik ciljni korisnik 
     * @return korisnik 
     * @throws SQLException 
     */
    public RezultatPodaciKorisnika podaciOKorisniku(String admin) 
            throws SQLException{
        RezultatPodaciKorisnika kb = new RezultatPodaciKorisnika(); 
        kb.setUsername("");
        PreparedStatement sta = konekcija.prepareStatement(
                "SELECT * FROM bk_programiranje_data WHERE korisnicko_ime_korisnika=?");
        sta.setString(1, admin);
        ResultSet rs = sta.executeQuery();
        while(rs.next()){ 
           kb.setAddress(rs.getString(KarakteristikeKorisnika.adresa_korisnika.toString()));
           kb.setEmail(rs.getString(KarakteristikeKorisnika.elpostakorisnika.toString()));
           kb.setIdentificator(rs.getString(KarakteristikeKorisnika.identifikator_korisnika.toString()));
           kb.setName(rs.getString(KarakteristikeKorisnika.ime_korisnika.toString()));
           kb.setSurname(rs.getString(KarakteristikeKorisnika.prezime_korisnika.toString()));
           kb.setUsername(rs.getString(KarakteristikeKorisnika.korisnicko_ime_korisnika.toString()));
           kb.setWebs(rs.getString(KarakteristikeKorisnika.vebsajtovikorisnika.toString()));
           kb.setWorkplaces(rs.getString(KarakteristikeKorisnika.radno_mjesto_korisnika.toString()));
           kb.setTelephone(rs.getString(KarakteristikeKorisnika.telefon_korisnika.toString()));
           kb.setType(rs.getString(KarakteristikeKorisnika.tip_korisnika.toString()));
        }
        rs.close();
        sta.close();
        return kb;
    }
}
