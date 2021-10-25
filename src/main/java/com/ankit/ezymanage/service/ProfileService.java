package com.ankit.ezymanage.service;

import com.ankit.ezymanage.model.Profile;

public interface ProfileService {
	void saveProfile(Profile profile);

	Profile getProfile(String username);

	void updateProfile(Profile profile);
}
