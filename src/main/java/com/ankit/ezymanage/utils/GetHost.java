package com.ankit.ezymanage.utils;

import java.net.UnknownHostException;

public class GetHost {
    public static String getHost() {
        String host = "";
        Boolean isLocal = false;
        try {
            host = java.net.InetAddress.getLocalHost().getHostName();
            isLocal = java.net.InetAddress.getLocalHost().isAnyLocalAddress();
            System.out.println(java.net.InetAddress.getLocalHost().getHostAddress());
            System.out.println(java.net.InetAddress.getLocalHost().getHostName());
            System.out.println(java.net.InetAddress.getLocalHost().isAnyLocalAddress());
            System.out.println(java.net.InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return host;
    }
}
