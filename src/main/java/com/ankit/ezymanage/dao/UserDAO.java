package com.ankit.ezymanage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ankit.ezymanage.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
	private JdbcTemplate jdbcTemplate;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserDAO(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jdbcTemplate = jdbcTemplate;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		final String sql = "INSERT INTO user(id, username, password, role, isadmin) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isAdmin());

	}

	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet row, int i) throws SQLException {
			return new User(row.getString("id"), row.getString("username"), row.getString("password"),
					row.getString("role"), Boolean.valueOf(row.getString("isadmin")));
		}
	};

	public void updateUser(User user) {
		// final String sql = "UPDATE USER SET firstname=?, middlename=?, lastname=?,
		// phone=?, gender=?, dob=?, email=?, address=? aadhaar=?";
		// jdbcTemplate.update(sql, user.getFirstName(), user.getMiddleName(),
		// user.getLastName(), user.getPhoneNumber(),
		// user.getGender(), user.getDateOfBirth(), user.getEmail(), user.getAddress(),
		// user.getAadhaarNumber());
	}

	public User getUserDataByUsername(String username) {
		final String sql = "SELECT * FROM user WHERE username=?";
		return jdbcTemplate.queryForObject(sql, userRowMapper, username);
	}

	public void deleteUserByUsername(String username) {
		final String sql = "DELETE FROM user WHERE username=?";
		jdbcTemplate.update(sql, username);
	}

	public User getUserDataById(Long id) {
		final String sql = "SELECT * FROM user WHERE id=?";
		return jdbcTemplate.queryForObject(sql, userRowMapper, id);
	}

	public List<User> getAllUsers() {
		final String sql = "SELECT * FROM user";
		return jdbcTemplate.query(sql, userRowMapper);
	}
}
