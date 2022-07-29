package utils;

import java.io.*;

public class Properties extends java.util.Properties {

    public Properties() throws Exception {
        load();
    }

    public void load() throws Exception {
        InputStream input = Properties.class.getClassLoader().getResourceAsStream("junit.properties");
        super.load(input);
    }

    public String getSocks5Ip() {
        return this.getProperty("ip");
    }

    public String getSocks5Port() {
        return this.getProperty("port");
    }

    public String getSocks5Host() {
        return this.getProperty("host");
    }

    public String getSocks5TestUrl() {
        return this.getProperty("testurl");
    }
}
