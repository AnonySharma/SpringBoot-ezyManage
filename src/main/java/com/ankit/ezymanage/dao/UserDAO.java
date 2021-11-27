package com.ankit.ezymanage.dao;

import java.util.List;

import com.ankit.ezymanage.model.OwnerRequest;
import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.utils.RowMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		final String sql = "INSERT INTO users(id, username, email, password, role, isadmin) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
					user.getRole(), user.isAdmin());
		} catch (Exception e) {
			System.out.println(e);
			throw new DuplicateKeyException("Username not available!");
		}

	}

	public void updateUser(User user) {
		final String sql = "UPDATE users SET username = ?, password = ?, role = ?, email = ?, isadmin = ? WHERE id = ?";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.getEmail(),
				user.isAdmin(), user.getId());
	}

	public User getUserDataByUsername(String username) {
		final String sql = "SELECT * FROM users WHERE username=?";
		try {
			return jdbcTemplate.queryForObject(sql, RowMappers.userRowMapper, username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}

	public void deleteUserByUsername(String username) {
		final String sql = "DELETE FROM users WHERE username=?";
		jdbcTemplate.update(sql, username);
	}

	public User getUserDataById(int id) {
		final String sql = "SELECT * FROM users WHERE id=?";
		return jdbcTemplate.queryForObject(sql, RowMappers.userRowMapper, id);
	}

	public List<User> getAllUsers() {
		final String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, RowMappers.userRowMapper);
	}

	public void createOwnerRequest(OwnerRequest ownerRequest) {
		final String sql = "INSERT INTO owner_requests(user_id, date, status) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, ownerRequest.getUserId(), ownerRequest.getDate(), ownerRequest.getStatus());
	}

	public void updateStatusOwnerRequest(int user_id, String status) {
		final String sql = "UPDATE owner_requests SET status = ? WHERE user_id = ?";
		jdbcTemplate.update(sql, status, user_id);
	}

	public List<OwnerRequest> getAllActiveOwnerRequests() {
		final String sql = "SELECT * FROM owner_requests WHERE status = ?";
		return jdbcTemplate.query(sql, RowMappers.ownerRequestRowMapper, "pending");
	}

	public void deleteOwnerRequest(int userId) {
		final String sql = "DELETE FROM owner_requests WHERE user_id = ?";
		jdbcTemplate.update(sql, userId);
	}

	public List<OwnerRequest> getOwnerRequestByUserId(int userId) {
		final String sql = "SELECT * FROM owner_requests WHERE user_id = ?";
		return jdbcTemplate.query(sql, RowMappers.ownerRequestRowMapper, userId);
	}

	public void createVerificationToken(String username, String email, String token) {
		final String sql = "INSERT INTO verification_emails(username, email, token) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql, username, email, token);
	}

	public void deleteVerificationToken(String username) {
		final String sql = "DELETE FROM verification_emails WHERE username = ?";
		jdbcTemplate.update(sql, username);
	}

	public boolean isVerificationTokenExists(String token) {
		final String sql = "SELECT COUNT(*) FROM verification_emails WHERE token = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, token) > 0;
	}

	public String getUsernameByVerificationToken(String token) {
		if (!isVerificationTokenExists(token))
			return null;
		final String sql = "SELECT username FROM verification_emails WHERE token = ?";
		return jdbcTemplate.queryForObject(sql, String.class, token);
	}

	public void updateUserVerificationStatus(String username, boolean isVerified) {
		final String sql = "UPDATE users SET isverified = ? WHERE username = ?";
		jdbcTemplate.update(sql, isVerified, username);
		deleteVerificationToken(username);
	}
}
