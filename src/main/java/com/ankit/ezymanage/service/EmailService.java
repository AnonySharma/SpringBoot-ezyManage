package com.ankit.ezymanage.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendHTMLEmail(String to, String subject, String body);

    void sendHTMLEmailByShop(int shopId, String to, String subject, String body);
}
