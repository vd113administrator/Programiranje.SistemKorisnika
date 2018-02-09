/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.global;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Globalne funkcije na nivou veb aplikacije
 * @author Mikec
 */
public final class GlobalneFunkcije {
    private GlobalneFunkcije(){} 
    
    /**
     * Dobijenje sesijskih objekata
     * @return objekti vezanih za sesije 
     */
    public static SesijskiObjekti getSesijskiObjekti(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        SesijskiObjekti si = (SesijskiObjekti) session.get("sesijskiObjekti");
        if(si == null) {si = new SesijskiObjekti(); session.put("sesijskiObjekti",si);}
        return si; 
    }
    
    /**
     * Informacija o poruki kod nekih dijelova
     * @return informacija o poruci
     */
    public static String getPorukaInfo(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("porukaInfo");
        if(si == null) {si = new String(); session.put("porukaInfo", si);}
        return si;  
    }
    
    /**
     * Informacija o poruci kod nekih dijelova 
     * @return informacija o poruci
     */
    public static String getGreskaInfo(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = (String) session.get("greskaInfo");
        if(si == null) {si = new String(); session.put("greskaInfo", si);}
        return si; 
    }
    
    /**
     * Resetovanje informacija ove klase 
     */
    public static void resetInfo(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        session.put("porukaInfo", "");
        session.put("greskaInfo", "");
    }
        
    /**
     * Postavljanje nove poruce
     * @param info nova poruka
     */
    public static void setPorukaInfo(String info){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = info; 
        if(si == null) si = new String();
        session.put("porukaInfo", si);
    }
    
    /**
     * Postavljanje nove poruke greske
     * @param info nova poruka o greski 
     */
    public static void setGreskaInfo(String info){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        String si = info; 
        if(si == null) si = new String();
        session.put("greskaInfo", si);
    }
    
    /**
     * Resetovanje sesijske informacije
     */
    public static void resetSessionInfo(){
        resetInfo();
    }
}
