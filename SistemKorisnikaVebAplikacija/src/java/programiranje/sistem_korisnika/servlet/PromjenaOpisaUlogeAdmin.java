/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.sistem_korisnika.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ovomim servletom se generise stranica koja izvsava <br>
 * promjenu opisa korisnika kreiranog od administratora.
 * @author Mikec
 */
public class PromjenaOpisaUlogeAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title></title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<script>history.back();</script>");
            out.println("</body>");
            out.println("</html>");
        }
        
        File dir = new File("./data/opisi");
        if(!dir.exists()) dir.mkdirs();
        String user = request.getParameter("ok_user_clan"); 
        String grupa = request.getParameter("ok_group_clan"); 
        String data = request.getParameter("ok_data_clan");
        data = URLDecoder.decode(data, "UTF-8");
        Date date = new Date();

        File newfile = new File(dir,"uloga."+date.toString().replaceAll(":",".")+".{"+user+"}.{"+grupa+"}.administrator.html");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(newfile),"UTF-8"));
        for(File file : dir.listFiles())
            if(file.getName().startsWith("uloga.") && file.getName().endsWith(".{"+user+"}.{"+grupa+"}.administrator.html"))
                file.delete();

        pw.println(data);
        pw.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
