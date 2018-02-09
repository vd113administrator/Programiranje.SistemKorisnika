/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika_web.global;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import programiranje.sistem_korisnika_web.driver.DriverManager;

/**
 * Globalne funkcije u vlasnistvu sistema korisnika
 * @author Mikec
 */
public class SKGlobalneFunkcije {
    private static ExternalContext lastContext;
    
    /**
     * Dobijanje JSF konteksta
     * @return JSF kontekst
     */
    private static ExternalContext getExternalContext(){
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if(fcontext==null) return lastContext;
        ExternalContext context = fcontext.getExternalContext();
        if(context==null) return lastContext;
        else return lastContext = context;
    }
    
    /**
     * Mod selekcije grupe (izbor: vlasnik, clan, sve)
     * @return  mod
     */
    public synchronized static String getSelekcijaGrupaMode(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("selekcijaGrupeMode");
        if(si == null) {
            si = new String("1");
            session.put("selekcijaGrupeMode", si);
        }
        return si; 
    }
    
    /**
     * Postavljanje selekcije grupe 
     * @param grupe mod (1,2,3) - string
     */
    public synchronized static void setSelekcijaGrupaMode(String grupe){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = grupe;
        if(si == null) si = new String("1");
        session.put("selekcijaGrupeMode", si);
    }
    
    /**
     * Informacija o prikazu stranice grupe
     * @return stranica
     */
    public synchronized static int getStranicaGrupa(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        Integer si = (Integer) session.get("stranicaGrupa");
        if(si == null) {si = 1; session.put("stranicaGrupa", si);}
        return si;
    }
    
    /**
     * Postavljanje stranice grupe 
     * @param stranicaGrupa stranica opisa vlasnika ili grupa (1 ili 2) 
     */
    public synchronized static void setStranicaGrupa(int stranicaGrupa){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        Integer si = stranicaGrupa;
        if(si == null) si = 1;
        session.put("stranicaGrupa", si);
    }
    
    /**
     * Interni JSF-JavaScript prenosni podaci
     * @return podatak
     */
    public synchronized static String getPoljeNoveGrupe(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("poljeNoveGrupe");
        if(si == null) {si = ""; session.put("poljeNoveGrupe", si);}
        return si; 
    }
    
    /**
     * Interni JSF-JavaScript prenosi podaci 
     * @param polje novi podatak
     */
    public static void setPoljeNoveGrupe(String polje){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = polje;
        if(si == null) si = "";
        session.put("poljeNoveGrupe", si);
    }
    
    /**
     * Dobijanje HTML-a sa listom korisnika 
     * @return korisnici u HTML 
     */
    public synchronized static String getKorisniciInfoHTML(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("kinfoHTML");
        if(si == null) {si = new String(""); session.put("kinfoHTML", si);}
        return si; 
    }
    
    /**
     * Postavljanje HTML-a za korisnike
     * @param infoHTML korisnici u HTML
     */
    public synchronized static void setKorisniciInfoHTML(String infoHTML){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = infoHTML;
        if(si == null) si = new String(""); 
        session.put("kinfoHTML", si);
    }
    
    /**
     * Resetovanje sesijskih infromacija
     */
    public synchronized static void resetSessionInfo(){
        SKGlobalneFunkcije.setPoljeNoveGrupe("");
        SKGlobalneFunkcije.setSelekcijaGrupaMode("1");
        SKGlobalneFunkcije.setStranicaGrupa(1);
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
    }
    
    /**
     * Dobijanje imena ciljne grupe
     * @return ime ciljne grupe
     */
    public static String getImeCiljneGrupe(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("ciljnaGrupa");
        if(si == null) {si = new String(""); session.put("ciljnaGrupa", si);}
        return si; 
    }
    
    /**
     * Postavljanje imena ciljne grupe
     * @param grupe novo ime ciljne grupe
     */
    public static void setImeCiljneGrupe(String grupe){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = grupe;
        if(si == null) si = new String(""); 
        session.put("ciljnaGrupa", si);
    }
    
    /**
     * Dobijanje imena ciljnog korisnika
     * @return ime ciljnog korisnika
     */
    public static String getImeCiljnogKorisnika(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("ciljniKorisnik");
        if(si == null) {si = new String(""); session.put("ciljniKorisnik", si);}
        return si; 
    }
    
    /**
     * Postavljanje ciljnog korisnika
     * @param grupe novi ciljni korisnik 
     */
    public static void setImeCiljnogKorisnika(String grupe){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = grupe;
        if(si == null) si = new String(""); 
        session.put("ciljniKorisnik", si);
    }
    
    /**
     * Postavljanje imena ciljnog administratora
     * @return ime ciljnog administratora
     */
    public static String getImeCiljnogAdministratora(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("ciljniAdmin");
        if(si == null) {si = new String(""); session.put("ciljniAdmin", si);}
        return si; 
    }
    
    /**
     * Postavljanje imena ciljnog administratora 
     * @param admin ime ciljnog administratora
     */
    public static void setImeCiljnogAdministratora(String admin){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = admin;
        if(si == null) si = new String(""); 
        session.put("ciljniAdmin", si);
    }
    
    /**
     * Postavljanje menadzera za upravljanje dogadjajima 
     * @param dm menadzer dogadjaja 
     */
    public static void setDriverManager(DriverManager dm){
        if(getDriverManager()!=null) return; 
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        if(dm == null)  dm = new DriverManager();
        session.put("driverManager", dm);
    }
    
    /**
     * Dobijanje menadzera dogadjaja 
     * @return menadzer dogadjaja 
     */
    public static DriverManager getDriverManager(){
        ExternalContext context = getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        DriverManager si = (DriverManager) session.get("driverManager");
        if(si == null) {
            si = new DriverManager();
            session.put("driverManager", si);
        }
        return si;
    }
}
