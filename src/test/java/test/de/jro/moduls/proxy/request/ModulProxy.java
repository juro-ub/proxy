package test.de.jro.moduls.proxy.request;

import de.jro.moduls.proxy.request.ProxyRequestInterface;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import utils.Properties;

public class ModulProxy {

    @Test
    public void makeRequest() {
        ProxyRequestInterface request = new de.jro.moduls.proxy.request.Socks5Request();

        try {
            Properties props = new Properties();
            final String ip = props.getSocks5Ip();
            final String url = props.getSocks5TestUrl();
            final Integer port = Integer.valueOf(props.getSocks5Port());
            final String httpHost = props.getSocks5Host();
            ArrayList<org.apache.http.Header> header = new ArrayList<org.apache.http.Header>();
            final Integer timeoutSec = 10;

            request.proxyRequest(ip, port, httpHost, url,
                    header, timeoutSec);
        } catch (Exception exception) {
            fail(exception.getLocalizedMessage());
        }
    }
}
