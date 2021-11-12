package com.ankit.ezymanage.service;

import com.ankit.ezymanage.model.Profile;

import org.springframework.security.access.annotation.Secured;

public interface ProfileService {
	void saveProfile(Profile profile);

	void deleteProfileByUsername(String username);

	// @Secured("ROLE_ADMIN")
	Profile getProfile(String username);

	void updateProfile(Profile profile);
}
