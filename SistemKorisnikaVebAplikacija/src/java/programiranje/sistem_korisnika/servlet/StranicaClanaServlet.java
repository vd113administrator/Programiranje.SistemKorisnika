/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import programiranje.baza_korisnika_web.beans.data.PrijavaBean;
import programiranje.baza_korisnika_web.beans.data.RegistracijaBean;
import programiranje.sistem_korisnika.grupe.beans.StranicaKorisnikaBean;
import programiranje.sistem_korisnika_web.global.SKGlobalneFunkcije;
import programiranje.sistem_korisnika_web.text.FileUtil;
import programiranje.sistem_korisnika_web.text.XMLUtil;

/**
 * Servlet kojim se generise stranica kojom se biografije korisnika daju kao biografije clana 
 * @author Mikec
 */
public class StranicaClanaServlet extends HttpServlet {
    /**
     * Izvrsavanje generisanja stranice
     * @param request zahtijev 
     * @param response povrat
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrijavaBean bean = (PrijavaBean) request.getSession().getAttribute("prijavaBean");
        RegistracijaBean rbean = (RegistracijaBean) request.getSession().getAttribute("registracijaBean");
        StranicaKorisnikaBean skbean = (StranicaKorisnikaBean) request.getSession().getAttribute("stranicaKorisnikaBean");
    
        String grupa = SKGlobalneFunkcije.getImeCiljneGrupe();
        String korisnik = SKGlobalneFunkcije.getImeCiljnogKorisnika();
        
        response.setContentType("text/html;charset=UTF-8");
        if(bean!=null)
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Систем корисника</title>");
            out.println("<style>.nazad{color:blue}</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>\n" +
"                <font color=\"green\">\n" +
"                    <b>СИСТЕМ КОРИСНИКА<br>СТРАНИЦА ЧЛАНА</b>\n" +  
"                    <br>Пријављени корисник : "+bean.getUsername()+"\n" +
"                    <br>Тражена група : "+grupa+"\n"+
"                    <br>Тражени корисник : "+korisnik+"\n"+
"                </font><br><br>\n" +
"            <div class='nazad' onclick='history.back()'><u>Почетна страница</u></div>"+
"            </center><br>");
            
            String opis = "<html>\n\t<body>\n\t\t<center>Нема описа.</center>\n\t</body>\n</html>";
            
            try{
                File dir = new File("./data/opisi");
                if(!dir.exists()) dir.mkdirs();
                for(File file : dir.listFiles())
                    if(file.getName().startsWith("korisnik.") && file.getName().endsWith("."+korisnik+".html"))
                        if(opis!=null) {
                            opis = FileUtil.readText(file);
                        }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
            out.println(XMLUtil.getXMLInFirstKeyTag(opis,"<body>","</body>"));
            out.println("</body>");
            out.println("</html>");
        }
        else{
            try (PrintWriter out = response.getWriter()) {
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
"    </center>");
            out.println("</body>");
            out.println("</html>");
          }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Servlet za pregled informacija grupe.";
    }
}
