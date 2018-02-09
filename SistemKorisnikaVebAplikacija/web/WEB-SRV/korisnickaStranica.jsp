<%-- 
    Document   : KorisnickaStranica
    Created on : Oct 10, 2017, 2:32:11 PM
    Author     : Mikec
--%>

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
      if(bean!=null && bean.getUsername().length()!=0){
        RegistracijaBean rbean = (RegistracijaBean) request.getSession().getAttribute("registracijaBean");
        StranicaKorisnikaBean skbean = (StranicaKorisnikaBean) request.getSession().getAttribute("stranicaKorisnikaBean");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\"/>");
        out.println("<title>Систем корисника</title>");
        out.println("<style>.nazad{color:blue}</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<center>\n" +
"                <font color=\"green\">\n" +
"                    <b>СИСТЕМ КОРИСНИКА<br>СТРАНИЦА КОРИСНИКА</b>\n" +  
"                    <br>Пријављени корисник : "+bean.getUsername()+"\n" +
"                </font><br><br>\n" +
"            <div class='nazad' onclick='history.back()'><u>Почетна страница</u></div>"+
"            </center>");
%>
    <script>
        var unosOpisaKorisnika = false;
        function unosOpisKorisnikaFF(){
            unosOpisaKorisnika = !unosOpisaKorisnika;
            if(unosOpisaKorisnika){
                document.getElementById('unos_opisa_korisnika').style='';
                document.getElementById('ispis_opisa_korisnika').style='display: none';
            }else{
                document.getElementById('unos_opisa_korisnika').style='display: none';
                document.getElementById('ispis_opisa_korisnika').style='';
            }
        }
    </script>
    <script>
        var htmlOpisaKorisnika; 
        function potvrdaOpisaKorisnika(){
            var text = document.getElementById("polje").value;
            document.getElementById("ok_data").value = encodeURI(text);
            document.getElementById("ok_link").click();
        }
        function odstupanjeOdOpisaKorisnika(){
            var text = document.getElementById("ok_data").value;
            text = decodeURI(text); 
            document.getElementById("polje").value = text;
            location.reload();
        }
    </script>
    <%
        String opis = "<html>\n\t<body>\n\t\t<center>Нема описа.</center>\n\t</body>\n</html>";
        try{
            File dir = new File("./data/opisi");
            if(!dir.exists()) dir.mkdirs();
            for(File file : dir.listFiles())
                if(file.getName().startsWith("korisnik.") && file.getName().endsWith("."+bean.getUsername()+".html"))
                    if(opis!=null) {
                        opis = FileUtil.readText(file);
                    }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    %>
    
    <center>
        <div class='nazad' onclick='unosOpisKorisnikaFF()'><u>Управљање описом</u></div>
        <br>
        <form style="display:none" id="unos_opisa_korisnika" method="post">
            <font face="Courier New">
                <textarea id="polje" cols="80" rows="25"><%=
                    XMLUtil.getXMLEmbended(opis)
                %></textarea><br>
            </font>
            <input type="button" id="da" value="Прихватање" onclick="potvrdaOpisaKorisnika()"/>
            <input type="button" id="ne" value="Одступање" onclick="unosOpisKorisnikaFF(); odstupanjeOdOpisaKorisnika();"/><br>
        </form>
    </center>
    <div id="ispis_opisa_korisnika"><%=
            XMLUtil.getXMLInFirstKeyTag(opis,"<body>","</body>")
    %></div>
    <form id="ok_form" method="post" action="/SistemKorisnikaVebAplikacija/PromjenaOpisaKorisnika" accept-charset="UTF-8">
        <input type="submit" id="ok_link" name="ok_link" style="display:none"/>
        <input type="hidden" id="ok_data" name="ok_data" value=""/>
        <%="<input type=\"hidden\" id=\"ok_user\" name=\"ok_user\" value=\""+bean.getUsername()+"\"/>"%>
    </form>
    <script>
        document.getElementById("ok_data").value = encodeURI(document.getElementById("polje").value);
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
    %>
    <center>
        <font color="red">
            <b>БАЗА КОРИСНИКА<br>ГРЕШКА</b>
        </font>
        <p><b>Не постоји веза са сервером.</b></p>
        <div><font color="blue" onclick="history.back()"><u>НАЗАД</u></font></div>
    </center>
<%
        out.println("</body>");
        out.println("</html>");
    }
%>