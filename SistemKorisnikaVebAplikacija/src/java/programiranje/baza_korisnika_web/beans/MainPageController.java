/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans;

import java.io.IOException;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import programiranje.baza_korisnika_web.beans.data.ErrorBean;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import static programiranje.baza_korisnika_web.global.ProtokolBKSP.BKSP_CLOSE;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;

/**
 * Kontrola pocetne strane 
 * @author Mikec
 */
public class MainPageController { 
    /**
     * Prelaz na strabicu za prijavljivanje 
     * @return JSF povrat za - stranica za registrovanje 
     */
    public String signup(){
        GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
        GlobalneFunkcije.resetSessionInfo();
        SKGlobalneFunkcije.resetSessionInfo();
        return "signup";
    }
    /**
     * Prelaz na stranicu za registrovanje 
     * @return JSF povrat za - stranica za registrovanje 
     */
    public String signin(){ 
        GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
        GlobalneFunkcije.resetSessionInfo();
        SKGlobalneFunkcije.resetSessionInfo();
        return "signin"; 
    }
    /**
     * Stranica za konekciju 
     * @return JSF povrat za - stranica za konekciju 
     */
    public String connect(){ 
        if(GlobalneFunkcije.getSesijskiObjekti().getConnectionId()==null){
            GlobalneFunkcije.getSesijskiObjekti().setShellServer();
            return "mainpage";
        }else {
            disconnect();
            
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            Map<String,Object> session = context.getSessionMap();
            
            GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
            GlobalneFunkcije.getSesijskiObjekti().setConnectionId(null);
            ErrorBean ebean = new ErrorBean();
            ebean.setError("Дупла конекција");
            session.put("errorBean", ebean);
            return "errorpage";
        }
    }
    /**
     * Stranica za razkonekiciju 
     * @return JSF povrat za raskonekciju 
     */
    public String disconnect(){
       try{
        if(GlobalneFunkcije.getSesijskiObjekti().getSessionId()!=null){
            GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().odijavaOut();
        }
        if(GlobalneFunkcije.getSesijskiObjekti().getConnectionId()!=null){
             GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().writeLine(BKSP_CLOSE);
        }
       }catch(Exception ex){
       }
       return "mainpage";
    }
    
    /**
     * Stranica odjave 
     * @return JSF povrat za stranicu odjave
     */
    public String logout(){
        GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
        GlobalneFunkcije.resetSessionInfo();
        SKGlobalneFunkcije.resetSessionInfo();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null)
            GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().odijavaOut();
        session.remove("registracijaBean");
        session.remove("prijavaBean");
        return "signin";
    }
    
    /**
     * Deregistracija 
     * @return JSF povrat za deregistraciju  
     */
    public String deregistracija(){
        GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        if(GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter()!=null){
            PrijavaBean prijava = (PrijavaBean) session.remove("prijavaBean");
            try {
                GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().deregistracijaOut(prijava.asList());
                GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().deregistracijaIn();
            }
            catch (IOException ex) {}
        };
        session.remove("registracijaBean");
        session.remove("prijavaBean");
        return "signin";
    }
}
