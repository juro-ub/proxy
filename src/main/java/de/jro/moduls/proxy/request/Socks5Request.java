package de.jro.moduls.proxy.request;

import java.util.*;
import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.client.config.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.*;
import org.apache.http.config.*;
import org.apache.http.conn.socket.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.protocol.*;
import org.apache.http.ssl.*;

public class Socks5Request implements ProxyRequestInterface {

    private static class SimpleProxyConnectionSocketFactory extends org.apache.http.conn.ssl.SSLConnectionSocketFactory {

        private SimpleProxyConnectionSocketFactory(final SSLContext sslContext, HostnameVerifier allowAll) {
            super(sslContext, allowAll);
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {

            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");

            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);

            return new Socket(proxy);
        }
    }

    @Override
    public Boolean proxyRequest(String socksfiveip, int socksfiveport, String httpshost, String url,
            ArrayList<org.apache.http.Header> header, Integer timeout_seconds) throws Exception {

        HostnameVerifier allow = new HostnameVerifier() {
            @Override
            public boolean verify(String hostName, SSLSession session) {
                return hostName.equals(url);
            }
        };

        System.out.println(org.apache.http.conn.ssl.SSLConnectionSocketFactory.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath());

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SimpleProxyConnectionSocketFactory(SSLContexts.createSystemDefault(), allow)).build();

        System.out.println(org.apache.http.impl.conn.PoolingHttpClientConnectionManager.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath());

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);

        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();

        try {

            InetSocketAddress socksaddr = new InetSocketAddress(socksfiveip, socksfiveport);

            HttpClientContext context = HttpClientContext.create();

            context.setAttribute("socks.address", socksaddr);

            HttpHost target = new HttpHost(httpshost, 443, "https");

            HttpGet request = new HttpGet(url);

            // timeout
            if (timeout_seconds != null && timeout_seconds > 0) {

                final RequestConfig rc = RequestConfig.custom().setConnectTimeout(timeout_seconds * 1000)
                        .setSocketTimeout(timeout_seconds * 1000).build();

                request.setConfig(rc);

            }

            if (header != null) {

                for (org.apache.http.Header i : header) {

                    request.addHeader(i);

                }

            }

            System.out.println("Executing request " + request + " to " + target + " via SOCKS proxy " + socksaddr);

            CloseableHttpResponse response = httpclient.execute(target, request, context);

            String text;

            try {

                System.out.println("----------------------------------------");

                System.out.println(response.getStatusLine());

                text = org.apache.commons.io.IOUtils.toString(response.getEntity().getContent(),
                        java.nio.charset.StandardCharsets.UTF_8.name());

                System.out.println("Response: " + text);

            } finally {

                response.close();

            }

            return false;

        } finally {

            httpclient.close();

        }
    }
}
