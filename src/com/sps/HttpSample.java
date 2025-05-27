package com.sps;

import org.openziti.Ziti;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Sample program to demonstrate `seamless` SDK usage.
 */
public class HttpSample {

    public static void main(String[] args) {
        try {
            Ziti.init(args[0], "".toCharArray(), true);

            while(true) {

                URL url1 = new URL("http://darkside.api.com/api/people/1");
                URL url2 = new URL("http://darkside.api.com/api/people/2");

                System.out.println("Información de Luke: \n");
                runHttp(url1);
                System.out.println("Información de C3PO: \n");
                runHttp(url2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void runHttp(URL url) {

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer eyJ4NXQjUzI1NiI6ImNxdGVoMEF2YkVzc2hfQmliSkVYQUV5TjlZcHcwdEFfTDNZeHlySVg4dVEiLCJ4NXQiOiJ1VzJYZkZyY21OTFlld0dfbk5NNEMzYTJsN0EiLCJraWQiOiJTSUdOSU5HX0tFWSIsImFsZyI6IlJTMjU2In0.eyJjbGllbnRfb2NpZCI6Im9jaWQxLmRvbWFpbmFwcC5vYzEuaWFkLmFtYWFhYWFhYXF0cDViYWF4aWNwM3B4MmE3anJhYzQ0a28yYWdtbzJ0Y2RnbXFrNnd3cGtvcng1cmdwcSIsInN1YiI6IjJiMjk2YmQ5ODg5NTQzNzI5MDM4MDRiZDM4NjFjYzUwIiwic2lkbGUiOjQ4MCwidXNlci50ZW5hbnQubmFtZSI6ImlkY3MtMWQ1MDM4Y2NjYzFkNGM3MTg2ZDRkMGNlZGIyYWMzZDciLCJpc3MiOiJodHRwczovL2lkZW50aXR5Lm9yYWNsZWNsb3VkLmNvbS8iLCJkb21haW5faG9tZSI6InVzLWFzaGJ1cm4tMSIsImNhX29jaWQiOiJvY2lkMS50ZW5hbmN5Lm9jMS4uYWFhYWFhYWE3NWZ0Y3g2ZDRqenU0cXF4c2ZlZWhlNzR1Zmw3eTd0bHZ0bHU2MzNvaXJyZWdreXRmZmZxIiwiY2xpZW50X2lkIjoiMmIyOTZiZDk4ODk1NDM3MjkwMzgwNGJkMzg2MWNjNTAiLCJkb21haW5faWQiOiJvY2lkMS5kb21haW4ub2MxLi5hYWFhYWFhYXV2M2R2ZmVrY2k1YTVlZmdubTdwejQ1enc1Zm9jYnNxcDR5c2FlNzUzenlvcXZrdTc1Y2EiLCJzdWJfdHlwZSI6ImNsaWVudCIsInNjb3BlIjoibm1wU2NvcGUiLCJjbGllbnRfdGVuYW50bmFtZSI6ImlkY3MtMWQ1MDM4Y2NjYzFkNGM3MTg2ZDRkMGNlZGIyYWMzZDciLCJyZWdpb25fbmFtZSI6InVzLWFzaGJ1cm4taWRjcy0xIiwiZXhwIjoxNzM4ODY0Njg0LCJpYXQiOjE3Mzg4NjEwODQsImNsaWVudF9ndWlkIjoiY2Q4MTZmODhhZjllNDhiNWI0NGMwOGZlYTdlY2RlMWUiLCJjbGllbnRfbmFtZSI6Ik5NUExhYlRlc3QiLCJ0ZW5hbnQiOiJpZGNzLTFkNTAzOGNjY2MxZDRjNzE4NmQ0ZDBjZWRiMmFjM2Q3IiwianRpIjoiZDNkM2M3YjYxYmJmNDRjZGJjZmY4MzkxZWVkYzg0MDUiLCJndHAiOiJjYyIsIm9wYyI6ZmFsc2UsInN1Yl9tYXBwaW5nYXR0ciI6InVzZXJOYW1lIiwicHJpbVRlbmFudCI6dHJ1ZSwidG9rX3R5cGUiOiJBVCIsImNhX2d1aWQiOiJjYWNjdC0xZDY4OTBkNzFlNzI0NjRjYmMzM2NiMzdjMGExMTQ1MyIsImF1ZCI6Im5tcEF1ZGllbmNlIiwiY2FfbmFtZSI6InJjYXJyYXNjb2diMjkyIiwiZG9tYWluIjoiT3JhY2xlSWRlbnRpdHlDbG91ZFNlcnZpY2UiLCJ0ZW5hbnRfaXNzIjoiaHR0cHM6Ly9pZGNzLTFkNTAzOGNjY2MxZDRjNzE4NmQ0ZDBjZWRiMmFjM2Q3LmlkZW50aXR5Lm9yYWNsZWNsb3VkLmNvbTo0NDMiLCJyZXNvdXJjZV9hcHBfaWQiOiJjZDgxNmY4OGFmOWU0OGI1YjQ0YzA4ZmVhN2VjZGUxZSJ9.eaNIntMexjqCsQEIObPRuSFI4WuXaXTudbphbmljhugHgOGzgX2gWG9Fo_ZUDLMKNL1cB-erixQrxu7KKd_ToJS47GAf2wMQdB7X6x1_SMzAgW5u9w04iFTYdTX_p1dWgx4tSP2rcYWV8BMZsmmSKCIyL6jr4LXuvBRN2hDy4nuX22kCeyo-c1FKoM7fs5NWCpfrmdXrRdeS5NcGawD9HwBxpGaBUaL1h2L4g7q0uHsxl9zkNj2kGE460Gf5QkHO95FF5WL0pX5zMYMwNqgzOO1DY8yqLE4oga58LSi5HaJ2uEleNqz6BpO0csP_QwgCYZlhpQrb3EdBVC2Ab0Avnw");
            conn.setRequestProperty("id", "dark");
            //conn.setRequestProperty("client_id", "5313e227248f4be5a422e74cf834f39f");
            //conn.setRequestProperty("client_secret", "41eA452dA0c344Dd99Ba73f8A03Bf193");
            conn.setRequestProperty("Connection", "close");
            //conn.setRequestProperty("User-Agent", "curl");

            conn.setDoInput(true);
            int rc = conn.getResponseCode();
            byte[] buf = new byte[1024];
            ByteArrayOutputStream resp = new ByteArrayOutputStream();

            if (rc > 399) {
                int len = conn.getErrorStream().read(buf);
                System.err.println(String.format("%d %s\n%s", rc, conn.getResponseMessage(), new String(buf, 0, len)));
            } else {
                do {
                    int len = conn.getInputStream().read(buf);
                    if (len < 0) break;
                    resp.write(buf, 0, len);
                } while (true);

                System.out.println(new String(resp.toByteArray()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("\n=============== Done! ==================\n");
    }
}
