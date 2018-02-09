/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.data;

/**
 * Instanca koja objedinjuje opisne podatke korisnika, odnosno model im je
 * @author Mikec
 */
public class OpisniPodaci {
    private String identificator = ""; 
    private String firstname = ""; 
    private String lastname = "";
    private String jobs = ""; 
    private String adress = "";
    private String emails = "";
    private String webs = "";
    private String phones = ""; 
    
    /**
     * Konstruktor sa minimalnim brojem 
     * @param id identifikacija
     * @param name ime 
     * @param lname prezime
     * @param uname korisnicko ime
     * @param pass sifra
     */
    public OpisniPodaci(String id, String name, String lname, String uname, String pass){
        identificator = id;
        firstname = name;
        lastname = lname;
    }
    
    /**
     * Konstruktor sa svim podacima od kojih neki mogu biti null, odnosno neponati
     * @param id identifikator
     * @param name ime
     * @param lname prezime 
     * @param address adresa
     * @param phones telefoni
     * @param jobs poslovi
     * @param emails emailovi
     * @param webs vebovi
     */
    public OpisniPodaci(String id, String name, String lname, 
                              String address, String phones, String jobs, String emails, String webs){
        identificator = id;
        firstname = name;
        lastname = lname;
        this.adress = address; 
        this.phones = phones;
        this.jobs = jobs; 
        this.emails = emails;
        this.webs = webs; 
        if(this.adress==null) this.adress = ""; 
        if(this.phones==null) this.phones = ""; 
        if(this.jobs==null) this.jobs = "";
        if(this.webs==null) this.webs = "";
        if(this.emails==null) this.emails = "";
    }
    
    /**
     * Dobijanje identifikatora
     * @return identifikator
     */
    public String getIdentificator(){
        return identificator;
    }
    
    /**
     * Dobijanje imena korisnika
     * @return ime
     */
    public String getFirstname(){
        return firstname; 
    }
    
    /**
     * Dobijanje prezimena
     * @return prezime
     */
    public String getLastname(){
        return lastname; 
    }
    
    /**
     * Dobijanje adrese
     * @return adresa
     */
    public String getAdress(){
        return adress;
    }
    
    /**
     * Dobijanje poslova
     * @return poslovi
     */
    public String getJobs(){
        return jobs;
    }
    
    /**
     * Dobijanje telefona
     * @return telefoni
     */
    public String getPhones(){
        return phones; 
    }
    
    /**
     * Dobijanje elektronske poste
     * @return emailovi
     */
    public String getEmails(){
        return emails;
    }
    
    /**
     * Dobijanje veb adresa
     * @return vebovi
     */
    public String getWebs(){
        return webs; 
    }
    
    /**
     * Poredjenje po identifikatoru 
     * @param o instanca opisnih podataka 
     * @return rezultat poredjenja
     */
    @Override 
    public boolean equals(Object o){
        if(o instanceof OpisniPodaci){
            OpisniPodaci a = (OpisniPodaci) o;
            return identificator.equals(a.identificator);
        }
        return false; 
    }
    
    
    /**
     * Jednakost po imenu i prezimenu 
     * @param o instanca opisnih podataka
     * @return rezultat poredjenja
     */
    public boolean nameequals(OpisniPodaci o){
        if(!identificator.equals(o.identificator)) return false; 
        if(!firstname.equals(o.firstname)) return false;
        if(!lastname.equals(o.lastname)) return false;
        return true; 
    }

    /**
     * Jednakost po svim opisnim podacima
     * @param o instanca opisnih podataka
     * @return rezultat poredjenja 
     */
    public boolean fullequals(OpisniPodaci o){
        if(!identificator.equals(o.identificator)) return false; 
        if(!firstname.equals(o.firstname)) return false;
        if(!lastname.equals(o.lastname)) return false;
        if(!adress.equals(o.adress)) return false; 
        if(!phones.equals(o.phones)) return false;
        if(!jobs.equals(o.jobs)) return false;
        if(!emails.equals(o.emails)) return false;
        if(!webs.equals(o.webs)) return false;
        return true; 
    }
    
    /**
     * Provjera da li podaci date instance odgoaraju lokalnim podacima
     * @param ap
     * @throws InvalidDescriptionException 
     */
    public void odgovara(OpisniPodaci ap) throws InvalidDescriptionException{
        if(!fullequals(ap)) throw new InvalidDescriptionException();
    } 
    
    /**
     * String sa imenom i prezimenom 
     * @return ime i prezime 
     */
    @Override 
    public String toString(){
        return firstname+" "+lastname; 
    }
}
