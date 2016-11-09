/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kookoo.outbound;

import java.net.URI;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

public class OutboundTest {

    public static void main(String a[]) {
        try {
            String server_ip = "http://XXXX:8080"; /*change to your web server*/
            String phone_no1 = "09985XXXXX";/*change number to your numbers*/
            String phone_no2 = "09985XXXXX";/*change number to your numbers*/
            String api_key = "KKXXXXX" ;/*kookoo api key*/
            String kookoo_number = "91xxxxx" ;/*kookoo assigned number*/        
            
      
            Date d = new Date();
            String trackId = "" + d.getTime();
            String url = "http://kookoo.in/outbound/outbound.php";
            URIBuilder uribuilder = new URIBuilder(url);
            uribuilder.addParameter("api_key", api_key);
            uribuilder.addParameter("phone_no", phone_no1);
            uribuilder.addParameter("caller_id", kookoo_number);
            /*assigned kookoo number*/
            uribuilder.addParameter("url", server_ip + "/kookoocall/outboundcall?number2=" + phone_no2 + "&trackId=" + trackId);
            uribuilder.addParameter("callback_url", server_ip + "/kookoocall/outbound_callstatus?number2=" + phone_no2 + "&trackId=" + trackId);

            URI uri = uribuilder.build();
            System.out.println("Final Outboud API url " + uri);
            HttpGet request = new HttpGet(uri);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);

            String responseString = new BasicResponseHandler().handleResponse(response);
            System.out.println(responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
