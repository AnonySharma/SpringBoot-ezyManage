package com.ankit.ezymanage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ankit.ezymanage.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ProfileDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createProfile(Profile profile) {
		final String sql = "INSERT INTO profile(username, firstname, middlename, lastname, phone, gender, dob, email, address, aadhaar) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, profile.getUsername(), null, null, null, null, null, null, null, null, null);
	}

	public void updateProfile(Profile profile) {
		final String sql = "UPDATE profile SET firstname=?, middlename=?, lastname=?, phone=?, gender=?, dob=?, email=?, address=?, aadhaar=? WHERE username=?";
		jdbcTemplate.update(sql, profile.getFirstName(), profile.getMiddleName(), profile.getLastName(),
				profile.getPhoneNumber(), profile.getGender(), profile.getDateOfBirth(), profile.getEmail(),
				profile.getAddress(), profile.getAadhaarNumber(), profile.getUsername());
	}

	public Profile getProfileDataByUsername(String username) {
		final String sql = "SELECT * FROM profile WHERE username=?";
		return jdbcTemplate.queryForObject(sql, profileRowMapper, username);
	}

	public void deleteProfileByUsername(String username) {
		final String sql = "DELETE FROM profile WHERE username=?";
		jdbcTemplate.update(sql, username);
	}

	private boolean isValid(String s) {
		return ((s != null) && (s != ""));
	}

	private RowMapper<Profile> profileRowMapper = new RowMapper<Profile>() {
		@Override
		public Profile mapRow(ResultSet row, int i) throws SQLException {
			Profile profile = new Profile();

			if (isValid(row.getString("username"))) {
				profile.setUsername(row.getString("username"));
			}

			if (isValid(row.getString("firstname"))) {
				profile.setFirstName(row.getString("firstname"));
			}

			if (isValid(row.getString("middlename"))) {
				profile.setMiddleName(row.getString("middlename"));
			}

			if (isValid(row.getString("lastname"))) {
				profile.setLastName(row.getString("middlename"));
			}

			if (isValid(row.getString("phone"))) {
				profile.setPhoneNumber(row.getString("phone") != null && row.getString("phone") != ""
						? Long.parseLong(row.getString("phone"))
						: null);
			}

			if (isValid(row.getString("gender"))) {
				profile.setGender(row.getString("gender").charAt(0));
			}

			if (isValid(row.getString("dob"))) {
				System.out.println(row.getString("dob"));
				try {
					profile.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(row.getString("dob")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			if (isValid(row.getString("email"))) {
				profile.setEmail(row.getString("email"));
			}

			if (isValid(row.getString("address"))) {
				profile.setAddress(row.getString("address"));
			}

			if (isValid(row.getString("aadhaar"))) {
				profile.setAadhaarNumber(Long.parseLong(row.getString("aadhaar")));
			}

			return profile;
		}
	};
}
