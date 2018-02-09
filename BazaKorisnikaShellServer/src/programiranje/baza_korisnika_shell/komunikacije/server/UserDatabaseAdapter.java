/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.komunikacije.server;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import programiranje.baza_korisnika.cert.alg.UpravljanjeSiframa;
import programiranje.baza_korisnika.cert.util.SaltManager;
import programiranje.baza_korisnika_shell.alati.SQLUtil;
import programiranje.baza_korisnika_shell.baza_podataka.ProvjeraImena;
import programiranje.baza_korisnika_shell.baza_podataka.SQLNames;
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_shell.kontrola.server.RegistracijaControl;
import programiranje.baza_korisnika_shell.model.Korisnik;
import programiranje.baza_korisnika_shell.server.ServerGlobalCommunicator;
import programiranje.baza_korisnika_shell.server.ServerMainProgram;
import static programiranje.baza_korisnika_shell.server.ServerStatics.databaseName;
import static programiranje.baza_korisnika_shell.server.ServerStatics.databaseTable;

/**
 * Komunikacija prema bazi podataka 
 * @author Mikec
 */
public class UserDatabaseAdapter {
    private String host; 
    private String user; 
    private String password; 
    private String database; 
    private String datatable;
    private String url; 
    
    private Connection konekcija; 
    private ServerGlobalCommunicator komunikator;
    private SaltManager saltManager; 
    private UpravljanjeSiframa passManager; 
    
    /**
     * Konstruktor baze podataka
     * @param host host baze
     * @param user korisnik
     * @param password sifra 
     * @throws ClassNotFoundException 
     */
    public UserDatabaseAdapter(String host, String user, String password) throws ClassNotFoundException{
        komunikator = ServerMainProgram.getServerKomunikator();
        saltManager = komunikator.getSaltManager(); 
        passManager = komunikator.getUpravljanjeSiframa();
        Class.forName("com.mysql.jdbc.Driver");
        this.host = host; 
        this.password = password; 
        this.user = user; 
        database = databaseName;
        datatable = databaseTable;
        url  = "jdbc:mysql://"+host+"/"+database+"?useUnicode=true&characterEncoding=UTF-8";
    }
    
    /**
     * Konektovanje prema bazi 
     * @throws SQLException 
     */
    public synchronized void konektuj() throws SQLException{
        konekcija = DriverManager.getConnection(url, user, password);
    }
    
    /**
     * Raskonektovanje od baze
     * @throws SQLException 
     */
    public synchronized void raskonektuj() throws SQLException{
        konekcija.close();
        konekcija = null;
    }
    
    /**
     * Dobijanje konekcije 
     * @return konekcija prema bazi 
     */
    public synchronized Connection getKonekcija(){
        return konekcija;
    }
    
    /**
     * Dodavanje korisnika u bazu
     * @param k korisnik
     * @throws SQLException 
     */
    public synchronized void dodajKorisnika(Korisnik k) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(k)) return;
        k.getAutentifikacija().gotoHashMode(saltManager, passManager);
        Statement sta = konekcija.createStatement();
        String identifikator_korisnika = k.getDeskripcija().getIdentificator();
        String ime_korisnika = k.getDeskripcija().getFirstname();
        String prezime_korisnika = k.getDeskripcija().getLastname();
        String korisnicko_ime_korisnika = k.getAutentifikacija().getUsername(); 
        String hash_sifre_korisnika = k.getAutentifikacija().getHashPassword();
        String tip_korisnika = "korisnik";
        String adresa_korisnika = k.getDeskripcija().getAdress();
        String telefon_korisnika = k.getDeskripcija().getPhones();
        String radno_mjesto_korisnika = k.getDeskripcija().getJobs();
        String elpostakorisnika = k.getDeskripcija().getEmails(); 
        String vebsajtovikorisnika = k.getDeskripcija().getWebs();
        String salt_sifre_korisnika = k.getAutentifikacija().getSalt();
        String stat = SQLUtil.getStatement("dodavanje",identifikator_korisnika,
                ime_korisnika,prezime_korisnika, korisnicko_ime_korisnika, 
                hash_sifre_korisnika, tip_korisnika, adresa_korisnika,
                telefon_korisnika, radno_mjesto_korisnika, elpostakorisnika,
                vebsajtovikorisnika,salt_sifre_korisnika);
        sta.execute(stat);
        sta.close();
    }
    
    /**
     * Brisanje korisnika iz baze
     * @param k korisnik
     * @throws SQLException 
     */
    public synchronized void obrisiKorisnika(Korisnik k) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(k)) return;
        k.getAutentifikacija().gotoHashMode(saltManager, passManager);
        Statement sta = konekcija.createStatement();
        String identifikator_korisnika = k.getDeskripcija().getIdentificator();
        String ime_korisnika = k.getDeskripcija().getFirstname();
        String prezime_korisnika = k.getDeskripcija().getLastname();
        String korisnicko_ime_korisnika = k.getAutentifikacija().getUsername(); 
        String hash_sifre_korisnika = k.getAutentifikacija().getHashPassword();
        String tip_korisnika = "korisnik";
        String adresa_korisnika = k.getDeskripcija().getAdress();
        String telefon_korisnika = k.getDeskripcija().getPhones();
        String radno_mjesto_korisnika = k.getDeskripcija().getJobs();
        String elpostakorisnika = k.getDeskripcija().getEmails(); 
        String vebsajtovikorisnika = k.getDeskripcija().getWebs();
        String stat = SQLUtil.getStatement("brisanje",identifikator_korisnika,
                ime_korisnika,prezime_korisnika, korisnicko_ime_korisnika, 
                hash_sifre_korisnika, tip_korisnika, adresa_korisnika,
                telefon_korisnika, radno_mjesto_korisnika, elpostakorisnika,
                vebsajtovikorisnika);
        sta.execute(stat);
        sta.close();
    }
    
    /**
     * Ucitavanje korisnika
     * @throws SQLException 
     */
    public synchronized void ucitavanje() throws SQLException{
        String stat = SQLUtil.getStatement("ucitavanje");
        Statement sta = konekcija.createStatement();
        
        ResultSet rs = sta.executeQuery(stat);
        while(rs.next()){
            String id = rs.getString(1);
            String ime = rs.getString(2);
            String prezime = rs.getString(3);
            String koime = rs.getString(4);
            String pass = rs.getString(5);
            String tip = rs.getString(6);
            String adresa = rs.getString(7);
            String telefon = rs.getString(8); 
            String posao = rs.getString(9); 
            String email = rs.getString(10);
            String webs = rs.getString(11);
            String salt = rs.getString(12);
            AutentifikacioniPodaci ap = new AutentifikacioniPodaci(koime,pass,salt,saltManager,passManager);
            OpisniPodaci op = new OpisniPodaci(id,ime,prezime,adresa,telefon,posao,email,webs);
            AutorizacioniPodaci atzp = new AutorizacioniPodaci(op,ap);
            RegistracijaControl.dodajKorisnikaBezBaze(atzp);
        }
        
        rs.close();
        sta.close();
    }
    
    /**
     * Zapisivanje promjene parameraea u bazu
     * @param oldusername staro korisnicko ime
     * @param newusername novo korisnicko ime
     * @param op opisni parametri
     * @return rezultat
     * @throws SQLException 
     */
    public synchronized boolean promenaParametara(String oldusername, String newusername, OpisniPodaci op) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(oldusername,newusername)) return false;
        if(!ProvjeraImena.provjeraVSMSBDCM(op)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                SQLNames.bk_programiranje_promena_parametara+"(?,?,?,?,?,?,?,?,?) }");
        sta.setString(1,oldusername);
        sta.setString(2,newusername);
        sta.setString(3,op.getFirstname());
        sta.setString(4,op.getLastname());
        sta.setString(5,op.getAdress());
        sta.setString(6,op.getPhones());
        sta.setString(7,op.getJobs());
        sta.setString(8,op.getWebs());
        sta.setString(9,op.getEmails());
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b; 
    }
    
    /**
     * Promjena sifre za korisnika u bazi
     * @param oldusername staro korisnicko ime
     * @param ap autentifikacioni podaci
     * @return rezultat promjene sifre 
     * @throws SQLException 
     */
    public synchronized boolean promenaSifre(String oldusername, AutentifikacioniPodaci ap) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(oldusername,oldusername)) return false;
        if(!ProvjeraImena.provjeraVSMSBDCM(ap.getPassword())) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                SQLNames.bk_programiranje_promena_sifre+"(?,?,?) }");
        sta.setString(1,oldusername);
        sta.setString(2,ap.getHashPassword());
        sta.setString(3,ap.getSalt());
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b; 
    }
    
    /**
     * Promjena tipa korisnika
     * @param oldusername staro korisnicko ime
     * @param newtype novi tip korisnika
     * @return rezultat operacije
     * @throws SQLException 
     */
    public synchronized boolean promenaTipa(String oldusername, String newtype) throws SQLException{
        if(!ProvjeraImena.provjeraVSMSBDCM(oldusername,newtype)) return false;
        CallableStatement sta = konekcija.prepareCall("{ call "+
                SQLNames.bk_programiranje_promena_tipa+"(?,?) }");
        sta.setString(1,oldusername);
        sta.setString(2,newtype);
        ResultSet rs = sta.executeQuery();
        rs.next();
        boolean b = rs.getBoolean(1);
        rs.close();
        sta.close();
        return b; 
    }
    
    public synchronized boolean proveraSifre(String oldusername, AutentifikacioniPodaci ap) 
            throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException{
        if(!ProvjeraImena.provjeraVSMSBDCM(oldusername,oldusername)) return false;
        if(!ProvjeraImena.provjeraVSMSBDCM(ap.getPassword())) return false;
        PreparedStatement saltsta = konekcija.prepareStatement("select salt_sifre_korisnika from bk_programiranje_data \n" +
                "where korisnicko_ime_korisnika=?");
        saltsta.setString(1,oldusername);
        ResultSet saltrs = saltsta.executeQuery();
        saltrs.next();
        String salt = saltrs.getString(1);
        ap.setSalt(salt).gotoHashModeLocal();
        saltrs.close();
        saltsta.close();
        CallableStatement sta = konekcija.prepareCall("{ call "+
                SQLNames.bk_programiranje_provera_sifre_korisnika+"(?,?) }");
        sta.setString(1,oldusername);
        sta.setString(2,ap.getHashPassword());
        ResultSet rs = sta.executeQuery();
        rs.next();
        String s = rs.getString(1);
        boolean b = false;
        if(s.equals("1")) b = true; 
        rs.close();
        sta.close();
        return b; 
    }
}
