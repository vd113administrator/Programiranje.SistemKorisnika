/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.grupe.beans.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import programiranje.baza_korisnika_web.beans.data.ErrorBean;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.control.AdapterServera;
import programiranje.baza_korisnika_web.global.GlobalneFunkcije;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;
import programiranje.sistem_korisnika_web.text.TextUtil;

/**
 * Kontroler za grupe u JSF stranicama 
 * @author Mikec
 */
public class GrupeController{
    /**
     * Stavka dodavanja grupe
     * @return JSF povrat 
     */
    public String dodavanjeGrupe(){
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String groupname = request.getParameterMap().get("grupe_polje")[0];
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        Pair<Boolean,String> res = server.getSKServerAdapter().dodavanjeGrupe(bean, groupname);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        if(res.getKey()) GlobalneFunkcije.setPorukaInfo("Успешно додавање групе");
        else GlobalneFunkcije.setGreskaInfo("Неуспешно додавање групе : "+res.getValue());
        return "dodavanjeGrupe";
    }
    
    /**
     * Brisnaje grupe 
     * @return JSF povrat
     */
    public String brisanjeGrupe(){
        int countTrue = 0;
        int countFalse = 0; 
        int countErase = 0; 
        
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        Map<String, String> mapa = new HashMap<>();
        List<String> lg = new ArrayList<>(); 
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        for(java.util.Map.Entry<String,String[]> o: request.getParameterMap().entrySet()){
            if(o.getKey().startsWith("hd") && o.getKey().length()>2) 
                mapa.put(o.getKey(),o.getValue()[0]);
            else 
                continue; 
            String[] vals =o.getValue()[0].split(":",2);
            if(Boolean.valueOf(vals[1])) {
                countTrue++;
                lg.add(vals[0]);
            }
            else countFalse++;
        }
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        }
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        if(countTrue==0){
            GlobalneFunkcije.setGreskaInfo("Нема селектованих ставки.");
            SKGlobalneFunkcije.setStranicaGrupa(2);
            return "brisanjeGrupe";
        }
        
        for(String lgi: lg){
            if(server.getSKServerAdapter().brisanjeGrupe(bean, lgi).getKey())
                countErase++;
        }
        GlobalneFunkcije.setPorukaInfo("Обрисано "+countErase+" ставки.");
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "brisanjeGrupe";
    }
    
    /**
     * Preimenovanje grupe
     * @return JSF povrat 
     */
    public String preimenovanjeGrupe(){
        int countTrue = 0;
        int countFalse = 0;
        String renameItem = "";
        
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        Map<String, String> mapa = new HashMap<>();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String groupname = request.getParameterMap().get("grupe_polje")[0];
        for(Map.Entry<String,String[]> o: request.getParameterMap().entrySet()){
            if(o.getKey().startsWith("hd") && o.getKey().length()>2) 
                mapa.put(o.getKey(),o.getValue()[0]);
            else 
                continue; 
            String[] vals =o.getValue()[0].split(":",2);
            if(Boolean.valueOf(vals[1])) {
                countTrue++;
                renameItem = vals[0];
            }
            else countFalse++;
        }
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        if(countTrue!=1){
            GlobalneFunkcije.setGreskaInfo("Није јединствена селектована ставка.");
            SKGlobalneFunkcije.setStranicaGrupa(2);
            return "brisanjeGrupe";
        }
        
        boolean res = server.getSKServerAdapter().preimenovanjeGrupe(bean,renameItem, groupname).getKey();
        
        String groupNameHTML = TextUtil.removeALG(groupname);
        String renameItemHTML = TextUtil.removeALG(renameItem);
        if(res) GlobalneFunkcije.setPorukaInfo("Преименованa ставкa <b>"+renameItemHTML+"</b> у <b>"+groupNameHTML+"</b>");
        else GlobalneFunkcije.setGreskaInfo("Није преименованa ставкa <b>"+renameItemHTML+"</b> у <b>"+groupNameHTML+"</b>");
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "preimenovanjeGrupe";
    }
    
    /**
     * Uclanjenje u grupu
     * @return rezultat uclanjenja 
     */
    public String uclanjenje(){
        int countTrue = 0;
        int countFalse = 0;
        
        List<String> grupe = new ArrayList<>(); 
        List<String> clanstvo = new ArrayList<>(); 
        
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        Map<String, String> mapa = new HashMap<>();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
       
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        for(java.util.Map.Entry<String,String[]> o: request.getParameterMap().entrySet()){
            if(o.getKey().startsWith("hd") && o.getKey().length()>2) 
                mapa.put(o.getKey(),o.getValue()[0]);
            else 
                continue; 
            String[] vals =o.getValue()[0].split(":",2);
            if(Boolean.valueOf(vals[1])) {
                countTrue++;
                grupe.add(vals[0]);
            }
            else countFalse++;
        }
        
        if(countTrue==0){
            GlobalneFunkcije.setGreskaInfo("Нема селектованих ставки.");
            SKGlobalneFunkcije.setStranicaGrupa(2);
            return "uclanjenje";
        }
        
        for(String lgi: grupe){
            if(server.getSKServerAdapter().uclanjenje(bean.getUsername(), lgi).getKey())
                clanstvo.add(lgi);
        }
        GlobalneFunkcije.setPorukaInfo("Нова учлањењеа у "+clanstvo);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "uclanjenje";
    }
    
    /**
     * Isclanjenje iz grupe
     * @return rezultat isclanjenja
     */
    public String isclanjenje(){
        int countTrue = 0;
        int countFalse = 0;
        
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        List<String> grupe = new ArrayList<>(); 
        List<String> clanstvo = new ArrayList<>(); 
        
        Map<String, String> mapa = new HashMap<>();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        for(java.util.Map.Entry<String,String[]> o: request.getParameterMap().entrySet()){
            if(o.getKey().startsWith("hd") && o.getKey().length()>2) 
                mapa.put(o.getKey(),o.getValue()[0]);
            else 
                continue; 
            String[] vals =o.getValue()[0].split(":",2);
            if(Boolean.valueOf(vals[1])) {
                countTrue++;
                grupe.add(vals[0]);
            }
            else countFalse++;
        }
        
        if(countTrue==0){
            GlobalneFunkcije.setGreskaInfo("Нема селектованих ставки.");
            SKGlobalneFunkcije.setStranicaGrupa(2);
            return "isclanjenje";
        }
        
        for(String lgi: grupe){
            if(server.getSKServerAdapter().isclanjenje(bean.getUsername(), lgi).getKey())
                clanstvo.add(lgi);
        }
        GlobalneFunkcije.setPorukaInfo("Нова исчлањења из "+clanstvo);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "isclanjenje";
    }
    
    /**
     * Iskljucenje iz grupe 
     * @return JSF povrat
     */
    public String iskljucenje(){
        SKGlobalneFunkcije.setKorisniciInfoHTML("");
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String za = request.getParameter("isk_korisnik");
        String iz = request.getParameter("isk_grupa");
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) {
                session.put("errorBean", new ErrorBean("Грешка при серверу"));
                return "errorGr";
            }
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        Pair<Boolean,String> res = server.getSKServerAdapter().iskljucenje(bean, iz, za);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        if(res.getKey()) 
            GlobalneFunkcije.setPorukaInfo("Корисник "+za+" искључен из групе "+iz);
        else 
            GlobalneFunkcije.setGreskaInfo("Корисник "+za+" није искључен из групе "+iz);
        
        return "iskljucenje";
    }
    
    
    /**
     * Ocitavanje korisnika grupe
     * @return JSF povrat
     */
    public String ocitavanjeKorisnika(){
        int countTrue = 0;
        int countFalse = 0;
        int x=0, y=0; 
        
        List<String> grupe = new ArrayList<>(); 
        Map<String,List<String>> clanstvo = new HashMap<>(); 
        
        Map<String, String> mapa = new HashMap<>();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        Map<String,Object> session = context.getSessionMap();
        PrijavaBean bean = (PrijavaBean) session.get("prijavaBean");
        
        if(bean==null) {
            bean = new PrijavaBean();
            session.put("prijavaBean", bean);
        } 
        
        
        String username = bean.getUsername();
        AdapterServera server = GlobalneFunkcije.getSesijskiObjekti().getShellServerAdapter();
        if(server==null) {
            ErrorBean errorBean = (ErrorBean) session.get("errorBean");
            if(errorBean==null) return "errorGr";
            errorBean.setError("Нема повезаности са сервером.");
            return "errorGr";
        }
        for(java.util.Map.Entry<String,String[]> o: request.getParameterMap().entrySet()){
            if(o.getKey().startsWith("hd") && o.getKey().length()>2) 
                mapa.put(o.getKey(),o.getValue()[0]);
            else 
                continue; 
            String[] vals =o.getValue()[0].split(":",2);
            if(Boolean.valueOf(vals[1])) {
                countTrue++;
                grupe.add(vals[0]);
            }
            else countFalse++;
        }
        
        for(String grupa: grupe){
            List<String> lista = server.getSKServerAdapter().listanjeKorisnikaGrupa(username, grupa); 
            if(lista.size()>0) clanstvo.put(grupa,lista);
        }
        
        String inHTML = "<br><br>";
        if(!clanstvo.isEmpty()){
            inHTML += "<b>КОРИСНИЦИ У ГРУПАМА</b>";
        }
        
        for(Map.Entry<String,List<String>> me: clanstvo.entrySet()){
            inHTML +="<table cellspacing='0' class='checkable'>\n";
            inHTML += "<thead class='checkable'>\n";
            inHTML += "<th class='checkable'>"+TextUtil.removeALG(me.getKey())+"</th>";
            inHTML += "<th class='checkable'></th>";
            inHTML += "<th class='checkable'></th>";
            inHTML += "<th class='checkable'></th>";
            inHTML += "</thead>\n";
            ++x;
            for(String e: me.getValue()){
                ++y;
                inHTML += "<tr class='checkable'>"
                        + "<td class='checkable'><div id='idk."+x+"."+y+"'>"+e+"</div></td>"
                        + "<td class='checkable'><div id='opk."+x+"."+y+"' class='operacija' onclick=\""+generatePodaciKorisnikaJS(me.getKey(),e,x,y,server,bean)+"\">подаци</div></td>"
                        + "<td class='checkable'><div class='operacija' onclick=\""+generatePodaciIskljucenjaJS(me.getKey(),e,x,y,server,bean)+"\">искључење</div></td>" 
                        + "<td class='checkable'>"
                        +"<u><font color='blue'><div onclick=\""
                        + "document.getElementById('isk_grupa').value='"+me.getKey()+"';"
                        + "document.getElementById('isk_korisnik').value='"+e+"';"
                        + "document.getElementById('isk_admin').value='"+server.getSKServerAdapter().getPodaci(me.getKey()).get(1)+"';"
                        + "document.getElementById('korisnici:opis_clana').click();"
                        + "\">Страница члана</div></font></u>"
                        +"<u><font color='blue'><div onclick=\""
                        + "document.getElementById('isk_grupa').value='"+me.getKey()+"';"
                        + "document.getElementById('isk_korisnik').value='"+e+"';"
                        + "document.getElementById('isk_admin').value='"+server.getSKServerAdapter().getPodaci(me.getKey()).get(1)+"';"
                        + "document.getElementById('korisnici:opis_uloge').click();"
                        + "\">Страница улоге</div></font></u>"
                        +"</tr>\n";
            }
            inHTML += "</table><br>\n";
        }
        
        SKGlobalneFunkcije.setKorisniciInfoHTML(inHTML);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        
        return "ocitavanjeKorisnika";
    }
    
    private String generatePodaciKorisnikaJS(String gr, String e,int x,int y, AdapterServera as,PrijavaBean bean){
        return  "if(document.getElementById('opk."+x+"."+y+"').innerHTML==='подаци\'){"
                + "document.getElementById('idk."+x+"."+y+"').innerHTML='<center><b>"+TextUtil.pripremiZaHTML(gr)+"."+TextUtil.pripremiZaHTML(e)+"</b></center>"
                +generisanjeHTMLaPodatakaKorisnika(gr,e,x,y,as,bean)+"\';"
                + "document.getElementById('opk."+x+"."+y+"').innerHTML='корисничко име\';"
                + "}else{"
                + "document.getElementById('idk."+x+"."+y+"').innerHTML='"+TextUtil.pripremiZaHTML(e)+"\';"
                + "document.getElementById('opk."+x+"."+y+"').innerHTML='подаци\';"
                + "}";
    }
    
    private String generatePodaciIskljucenjaJS(String gr, String e, int x, int y, AdapterServera as, PrijavaBean bean){
        return "document.getElementById('isk_admin').value='"+bean.getUsername()+"';"+
                "document.getElementById('isk_grupa').value='"+gr+"';\n"+
                "document.getElementById('isk_korisnik').value='"+e+"';\n"+
                "document.getElementById('korisnici:isk_ok').click()";
    }
    
    private String generisanjeHTMLaPodatakaKorisnika(String gr, String e, int x, int y, AdapterServera as, PrijavaBean bean){
        List<String> lista = as.getSKServerAdapter().listanjePodatakaKorisnikaGrupe(bean.getUsername(), gr, e); 
        String html = "";
               html += String.format("<br><table>"+
                          "<tr>"+
                            "<td>Корисничко име :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Идентификација :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Име :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Презиме :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Адреса :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Радно мјесто :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Телефон :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Електорнска пошта :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                          "<tr>"+
                            "<td>Сајтови :</td>"+
                            "<td>%s</td>"+
                          "</tr>"+
                      "</table>", lista.get(2),
                      lista.get(4), lista.get(0), lista.get(1), lista.get(3),
                      lista.get(5), lista.get(6), lista.get(7), lista.get(8));
        return html; 
    }
    
    /**
     * Ocitavanje opisa za grupe
     * @return JSF povrat
     */
    public synchronized String ocitavanjeOpisaGrupe(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String data = request.getParameter("unos_grupa:gdata");
        String gr = data.split("#o")[0].replaceAll("#k","#");
        String admin = (data.split("#o").length>1)?data.split("#o")[1].replaceAll("#k","#"):"";
        SKGlobalneFunkcije.setImeCiljneGrupe(gr);
        SKGlobalneFunkcije.setImeCiljnogAdministratora(admin);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "opisGrupe"; 
    }
    
    /**
     * Ocitavanje opisa clana grupe
     * @return JSF povrat
     */
    public synchronized String ocitavanjeOpisaClanaGrupe(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String gr  = request.getParameter("isk_grupa");
        String kor = request.getParameter("isk_korisnik");
        String admin =request.getParameter("isk_admin");
        SKGlobalneFunkcije.setImeCiljneGrupe(gr);
        SKGlobalneFunkcije.setImeCiljnogKorisnika(kor);
        SKGlobalneFunkcije.setImeCiljnogAdministratora(admin);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "opisClana";
    }
    
    /**
     * Ocitavanje opisa uloge grupe
     * @return ocitavanje opisa uloge grupe
     */
    public synchronized String ocitavanjeOpisaUlogeGrupe(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String gr  = request.getParameter("isk_grupa");
        String kor = request.getParameter("isk_korisnik");
        String admin =request.getParameter("isk_admin");
        SKGlobalneFunkcije.setImeCiljneGrupe(gr);
        SKGlobalneFunkcije.setImeCiljnogKorisnika(kor);
        SKGlobalneFunkcije.setImeCiljnogAdministratora(admin);
        SKGlobalneFunkcije.setStranicaGrupa(2);
        return "opisUloge";
    }
}
