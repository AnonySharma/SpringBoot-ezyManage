package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.dao.UserDAO;
import com.ankit.ezymanage.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void saveUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUser(String username) {
        return userDAO.getUserDataByUsername(username);
    }
}
