module de.jro.moduls.proxy {
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    exports de.jro.moduls.proxy.request;
    provides de.jro.moduls.proxy.request.ProxyRequestInterface with de.jro.moduls.proxy.request.Socks5Request;
}
