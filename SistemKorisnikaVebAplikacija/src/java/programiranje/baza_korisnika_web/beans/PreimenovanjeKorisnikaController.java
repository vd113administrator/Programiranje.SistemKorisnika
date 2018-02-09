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
import programiranje.baza_korisnika_shell.data.AutentifikacioniPodaci;
import programiranje.baza_korisnika_shell.data.AutorizacioniPodaci;
import programiranje.baza_korisnika_shell.data.OpisniPodaci;
import programiranje.baza_korisnika_web.autorizacija.AutorizacijaControl;
import programiranje.baza_korisnika_web.beans.data.ErrorBean;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.beans.data.RegistracijaBean;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.baza_korisnika_web.global.ProtokolBKSP;

/**
 * Stranica za preimenovanje korisnika ili promjenu parametara 
 * @author Mikec
 */
public class PreimenovanjeKorisnikaController {
    /**
     * Prihvatanje preimenovanja i/ili promjene parametara 
     * @return JSF prikaz 
     */
    public String preimenovanje(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String username = request.getParameter("preimenovanje_korisnika:username");
        String name = request.getParameter("preimenovanje_korisnika:name");
        String surname = request.getParameter("preimenovanje_korisnika:surname");
        String address = request.getParameter("preimenovanje_korisnika:address");
        String oldpassword = request.getParameter("preimenovanje_korisnika:oldpassword");
        String newpassword = request.getParameter("preimenovanje_korisnika:newpassword");
        String jobs = request.getParameter("preimenovanje_korisnika:jobs");
        String phones = request.getParameter("preimenovanje_korisnika:phones");
        String webs = request.getParameter("preimenovanje_korisnika:webs");
        String emails = request.getParameter("preimenovanje_korisnika:emails");
        
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        RegistracijaBean rbean = (RegistracijaBean) session.get("registracijaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        if(rbean==null){
            rbean = new RegistracijaBean(); 
            session.put("registracijaBean", rbean);
        }
        
        
        String oldusername = bean.getUsername();

        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        
        if(server!=null){
            if(oldpassword.equals(bean.getPassword())){
                AutentifikacioniPodaci ap = new AutentifikacioniPodaci(username, newpassword); 
                OpisniPodaci op = new OpisniPodaci(rbean.getIdentificator(), name, surname, 
                                  address, phones, jobs, emails, webs);
                AutorizacioniPodaci au = new AutorizacioniPodaci(op,ap); 
                if(!AutorizacijaControl.provjeraAutorizacije(au).getKey()){
                    GlobalneFunkcije.setGreskaInfo("Неправилна нова шифра или непотпуни подаци.");
                }
                else{
                    try {
                        server.promenaParametaraOut(oldusername, username, name, surname, address, emails, phones, jobs, webs);
                        boolean ok1 = server.promenaParametaraIn();
                        if(ok1){
                            bean.setUsername(username);
                            server.promenaSifreOut(username, newpassword);
                            boolean ok2 = server.promenaSifreIn();
                            if(ok2) bean.setPassword(newpassword);
                            if(!ok2) GlobalneFunkcije.setPorukaInfo("Успешна промена параметара.");
                            else    GlobalneFunkcije.setPorukaInfo("Успешна промена параметара и шифре.");
                        }else{
                            GlobalneFunkcije.setGreskaInfo("Грешка код преименовања.");
                        }
                    } catch (IOException ex){
                        GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
                        ErrorBean ebean = new ErrorBean();
                        ebean.setError("Грешка : "+ex.getMessage());
                        session.put("errorBean", ebean);
                        return "errorRen";
                    }
                }
            }
            else{
                GlobalneFunkcije.setGreskaInfo("Погрешна стара шифра.");
            }
        }
        try{
            server.prijavaOut(bean.asList());
            Pair<String,String> res = server.prijavaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(res.getValue());
                List<String> l = server.podaciIn().getValue();
                rbean.fromSignInList(l);
            }
        } catch (Exception ex){
            GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
            ErrorBean ebean = new ErrorBean();
            ebean.setError("Грешка : "+ex.getMessage());
            session.put("errorBean", ebean);
            return "errorRen";
        }
        
        return "preimenovanje";
    }
    
    /**
     * Odustajanje od preimenovanja ili promjene parametara
     * @return JSF povrat
     */
    public String povratak(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
       
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        RegistracijaBean rbean = (RegistracijaBean) session.get("registracijaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        if(rbean==null){
            rbean = new RegistracijaBean(); 
            session.put("registracijaBean", rbean);
        }
        
        
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(GlobalneFunkcije.getSesijskiObjekti().getSessionId()!=null) return "povratak"; 
        
        try{
            server.prijavaOut(bean.asList());
            Pair<String,String> res = server.prijavaIn();
            if(res.getKey().equals(ProtokolBKSP.BKSP_SUCCESS)){
                GlobalneFunkcije.getSesijskiObjekti().setSessionId(res.getValue());
                List<String> l = server.podaciIn().getValue();
                rbean.fromSignInList(l);
            }
        } catch (Exception ex){
            GlobalneFunkcije.getSesijskiObjekti().setSessionId(null);
            ErrorBean ebean = new ErrorBean();
            ebean.setError("Грешка : "+ex.getMessage());
            session.put("errorBean", ebean);
            return "errorRen";
        }
        
        return "povratak"; 
    }
}
