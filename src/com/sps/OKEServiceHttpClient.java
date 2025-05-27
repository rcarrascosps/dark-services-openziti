
package com.sps;

import org.openziti.Ziti;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class OKEServiceHttpClient {

    public static void main(String[] args) throws Exception {
        // Create a trust manager
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
            }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        // Connect to the Overlay Network using OpenZiti SDK
        Ziti.init(args[0], "".toCharArray(), true);

        OKEServiceHttpClient sessionAPI = new OKEServiceHttpClient();

        String sessionID= sessionAPI.getSession();
        sessionAPI.getProfile(sessionID);

    }

    public static String getSession() throws Exception{

        URL url = new URL("http://session-api.sps.com/login");

        URLConnection con = url.openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Connection", "close");

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("Content-Type", "application/json");

        httpConnection.setDoOutput(true);
        OutputStream outStream = httpConnection.getOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
        outStreamWriter.write("{\"username\":\"Hugo\",\"password\":\"Hugo123\"}");
        outStreamWriter.flush();
        outStreamWriter.close();
        outStream.close();

        System.out.println("\n\n├──Calling the getSession operation to obtain a sessionID:");
        System.out.println("│   ├── HTTP response code: " + httpConnection.getResponseCode());
        System.out.println("│   └── HTTP response status: " +httpConnection.getResponseMessage());

        String result;
        BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result2 = bis.read();
        while(result2 != -1) {
            buf.write((byte) result2);
            result2 = bis.read();
        }
        result = buf.toString();
        System.out.println("│   └── Service response message: " + result);

        String sessionID = "";
        try {
            JSONObject jsonObject = new JSONObject(result);
            sessionID = jsonObject.getString("sessionID");

        }catch (JSONException err){
            err.printStackTrace();
        }

        return sessionID;

    }

    public void getProfile(String sessionID) throws  Exception{

        URL url = new URL("http://session-api.sps.com/profile");
        URLConnection con = url.openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Connection", "close");

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("SessionID", sessionID);

        System.out.println("\n\n├──Calling the getProfile operation:");
        System.out.println("│   ├── HTTP response code: " + httpConnection.getResponseCode());
        System.out.println("│   └── HTTP response status: " +httpConnection.getResponseMessage());

        String result;
        BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result2 = bis.read();
        while(result2 != -1) {
            buf.write((byte) result2);
            result2 = bis.read();
        }

        result = buf.toString();
        System.out.println("│   └── Service response message: " + result);

    }

}