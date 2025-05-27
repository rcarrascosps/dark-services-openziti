package com.sps;


import org.openziti.Ziti;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class HttpsClient2 {
    public static void main(String[] args) throws Exception {
        // Create a trust manager
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
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


        URL url = new URL("https://mdsv5rqni2atlcnytbltwcyezm.apigateway.us-ashburn-1.oci.customer-oci.com/api/people/2");

        while (true) {
            URLConnection con = url.openConnection();
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("x-starwars-fan", "98JUKLO-101");
            con.setRequestProperty("Authorization", "Bearer eyJ4NXQjUzI1NiI6ImNxdGVoMEF2YkVzc2hfQmliSkVYQUV5TjlZcHcwdEFfTDNZeHlySVg4dVEiLCJ4NXQiOiJ1VzJYZkZyY21OTFlld0dfbk5NNEMzYTJsN0EiLCJraWQiOiJTSUdOSU5HX0tFWSIsImFsZyI6IlJTMjU2In0.eyJjbGllbnRfb2NpZCI6Im9jaWQxLmRvbWFpbmFwcC5vYzEuaWFkLmFtYWFhYWFhYXF0cDViYWF4aWNwM3B4MmE3anJhYzQ0a28yYWdtbzJ0Y2RnbXFrNnd3cGtvcng1cmdwcSIsInN1YiI6IjJiMjk2YmQ5ODg5NTQzNzI5MDM4MDRiZDM4NjFjYzUwIiwic2lkbGUiOjQ4MCwidXNlci50ZW5hbnQubmFtZSI6ImlkY3MtMWQ1MDM4Y2NjYzFkNGM3MTg2ZDRkMGNlZGIyYWMzZDciLCJpc3MiOiJodHRwczpcL1wvaWRlbnRpdHkub3JhY2xlY2xvdWQuY29tXC8iLCJkb21haW5faG9tZSI6InVzLWFzaGJ1cm4tMSIsImNhX29jaWQiOiJvY2lkMS50ZW5hbmN5Lm9jMS4uYWFhYWFhYWE3NWZ0Y3g2ZDRqenU0cXF4c2ZlZWhlNzR1Zmw3eTd0bHZ0bHU2MzNvaXJyZWdreXRmZmZxIiwiY2xpZW50X2lkIjoiMmIyOTZiZDk4ODk1NDM3MjkwMzgwNGJkMzg2MWNjNTAiLCJkb21haW5faWQiOiJvY2lkMS5kb21haW4ub2MxLi5hYWFhYWFhYXV2M2R2ZmVrY2k1YTVlZmdubTdwejQ1enc1Zm9jYnNxcDR5c2FlNzUzenlvcXZrdTc1Y2EiLCJzdWJfdHlwZSI6ImNsaWVudCIsInNjb3BlIjoibm1wU2NvcGUiLCJjbGllbnRfdGVuYW50bmFtZSI6ImlkY3MtMWQ1MDM4Y2NjYzFkNGM3MTg2ZDRkMGNlZGIyYWMzZDciLCJyZWdpb25fbmFtZSI6InVzLWFzaGJ1cm4taWRjcy0xIiwiZXhwIjoxNzI1OTMwNDM3LCJpYXQiOjE3MjU5MjY4MzcsImNsaWVudF9ndWlkIjoiY2Q4MTZmODhhZjllNDhiNWI0NGMwOGZlYTdlY2RlMWUiLCJjbGllbnRfbmFtZSI6Ik5NUExhYlRlc3QiLCJ0ZW5hbnQiOiJpZGNzLTFkNTAzOGNjY2MxZDRjNzE4NmQ0ZDBjZWRiMmFjM2Q3IiwianRpIjoiNjdkNzg5NDQzNDE5NDhkMThjMWViYTQ0YmI0YzYxY2YiLCJndHAiOiJjYyIsIm9wYyI6ZmFsc2UsInN1Yl9tYXBwaW5nYXR0ciI6InVzZXJOYW1lIiwicHJpbVRlbmFudCI6dHJ1ZSwidG9rX3R5cGUiOiJBVCIsImNhX2d1aWQiOiJjYWNjdC0xZDY4OTBkNzFlNzI0NjRjYmMzM2NiMzdjMGExMTQ1MyIsImF1ZCI6Im5tcEF1ZGllbmNlIiwiY2FfbmFtZSI6InJjYXJyYXNjb2diMjkyIiwiZG9tYWluIjoiT3JhY2xlSWRlbnRpdHlDbG91ZFNlcnZpY2UiLCJ0ZW5hbnRfaXNzIjoiaHR0cHM6XC9cL2lkY3MtMWQ1MDM4Y2NjYzFkNGM3MTg2ZDRkMGNlZGIyYWMzZDcuaWRlbnRpdHkub3JhY2xlY2xvdWQuY29tOjQ0MyIsInJlc291cmNlX2FwcF9pZCI6ImNkODE2Zjg4YWY5ZTQ4YjViNDRjMDhmZWE3ZWNkZTFlIn0.QZdClyE4qraDSkTpb2YlB8rF_b86jGS6vMsqKfQWTD41XGmBmNK9B0daWaEF70yskNLe6_7yLPBzrDi4OWk2zg-r4jJYGALSLPfAp5MHrKjEmwL8t9hBjQnNg309LbopXsPH9kprn23LmBuEgE2VPDD0JC9ZJ3Oif1edn24yYzgKzKCzYbXMmeNeabg0gCWjr7CgzvCLsDNKcfA6rq5sA3jN-9raxyfcW-WG4hXDAyS5Dxlp5AR38AmTdzHHDv7ejFR5erjGNoI1de2wLiubHDg3oCSlyD8A-SGcFQjD2YPAzNJoi08P6g3TvYidmH8TapKL9vj_r1NRCfqmP-qOcg");
            con.setRequestProperty("Connection", "close");

            System.out.println("\nGetting the response from our dark service:\n");

            Reader reader = new InputStreamReader(con.getInputStream());
            while (true) {
                int ch = reader.read();
                if (ch == -1) {
                    break;
                }
                System.out.print((char) ch);
            }
            System.out.println("\n");
        }
    }

}