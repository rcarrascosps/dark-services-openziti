package com.sps;

import com.ning.http.client.*;
import com.ning.http.client.providers.grizzly.GrizzlyAsyncHttpProvider;
import org.openziti.Ziti;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.util.concurrent.Future;
public class Main {

    public static void main(String[] args) throws Exception{
	// write your code here
        Main m = new Main();
        try {
            Ziti.init(args[0], "".toCharArray(), true);

            URL url = new URL(args[1]);

            m.test();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void test() {



        try {


            String trustStorePath ="C:\\oracle\\WL14\\user_projects\\domains\\wl_server\\security\\DemoIdentity.jks";
            String trustStorePass ="DemoIdentityKeyStorePassPhrase";
            SSLContext context =
                    SSLContext.getInstance("SSL");
            TrustManagerFactory.getInstance("SunX509");
            KeyStore.getInstance("JKS");
            KeyStore trustStore =
                    KeyStore.getInstance("JKS");
            trustStore.load(new
                    FileInputStream(trustStorePath), trustStorePass.toCharArray());
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance("SunX509");
            tmf.init(trustStore);
            context.init(null, tmf.getTrustManagers(), null);
            AsyncHttpClientConfig config = new
                    AsyncHttpClientConfig.Builder().setSSLContext(context).build();
            AsyncHttpClient c = new AsyncHttpClient(new
                    GrizzlyAsyncHttpProvider(config), config);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            Future<Response> f = c.prepareGet("http://darkside.api.com/api/people/1 ").execute();
            Response r = f.get();
            System.out.println(" a ver: " + r.getResponseBody());
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }


}
