/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LuisOsvaldo
 */
@WebServlet(urlPatterns = {"/ValidaDatos"})
public class ValidaDatos extends HttpServlet {

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
            out.println("<link href=\"css/estilo.css\" media=\"screen\" rel=\"StyleSheet\" type=\"text/css\">");
            out.println("<title>Test 1</title>");            
            out.println("</head>");
            out.println("<body><center>");
            
            String tarjeta = request.getParameter("tarjeta");
            String x = VerificarTarjeta(tarjeta);
            String run = request.getParameter("run");
            String run2=new StringBuilder(run).reverse().toString();
            String y = ValidarRun(run2);
            out.println("<section>");
            out.println("<table border = \"2px\">"
                    + "<tr><td><center>Datos</center></td><td><center>Valores</center></td></tr>"
                    + "<tr><td>Nombre</td><td>" +request.getParameter("nombre")+" " +request.getParameter("apellido")+"</td></tr>"
                    + "<tr><td>Run</td><td>" +request.getParameter("run")+"</td></tr>"
                    + "<tr><td>Validación Run</td><td>" + y +"</td></tr>"
                    + "<tr><td>Número de Tarjeta</td><td>"+request.getParameter("tarjeta")+"</td></tr>"
                    + "<tr><td>Fecha Vencimiento</td><td>"+request.getParameter("mesano")+"</td></tr>"
                    + "<tr><td>Validación Tarjeta</td><td>" + x +"</td></tr>"
                    + "</table>");
            out.println("<input type=\"button\" value=\"Regresar\" name=\"Regresar\" onclick=\"history.back()\" />");
            
            out.println("</section>");
            out.println("<footer><img src=images/logo.png></footer>");
            out.println("</body></center>");
            out.println("</html>");
        }
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

    public String VerificarTarjeta(String tarjeta){
        char[] caracteresTarjeta = tarjeta.toCharArray();
        switch ( Character.getNumericValue(caracteresTarjeta[0]) ) {
            case 3:
                return (ValidarTarjeta(caracteresTarjeta) == true) ? "<img src=images/amex.png> <img src=images/tick.png>" : "<img src=images/amex.png> <img src=images/cross.png>";
            case 4:
                return (ValidarTarjeta(caracteresTarjeta) == true) ? "<img src=images/visa.png> <img src=images/tick.png>" : "<img src=images/visa.png> <img src=images/cross.png>";
            case 5:
                return (ValidarTarjeta(caracteresTarjeta) == true) ? "<img src=images/mastercard.png> <img src=images/tick.png>" : "<img src=images/mastercard.png> <img src=images/cross.png>";                    
            case 6:
                return (ValidarTarjeta(caracteresTarjeta) == true) ? "<img src=images/discover.png> <img src=images/tick.png>" : "<img src=images/discover.png> <img src=images/cross.png>";
            default :
                return "<img src=images/desc.png> <img src=images/cross.png>";
    }  
}
    public boolean ValidarTarjeta(char[] tarjeta){
        int suma = 0;
        int valor;
        for(int i=0;i<16;i++){
            if(i % 2 == 0 || i == 0){
                valor = (Character.getNumericValue(tarjeta[i]))*2;
                if(valor > 9){
                    valor = valor-9;
                }
                suma = suma + valor;
            }else{
                suma = suma + Character.getNumericValue(tarjeta[i]);
            }
        }
        if(suma % 10 == 0 && suma <= 150){
            return true;
        }else{
            return false;
        }
    }
    public String ValidarRun(String run2){
        
        char[] runInv = run2.toCharArray();
        
        int j = 2;        int k = 11;        int sumaR = 0;
        for (int i=1;i<run2.length();i++){
            if(j>7){j=2;}
            sumaR = sumaR + (Character.getNumericValue(runInv[i]))*j;
            j++;
        }
        int resto = sumaR % k;        k = k-resto;
        
        if (k<10 && k>=0){
            if(k == Character.getNumericValue(runInv[0])){
                return "<img src=images/tick.png>";
            }
        }else if(k == 11 && 0 == Character.getNumericValue(runInv[0])){
            return "<img src=images/tick.png>";
        }else if(k == 10 && (75 == Character.getNumericValue(runInv[0]) || 107 == Character.getNumericValue(runInv[0]))){
            return "<img src=images/tick.png>";
        }        return "<img src=images/cross.png>";
    }

}
