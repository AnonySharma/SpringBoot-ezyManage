package com.ankit.ezymanage.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails)
            return ((UserDetails) userDetails).getUsername();

        return null;
    }
}
