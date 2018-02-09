/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_web.beans;

import java.io.IOException;
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
 * Kontrola stranice za registraciju 
 * @author Mikec
 */
public class SignUpController {
    private DriverManager dm;
    
    /**
     * Konstruktor 
     */
    public SignUpController(){
        DriverManager dm = SKGlobalneFunkcije.getDriverManager();
        this.dm = dm; 
    }
    
    /**
     * Osnovna stranica 
     * @return JSF povrat
     */
    public String mainpage(){
        return "mainpageUp";
    }
    
    /**
     * Prihvatanje registraicje 
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
        
        String username = request.getParameter("signUpForm:username");
        String password = request.getParameter("signUpForm:password");
        String name = request.getParameter("signUpForm:name");
        String surname = request.getParameter("signUpForm:surname");
        String identificator = request.getParameter("signUpForm:id");
        String webs = request.getParameter("signUpForm:webs"); 
        String email = request.getParameter("signUpForm:email");
        String workplaces = request.getParameter("signUpForm:workplace"); 
        String telephone = request.getParameter("signUpForm:telephone");
        String address = request.getParameter("signUpForm:address");
        
        rbean.setUsername(username);
        rbean.setPassword(password);
        rbean.setName(name);
        rbean.setSurname(surname);
        rbean.setIdentificator(identificator);
        rbean.setWebs(webs); 
        rbean.setEmail(email);
        rbean.setWorkplaces(workplaces);
        rbean.setTelephone(telephone); 
        rbean.setAddress(address);
        
        bean.setUsername(username);
        bean.setPassword(password);
        
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        
        try{
            server.registracijaOut(rbean.asSignUpList());
            Pair<String,String> res = server.registracijaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(res.getValue());
                return "submitUp";
            }
            else {
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
                ErrorBean ebean = new ErrorBean();
                ebean.setError(res.getValue());
                session.put("errorBean", ebean);
                return "errorUp";
            }
        } catch (Exception ex){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
                ErrorBean ebean = new ErrorBean();
                ebean.setError("Не постоји веза са сервером.");
                session.put("errorBean", ebean);
                return "errorUp";
        } 
    }
}
