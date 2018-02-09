/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.beans;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;

/**
 * Modelza za stranice opisa korisnika
 * @author Mikec
 */
public class StranicaKorisnikaBean implements Serializable{
    private String povratnaStranicaURL = ""; 
    
    /**
     * Konstruktor
     */
    public StranicaKorisnikaBean(){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            Map<String,Object> session = context.getSessionMap();
            session.put("stranicaKorisnikaBean", this);
    }
    
    /**
     * Cuva se i stranica za povratak 
     * @return adresa povratne stranice
     */
    public String getPovratnaStranicaURL(){
        return povratnaStranicaURL;
    }
    
    /**
     * Postavljanje povratne stranice 
     * @param url adresa povratne stranice 
     */
    public void setPovratnaStranicaURL(String url){
        povratnaStranicaURL = url;
    }
    
    /**
     * Inicijalizacija
     */
    public void init(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        setPovratnaStranicaURL(request.getRequestURL().toString());
    }
}
