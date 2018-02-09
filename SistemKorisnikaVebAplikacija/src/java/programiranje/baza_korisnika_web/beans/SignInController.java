/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import programiranje.baza_korisnika_web.beans.data.ErrorBean;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.beans.data.RegistracijaBean;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.baza_korisnika_web.global.SesijskiObjekti;
import programiranje.baza_korisnika_web.global.ProtokolBKSP;
import programiranje.sistem_korisnika_web.driver.Driver;
import programiranje.sistem_korisnika_web.driver.DriverManager;
import programiranje.sistem_korisnika_web.driver.UlazniDriver;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;

/**
 * Kontrole stranica za prijavu 
 * @author Mikec
 */
public class SignInController {
    private DriverManager dm; 
    
    /**
     * Konstruktor
     */
    public SignInController(){
        dm = SKGlobalneFunkcije.getDriverManager();
    }
    
    /**
     * Osnovna stranica
     * @return JSF povrat
     */
    public String mainpage(){
        return "mainpageIn"; 
    }
    /**
     * Potvrada prijave
     * @return JSF povrat
     */
    public String submit(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        RegistracijaBean rbean = (RegistracijaBean) session.get("registracijaBean");
       
        Driver d = dm.getFirstDriver(UlazniDriver.class);
        dm.go(UlazniDriver.class, UlazniDriver.Aktivnosti.ULAZNI_PROLAZ.toString());
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        if(rbean==null){
            rbean = new RegistracijaBean(); 
            session.put("registracijaBean", rbean);
        }
        
        if(GlobalneFunkcije.getSesijskiObjekti().getSessionId()!=null){
            GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter().odijavaOut();
        }
        
        String username = request.getParameter("signInForm:username");
        String password = request.getParameter("signInForm:password");
        
        bean.setPassword(password);
        bean.setUsername(username);
        
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        
        try{
            server.prijavaOut(bean.asList());
            Pair<String,String> res = server.prijavaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(res.getValue());
                List<String> l = server.podaciIn().getValue();
                rbean.fromSignInList(l);
                return "submitIn";
            }
            else {
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
                ErrorBean ebean = new ErrorBean();
                ebean.setError(res.getValue());
                session.put("errorBean", ebean);
                return "errorIn";
            }
        } catch (Exception ex){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
                ErrorBean ebean = new ErrorBean();
                ebean.setError("Не постоји веза са сервером.");
                session.put("errorBean", ebean);
                return "errorIn";
        }
    }
}
