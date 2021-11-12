package com.ankit.ezymanage.dao;

import java.util.List;

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
		final String sql = "INSERT INTO users(id, username, password, role, isadmin) VALUES(?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), user.getRole(),
					user.isAdmin());
		} catch (Exception e) {
			throw new DuplicateKeyException("Username not available!");
		}

	}

	public void updateUser(User user) {
		final String sql = "UPDATE users SET username = ?, password = ?, role = ?, isadmin = ? WHERE id = ?";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.isAdmin(), user.getId());
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
}
