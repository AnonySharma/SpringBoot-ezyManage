package com.ankit.ezymanage.service;

import com.ankit.ezymanage.dao.ProfileDAO;
import com.ankit.ezymanage.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileDAO profileDAO;

    @Autowired
    public ProfileServiceImpl(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    @Override
    public Profile getProfile(String username) {
        return profileDAO.getProfileDataByUsername(username);
    }

    @Override
    public void saveProfile(Profile profile) {
        profileDAO.createProfile(profile);
    }

    @Override
    public void updateProfile(Profile profile) {
        profileDAO.updateProfile(profile);
    }
}
