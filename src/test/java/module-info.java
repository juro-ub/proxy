open module test.de.jro.moduls.proxy.request {
    requires de.jro.moduls.proxy;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    
    uses de.jro.moduls.proxy.request.ProxyRequestInterface;
    
    exports test.de.jro.moduls.proxy.request;
}
