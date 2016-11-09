/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kookoo;

import com.ozonetel.kookoo.CollectDtmf;
import com.ozonetel.kookoo.Dial;
import com.ozonetel.kookoo.Response;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pavanj
 */
@WebServlet(name = "incomingcall", urlPatterns = {"/incomingcall"})
public class incomingcall extends HttpServlet {

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

        String uri = request.getRequestURI();
        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }
        System.out.println("kookoo request url : " + uri);/*here I am just kookoo final xml prepared*/
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Response r = new Response();
            /*it is kookoo libriray*/
            String kookoo_event = request.getParameter("event");

            if ((null != kookoo_event)
                    && kookoo_event.equalsIgnoreCase("newcall")) {
                r.addPlayText("welcome to koo koo");
                CollectDtmf cd = new CollectDtmf();
                /*collect dtmf class*/
                cd.addPlayText("please enter the 10 digit mobile to dial");
                cd.setMaxDigits(10);
                cd.setTermChar("#");
                cd.setTimeOut(5000);
                /*5000 milli seconds*/
                r.addCollectDtmf(cd);
            } else if ((null != kookoo_event)
                    && kookoo_event.equalsIgnoreCase("gotdtmf")) {
                /*the call status will be sent by kookoo after call is completed, 
               based on call status you can do next again here I am retrying when call
                not answered by given number*/
                String dtmf = request.getParameter("data");
                /*entered number will come in data parameter*/
                if ((null != dtmf)
                        && dtmf.length() == 10) {
                    r.addPlayText("please wait while we connecting to consern person");
                    Dial dialnumber = new Dial(); //kookoo dial tag class
                    dialnumber.setNumber(dtmf);
                    dialnumber.setLimitTime(3600);
                    /*you can set call limit, here I am setting to max one hour (3600 seconds) */
                    r.addDial(dialnumber);
                } else {
                    /*if no dtmf enter or invalid dtmf we will be asing again to enter*/
                    CollectDtmf cd = new CollectDtmf();
                    /*collect dtmf class*/
                    cd.addPlayText("please re try");
                    cd.setMaxDigits(10);
                    cd.setTermChar("#");
                    cd.setTimeOut(5000);
                    /*5000 milli seconds*/
                    r.addCollectDtmf(cd);
                }
            } else if ((null != kookoo_event)
                    && kookoo_event.equalsIgnoreCase("dial")) {
                /*dialled number call status will be coming here*/
                String status = request.getParameter("status");
                if (status.equalsIgnoreCase("answered")) {
                    r.addPlayText("the call is answered ");
                } else {
                    r.addPlayText("call was not answered");
                }
                r.addHangup();
            } else if ((null != kookoo_event)
                    && kookoo_event.equalsIgnoreCase("hangup")) {
                /*whenever call hang up from primary caller, request comes to here, we can handle reporting here*/

            } else {
                r.addHangup();
            }
            System.out.println(r.getXML());/*here I am just printing kookoo final xml prepared*/
            out.println(r.getXML());

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "kookoo incoming call application";
    }// </editor-fold>

}
