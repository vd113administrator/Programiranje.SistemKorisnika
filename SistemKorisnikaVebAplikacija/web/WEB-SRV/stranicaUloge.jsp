<%-- 
    Document   : stranicaUloge
    Created on : Oct 11, 2017, 3:25:24 PM
    Author     : Mikec
--%>

<%@page import="programiranje.sistem_korisnika_web.opis.kontrola.Informacije"%>
<%@page import="programiranje.sistem_korisnika_web.text.XMLUtil"%>
<%@page import="programiranje.sistem_korisnika.grupe.beans.StranicaKorisnikaBean"%>
<%@page import="programiranje.baza_korisnika_web.beans.data.RegistracijaBean"%>
<%@page import="programiranje.baza_korisnika_web.beans.data.PrijavaBean"%>
<%@page import="programiranje.sistem_korisnika_web.text.FileUtil"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.File"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@page import="programiranje.baza_korisnika_web.global.GlobalneFunkcije"%>
<%@page import="programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije"%>

<%        
        PrijavaBean bean = (PrijavaBean) request.getSession().getAttribute("prijavaBean");
        RegistracijaBean rbean = (RegistracijaBean) request.getSession().getAttribute("registracijaBean");
        StranicaKorisnikaBean skbean = (StranicaKorisnikaBean) request.getSession().getAttribute("stranicaKorisnikaBean");
    
        String grupa = SKGlobalneFunkcije.getImeCiljneGrupe();
        String korisnik = SKGlobalneFunkcije.getImeCiljnogKorisnika();
        String admin = SKGlobalneFunkcije.getImeCiljnogAdministratora();
        
        response.setContentType("text/html;charset=UTF-8");
        if(bean!=null){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Систем корисника</title>");
            out.println("<style>.nazad{color:blue}</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>\n" +
"                <font color=\"green\">\n" +
"                    <b>СИСТЕМ КОРИСНИКА<br>СТРАНИЦА УЛОГЕ</b>\n" +  
"                    <br>Пријављени корисник : "+bean.getUsername()+"\n" +
"                    <br>Тражена група : "+grupa+"\n"+
"                    <br>Тражени корисник : "+korisnik+"\n"+
"                </font><br><br>\n" +
"            <div class='nazad' onclick='history.back()'><u>Почетна страница</u></div>"+
"            </center>");
%>
     <script>
        function potvrdaOpisaUloge(){
            var text = document.getElementById("polje").value;
            document.getElementById("ok_data").value = encodeURI(text);
            document.getElementById("ok_link").click();
        }
        function potvrdaOpisaUlogeClana(){
            var text = document.getElementById("polje_clana").value;
            document.getElementById("ok_data_clan").value = encodeURI(text);
            document.getElementById("ok_link_clan").click();
        }
        function odstupanjeOdOpisaUloge(){
            var text = document.getElementById("ok_data").value;
            text = decodeURI(text); 
            document.getElementById("polje").value = text;
            var domtext = document.createElement('div'); 
            domtext.innerHTML = text; 
            document.getElementById("ispis_opisa_uloge").innerHTML = domtext.innerHTML;
        }
        function odstupanjeOdOpisaUlogeClana(){
            var text = document.getElementById("ok_data_clan").value;
            text = decodeURI(text);
            document.getElementById("polje_clana").value = text;
            var domtext = document.createElement('div');
            domtext.innerHTML = text;
            document.getElementById("ispis_opisa_uloge_clana").innerHTML = domtext.innerHTML;
        }
     </script>
     <script>
        var unosOpisaUloge = false;
        function unosOpisUlogeFF(){
            unosOpisaUloge = !unosOpisaUloge;
            if(unosOpisaUloge){
                document.getElementById('unos_opisa_uloge').style='';
                document.getElementById('ispis_opisa_uloge').style='display: none';
            }else{
                document.getElementById('unos_opisa_uloge').style='display: none';
                document.getElementById('ispis_opisa_uloge').style='';
            }
        }
        
        var unosOpisaUlogeClana = false;
        function unosOpisUlogeClanaFF(){
            unosOpisaUlogeClana = !unosOpisaUlogeClana;
            if(unosOpisaUlogeClana){
                document.getElementById('unos_opisa_uloge_clana').style='';
                document.getElementById('ispis_opisa_uloge_clana').style='display: none';
            }else{
                document.getElementById('unos_opisa_uloge_clana').style='display: none';
                document.getElementById('ispis_opisa_uloge_clana').style='';
            }
        }
    </script>
    
    <form id="ok_form" method="post" action="/SistemKorisnikaVebAplikacija/PromjenaOpisaUloge">
        <input type="submit" id="ok_link" name="ok_link" style="display:none"/>
        <input type="hidden" id="ok_data" name="ok_data" value=""/>
        <%="<input type=\"hidden\" id=\"ok_user\" name=\"ok_user\" value=\""+korisnik+"\"/>"%>
        <%="<input type=\"hidden\" id=\"ok_group\" name=\"ok_group\" value=\""+grupa+"\"/>"%>
    </form>
    
    <form id="ok_form_clan" method="post" action="/SistemKorisnikaVebAplikacija/PromjenaOpisaUlogeAdmin">
        <input type="submit" id="ok_link_clan" name="ok_link_clan" style="display:none"/>
        <input type="hidden" id="ok_data_clan" name="ok_data_clan" value=""/>
        <%="<input type=\"hidden\" id=\"ok_user_clan\" name=\"ok_user_clan\" value=\""+korisnik+"\"/>"%>
        <%="<input type=\"hidden\" id=\"ok_group_clan\" name=\"ok_group_clan\" value=\""+grupa+"\"/>"%>
    </form>

    <%
        String opis = "<html>\n\t<body>\n\t\t<center>Нема описа.</center>\n\t</body>\n</html>";
        String opisClana = "<html>\n\t<body>\n\t\t<center>Нема описа.</center>\n\t</body>\n</html>";
        try{
          File dir = new File("./data/opisi");
            if(!dir.exists()) dir.mkdirs();
            for(File file : dir.listFiles())
                if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+korisnik+"}.{"+grupa+"}.clan.html"))
                    if(opis!=null) {
                        opis = FileUtil.readText(file);
                    }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        try{
          File dir = new File("./data/opisi");
            if(!dir.exists()) dir.mkdirs();
            for(File file : dir.listFiles())
                if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+korisnik+"}.{"+grupa+"}.administrator.html"))
                    if(opis!=null) {
                        opisClana = FileUtil.readText(file);
                    }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        Informacije info = new Informacije();
        boolean admink = info.administratorKorisnik();
        boolean loggin = info.prijavljenKorisnik();
        boolean adminp = info.prijavljenAdmin();
        boolean imap = info.imaPrijavljenKorisnik();
    %>
    
    <center>
        <%if((!admink || !adminp) && imap){%>
        <%if(loggin && !adminp){%>
        <center>
          <div class='nazad' onclick='unosOpisUlogeFF()'><u>Сопствени опис улоге у групи</u></div>
        </center>
        <%}else{%>
        <center>
          <div><u>Сопствени опис улоге у групи</u></div>
        </center>
        <%}%>
        <br>
        <form style="display:none" id="unos_opisa_uloge" method="post">
            <font face="Courier New">
                <textarea id="polje" cols="80" rows="25"><%=
                    XMLUtil.getXMLEmbended(opis)
                %></textarea><br>
            </font>
            <input type="button" id="da" value="Прихватање" onclick="potvrdaOpisaUloge()"/>
            <input type="button" id="ne" value="Одступање" onclick="unosOpisUlogeFF();odstupanjeOdOpisaUloge()"/><br>
        </form>
        </center>
        <div id="ispis_opisa_uloge"><%=
            XMLUtil.getXMLInFirstKeyTag(opis,"<body>","</body>")
        %></div>
        <br>
        <%}if(imap){%>
        <%if((!loggin && admink && adminp) || adminp){%>
        <center>
        <div class='nazad' onclick='unosOpisUlogeClanaFF()'><u>Администраторов опис улоге у групи</u></div>
        </center>
        <%}else{%>
        <center>
        <div><u>Администраторов опис улоге у групи</u></div>
        </center>
        <%}%>
        <br>
        <center>
        <form style="display:none" id="unos_opisa_uloge_clana" method="post">
            <font face="Courier New">
                <textarea id="polje_clana" cols="80" rows="25"><%=
                    XMLUtil.getXMLEmbended(opisClana)
                %></textarea><br>
            </font>
            <input type="button" id="da" value="Прихватање" onclick="potvrdaOpisaUlogeClana()"/>
            <input type="button" id="ne" value="Одступање" onclick="unosOpisUlogeClanaFF(); odstupanjeOdOpisaUlogeClana()"/><br>
        </form>
       </center>
       <div id="ispis_opisa_uloge_clana"><%=
            XMLUtil.getXMLInFirstKeyTag(opisClana,"<body>","</body>")
       %></div>
       <%}%>
        <script>
            document.getElementById("ok_data").value = encodeURI(document.getElementById("polje").value);
            document.getElementById("ok_data_clan").value = encodeURI(document.getElementById("polje_clana").value);
        </script>
        <%
            out.println("</body>");
            out.println("</html>");
        }
        else{
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Систем корисника</title>");
            out.println("<style>.nazad{color:blue}</style>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<center>\n" +
"        <font color=\"red\">\n" +
"            <b>БАЗА КОРИСНИКА<br>ГРЕШКА</b>\n" +
"        </font>\n" +
"        <p>\n" +
"            <b>Не постоји веза са сервером.</b> \n" +
"        </p>\n" +
"        <div><font color=\"blue\" onclick=\"history.back()\"><u>НАЗАД</u></font></div>\n" +
"        </center>");
            out.println("</body>");
            out.println("</html>");
        }
%>