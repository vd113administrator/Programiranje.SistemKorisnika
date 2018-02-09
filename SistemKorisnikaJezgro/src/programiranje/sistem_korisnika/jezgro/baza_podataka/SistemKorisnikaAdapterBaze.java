/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.jezgro.baza_podataka;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import programiranje.baza_korisnika_shell.komunikacije.server.UserDatabaseAdapter;
import programiranje.sistem_korisnika.jezgro.model_parametara.RezultatPreimenovanjaGrupaBean;
import programiranje.sistem_korisnika.jezgro.model_podataka.GrupaBean;
import programiranje.sistem_korisnika.jezgro.model_podataka.KorisnikBean;
import programiranje.sistem_korisnika.jezgro.standard.ProtokolSKGPRezultati;

/**
 * Osnovna komunikacija prema bazi <br>
 * Komunikacija koja je javna za cijeli sistem korisnika 
 * @author Mikec
 */
public class SistemKorisnikaAdapterBaze{
    private UserDatabaseAdapter adapter;
    private Connection konekcija;
    
    /**
     * Konstruktor koji dobija komunikaciju iz baze korisnika 
     * @param adapter komunikacija iz baze podataka 
     */
    public SistemKorisnikaAdapterBaze(UserDatabaseAdapter adapter){
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
     * Aktivnost pravljenja grupe 
     * @param noviAdmin ime administratora 
     * @param ime_grupe ime nove grupe
     * @return rezultat dodavanja
     * @throws SQLException 
     */
    public boolean pravljenjeGrupe(String noviAdmin, String ime_grupe) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(noviAdmin, ime_grupe)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_kreiranje_grupe+"(?,?) }");
        sta.setString(1, noviAdmin);
        sta.setString(2, ime_grupe);
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b;
    }
    
    /**
     * Aktivnost brisanja grupe
     * @param stariAdmin ime starog administratora
     * @param ime_grupe ime grupe za brisanje 
     * @return rezultat brisanja 
     * @throws SQLException 
     */
    public boolean brisnanjeGrupe(String stariAdmin, String ime_grupe) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(stariAdmin, ime_grupe)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_brisanje_grupe+"(?,?) }");
        sta.setString(1, stariAdmin);
        sta.setString(2, ime_grupe);
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b;
    }
    
    /**
     * Aktivnost uclanjenja u grupu
     * @param korisnik ime korisnika kandidata 
     * @param grupa ime grupe u koju se ucijenjuje 
     * @return uspjesnost
     * @throws SQLException 
     */
    public boolean uclanjenje(String korisnik, String grupa) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(korisnik, grupa)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_uclanjenje+"(?,?) }");
        sta.setString(1, grupa);
        sta.setString(2, korisnik);
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b;
    }
    
    /**
     * Isclanjenje clana iz grupe 
     * @param korisnik moguci clan 
     * @param grupa ime grupe
     * @return uspjesnost 
     * @throws SQLException 
     */
    public boolean isclanjenje(String korisnik, String grupa) throws SQLException{
         if(!ProvjeraImena.provjeraVSMSBDCM(korisnik, grupa)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_isclanjenje+"(?,?) }");
        sta.setString(1, grupa);
        sta.setString(2, korisnik);
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b;
    }
    
    /**
     * Provjera clanstva korisnika u grupi 
     * @param korisnik korisnik 
     * @param grupa ime grupe 
     * @return uspjesnost
     * @throws SQLException 
     */
    public boolean provjeraClanstva(String korisnik, String grupa) throws SQLException{
        List<String> grupe = this.listaClanstva(korisnik);
        return grupe.contains(grupa);
    }
    
    /**
     * Lista korisnika
     * @param grupa ime grupe 
     * @param admin administrator te grupe
     * @return lista korisnicko po korisnickim imenima 
     * @throws SQLException 
     */
    public List<String> listaKorisnika(String grupa, String admin) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(grupa,admin)) return new ArrayList<>();
        ArrayList<String> korisnici = new ArrayList<>();
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_korisnici_grupe+"(?,?) }");
        
        sta.setString(1,admin);
        sta.setString(2,grupa);
        
        ResultSet rs = sta.executeQuery();
        while(rs.next()){
            korisnici.add(rs.getString(1));
        }
        rs.close();
        sta.close();
        return korisnici;
    }
    
    /**
     * Lista grupa ciji je vlasnik zadati korisnik
     * @param admin_za_grupe korisnik
     * @return lista imena grupa ciji je vlasnik dati korisnik 
     * @throws SQLException 
     */
    public List<String> listaVlasnistva(String admin_za_grupe) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(admin_za_grupe)) return new ArrayList<>();
        ArrayList<String> lista = new ArrayList<>();
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_vlasnistvo+"(?) }");
        sta.setString(1, admin_za_grupe);
        ResultSet rs = sta.executeQuery(); 
        while(rs.next()){
            lista.add(rs.getString(1));
        }
        rs.close();
        sta.close();
        return lista; 
    }
    
    /**
     * Lista grupa ciji je clan dati korisnik 
     * @param clan_za_grupe korisnik 
     * @return lista imena grupa ciji je clan dati korisnik 
     * @throws SQLException 
     */
    public List<String> listaClanstva(String clan_za_grupe) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(clan_za_grupe)) return new ArrayList<>();
        ArrayList<String> lista = new ArrayList<>();
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_clanstvo+"(?) }");
        sta.setString(1, clan_za_grupe);
        ResultSet rs = sta.executeQuery();
        while(rs.next()){
            lista.add(rs.getString(1));
        }
        rs.close();
        sta.close();
        return lista;
    }
    
    /**
     * Dobijanje podataka o grupi 
     * @param admin administrator 
     * @param grupa ime grupe 
     * @return podaci 
     * @throws SQLException 
     */
    public GrupaBean podaciOGrupi(String admin, String grupa) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(grupa,admin)) return null;
        KorisnikBean kb = new KorisnikBean(); kb.setUsername("");
        GrupaBean gb = new GrupaBean(kb,"","");
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_podaci_grupe+"(?,?) }");
        sta.setString(1, admin);
        sta.setString(2, grupa);
        ResultSet rs = sta.executeQuery();
        while(rs.next()){ 
           kb = new KorisnikBean();
           kb.setUsername(rs.getString(1));
           gb = new GrupaBean(kb, rs.getString(2), rs.getString(3));
        }
        rs.close();
        sta.close();
        return gb;
    }
    
    /**
     * Dobijanje podataka o korisniku u zavisnosti od clanstva u grupi 
     * @param admin administrator grupe 
     * @param grupa ime grupe 
     * @param korisnik ciljni korisnik 
     * @return korisnik 
     * @throws SQLException 
     */
    public KorisnikBean podaciOKorisniku(String admin, String grupa, String korisnik) 
            throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(grupa,admin,korisnik)) return null;
        KorisnikBean kb = new KorisnikBean(); 
        kb.setUsername("");
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_podaci_korisnika+"(?,?,?) }");
        sta.setString(1, admin);
        sta.setString(2, grupa);
        sta.setString(3, korisnik);
        ResultSet rs = sta.executeQuery();
        while(rs.next()){ 
           kb.setAddress(rs.getString(KorisniciColumns.adresa_korisnika.toString()));
           kb.setEmail(rs.getString(KorisniciColumns.elpostakorisnika.toString()));
           kb.setIdentificator(rs.getString(KorisniciColumns.identifikator_korisnika.toString()));
           kb.setName(rs.getString(KorisniciColumns.ime_korisnika.toString()));
           kb.setSurname(rs.getString(KorisniciColumns.prezime_korisnika.toString()));
           kb.setUsername(rs.getString(KorisniciColumns.korisnicko_ime_korisnika.toString()));
           kb.setWebs(rs.getString(KorisniciColumns.vebsajtovikorisnika.toString()));
           kb.setWorkplaces(rs.getString(KorisniciColumns.radno_mjesto_korisnika.toString()));
           kb.setTelephone(rs.getString(KorisniciColumns.telefon_korisnika.toString()));
           kb.setType(rs.getString(KorisniciColumns.tip_korisnika.toString()));
        }
        rs.close();
        sta.close();
        return kb;
    }
    
    /**
     * Podaci o grupi uz podatke o korisnicima
     * @param admin administrator grupe  
     * @param grupa ime grupe 
     * @return podaci
     * @throws SQLException 
     */
    public GrupaBean detaljniPodaciOGrupiIKorisnicima(String admin, String grupa) throws SQLException{
        return podaciOGrupi(admin,grupa);
    }
    
    /**
     * Dobijanje naziva grupa
     * @return lista svih grupa 
     * @throws SQLException 
     */
    public List<String> naziviGrupa() throws SQLException{
        ArrayList<String> grupe = new ArrayList<>();
        CallableStatement sta = konekcija.prepareCall("{ call "+
                ProcedureBP.sk_programiranje_grupe+"() }");
        ResultSet rs = sta.executeQuery();
        while(rs.next()){
            grupe.add(rs.getString(1));
        }
        rs.close();
        sta.close();
        return grupe;
    }
    
    /**
     * Preimenovanje grupa 
     * @param admin administrator 
     * @param oldname staro ime grupe 
     * @param newname novo ime grupe 
     * @return informacija o uspjesnosti 
     * @throws SQLException 
     */
    public RezultatPreimenovanjaGrupaBean preimenovanjaGrupa(String admin, String oldname, String newname) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(oldname,admin,newname)) return null;
        CallableStatement sta = konekcija.prepareCall("{ call "+
            ProcedureBP.sk_programiranje_preimenovanje_grupe+"(?,?,?) }");
        
        sta.setString(1, admin);
        sta.setString(2, oldname);
        sta.setString(3, newname);
        
        ResultSet rs = sta.executeQuery();
        
        rs.next();
        boolean b = rs.getBoolean(1);
        
        rs.close();
        sta.close();
        
        if(b) return new RezultatPreimenovanjaGrupaBean(ProtokolSKGPRezultati.SKGP_O1_POZITIVNO.toString(),"");
        else return new RezultatPreimenovanjaGrupaBean(ProtokolSKGPRezultati.SKGP_O1_NEGATIVNO.toString(),"Nije preimenovano");
    }
}
