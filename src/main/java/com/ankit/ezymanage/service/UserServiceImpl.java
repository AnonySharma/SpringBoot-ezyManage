package com.ankit.ezymanage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ankit.ezymanage.dao.UserDAO;
import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;
    private final ProfileService profileService;

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails)
            return ((UserDetails) userDetails).getUsername();

        return null;
    }

    public boolean isLoggedIn() {
        return findLoggedInUsername() != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserDataByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String[] roles = user.getRole().split(" ");
        for (String role : roles)
            authorities.add(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Autowired
    public UserServiceImpl(UserDAO userDAO, ProfileService profileService) {
        this.userDAO = userDAO;
        this.profileService = profileService;
    }

    @Override
    public void saveUser(User user) {
        userDAO.createUser(user);
        profileService.saveProfile(new Profile(user.getUsername()));
    }

    @Override
    public void deleteUser(String username) {
        profileService.deleteProfileByUsername(username);
        userDAO.deleteUserByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userDAO.getUserDataByUsername(username);
        user.setRole(user.getRole() + " " + roleName);
    }

    @Override
    public User getUser(String username) {
        return userDAO.getUserDataByUsername(username);
    }
}
