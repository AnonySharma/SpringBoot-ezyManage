package com.ankit.ezymanage.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendHTMLEmail(String to, String subject, String body);

    void sendHTMLEmailByShop(String shopName, String to, String subject, String body);

    void sendVerificationEmail(String username, String email, String token);
}
