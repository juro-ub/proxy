package de.jro.moduls.proxy.request;

import java.util.ArrayList;

public interface ProxyRequestInterface {

    public Boolean proxyRequest(String ip, int port, String httpshost, String url,
            ArrayList<org.apache.http.Header> header, Integer timeoutSeconds) throws Exception;
}
