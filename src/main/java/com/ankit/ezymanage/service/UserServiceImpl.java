package com.ankit.ezymanage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.ankit.ezymanage.dao.UserDAO;
import com.ankit.ezymanage.model.OwnerRequest;
import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.model.User;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;
    private final ProfileService profileService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, ProfileService profileService, EmailService emailService) {
        this.userDAO = userDAO;
        this.profileService = profileService;
        this.emailService = emailService;
    }

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // System.out.println(userDetails);
        if (userDetails instanceof UserDetails)
            return ((UserDetails) userDetails).getUsername();

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserDataByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                makeAuthorities(user.getRole()));
    }

    private Collection<SimpleGrantedAuthority> makeAuthorities(String userRoles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String[] roles = userRoles.split(" ");
        for (String role : roles)
            authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public void saveUser(User user) {
        userDAO.createUser(user);
        Profile profile = new Profile(user.getUsername());
        profile.setEmail(user.getEmail());
        profileService.saveProfile(profile);

        String token = UUID.randomUUID().toString();
        userDAO.createVerificationToken(user.getUsername(), user.getEmail(), token);
        emailService.sendVerificationEmail(user.getUsername(), user.getEmail(), token);
    }

    @Override
    public void updateUserVerificationStatus(String username, boolean isVerified) {
        userDAO.updateUserVerificationStatus(username, isVerified);
    }

    @Override
    public String getUsernameByVerificationToken(String token) {
        return userDAO.getUsernameByVerificationToken(token);
    }

    @Override
    public void deleteUser(String username) {
        profileService.deleteProfileByUsername(username);
        userDAO.deleteUserByUsername(username);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserDataById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean addRoleToUser(String username, String roleName) {
        User user = userDAO.getUserDataByUsername(username);
        String[] roles = user.getRole().split(" ");
        for (String role : roles)
            if (role.equals(roleName))
                return false;

        user.setRole(user.getRole() + " " + roleName);
        if (roleName.equals("ROLE_ADMIN"))
            user.setAdmin(true);

        userDAO.updateUser(user);
        updateAuthoritites(user, user.getRole());
        return true;
    }

    private void updateAuthoritites(User user, String userRoles) {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Authentication newAuth = new
        // UsernamePasswordAuthenticationToken(auth.getPrincipal(),
        // auth.getCredentials(),
        // makeAuthorities(userRoles));
        // SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public boolean removeRoleFromUser(String username, String role) {
        User user = userDAO.getUserDataByUsername(username);
        String[] roles = user.getRole().split(" ");
        System.out.println(roles.toString());
        for (String roleName : roles)
            if (roleName.equals(role)) {
                String updatedRoles = user.getRole().replace(" " + roleName, "");
                System.out.println(updatedRoles);
                if (roleName.equals("ROLE_ADMIN"))
                    user.setAdmin(false);
                user.setRole(updatedRoles);
                userDAO.updateUser(user);
                updateAuthoritites(user, user.getRole());
                return true;
            }
        return false;
    }

    @Override
    public List<String> getUserRoles(String username) {
        String roles = getUserByUsername(username).getRole();
        String[] rolesArray = roles.split(" ");
        List<String> rolesList = new ArrayList<>();
        for (String role : rolesArray)
            rolesList.add(role);
        return rolesList;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserDataByUsername(username);
    }

    @Override
    public void createOwnerRequest(OwnerRequest ownerRequest) {
        userDAO.createOwnerRequest(ownerRequest);
    }

    @Override
    public void deleteOwnerRequest(int userId) {
        userDAO.deleteOwnerRequest(userId);
    }

    @Override
    public List<OwnerRequest> getAllActiveOwnerRequests() {
        return userDAO.getAllActiveOwnerRequests();
    }

    @Override
    public void updateStatusOwnerRequest(int user_id, String status) {
        userDAO.updateStatusOwnerRequest(user_id, status);
    }

    @Override
    public boolean checkIfAlreadyRequestedToBeOwner(int userId) {
        return getOwnerRequestByUserId(userId) != null;
    }

    @Override
    public OwnerRequest getOwnerRequestByUserId(int userId) {
        List<OwnerRequest> ownerRequests = userDAO.getOwnerRequestByUserId(userId);
        if (ownerRequests.size() == 0)
            return null;
        return ownerRequests.get(0);
    }

}
