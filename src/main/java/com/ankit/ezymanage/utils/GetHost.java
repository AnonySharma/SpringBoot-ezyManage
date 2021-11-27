package com.ankit.ezymanage.utils;

import java.net.UnknownHostException;

public class GetHost {
    public static String getHost() {
        String host = "";
        try {
            host = java.net.InetAddress.getLocalHost().getHostName();
            System.out.println(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return host;
    }
}
