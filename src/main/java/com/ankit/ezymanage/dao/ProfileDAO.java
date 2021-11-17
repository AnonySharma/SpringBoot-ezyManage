package com.ankit.ezymanage.dao;

import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ProfileDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createProfile(Profile profile) {
		final String sql = "INSERT INTO profile(username) VALUES(?)";
		jdbcTemplate.update(sql, profile.getUsername());
	}

	public void updateProfile(Profile profile) {
		final String sql = "UPDATE profile SET firstname=?, middlename=?, lastname=?, phone=?, gender=?, dob=?, email=?, address=?, aadhaar=? WHERE username=?";
		jdbcTemplate.update(sql, profile.getFirstName(), profile.getMiddleName(), profile.getLastName(),
				profile.getPhoneNumber(), profile.getGender(), profile.getDateOfBirth(), profile.getEmail(),
				profile.getAddress(), profile.getAadhaarNumber(), profile.getUsername());
	}

	public Profile getProfileDataByUsername(String username) {
		final String sql = "SELECT * FROM profile WHERE username=?";
		return jdbcTemplate.queryForObject(sql, RowMappers.profileRowMapper, username);
	}

	public void deleteProfileByUsername(String username) {
		final String sql = "DELETE FROM profile WHERE username=?";
		jdbcTemplate.update(sql, username);
	}

}
