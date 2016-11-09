/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kookoo.outbound;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * outbound callback will be post request
 */
@WebServlet(name = "outbound_callstatus", urlPatterns = {"/outbound_callstatus"})
public class outbound_callstatus extends HttpServlet {

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

        String uri = request.getRequestURI();
        System.out.println("kookoo outbound status : " + uri);/*here I am just kookoo final xml prepared*/
        if (request.getQueryString() != null) 
        {
            uri += "?" + request.getQueryString();
        }
        /*printing outbound callback parameters*/
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                System.out.println("param : " + paramName + "=" + paramValue);
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("OK");
            out.close();
            /*no need to send any response back just printing that we have got data*/
        }

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
