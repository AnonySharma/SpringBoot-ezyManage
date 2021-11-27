package com.ankit.ezymanage.utils;

import java.net.UnknownHostException;

public class GetHost {
    public static String getHost() {
        String host = "";
        String cannonicalHost = "";
        try {
            cannonicalHost = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            if (cannonicalHost.contains("heroku")) {
                host = "https://ezy-manage.herokuapp.com";
            } else {
                host = "http://localhost:8080";
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(e);
            host = "https://ezy-manage.herokuapp.com";
        }
        return host;
    }
}
