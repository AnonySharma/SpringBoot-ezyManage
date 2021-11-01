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
		final String sql = "INSERT INTO users(id, username, password, role, isadmin) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isAdmin());

	}

	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet row, int i) throws SQLException {
			User user = new User();
			user.setId(row.getInt("id"));
			user.setUsername(row.getString("username"));
			user.setPassword(row.getString("password"));
			user.setRole(row.getString("role"));
			user.setAdmin(row.getBoolean("isadmin"));
			return user;
		}
	};

	public void updateUser(User user) {
		final String sql = "UPDATE users SET username = ?, password = ?, role = ?, isadmin = ? WHERE id = ?";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.isAdmin(), user.getId());
	}

	public User getUserDataByUsername(String username) {
		final String sql = "SELECT * FROM users WHERE username=?";
		return jdbcTemplate.queryForObject(sql, userRowMapper, username);
	}

	public void deleteUserByUsername(String username) {
		final String sql = "DELETE FROM users WHERE username=?";
		jdbcTemplate.update(sql, username);
	}

	public User getUserDataById(int id) {
		final String sql = "SELECT * FROM users WHERE id=?";
		return jdbcTemplate.queryForObject(sql, userRowMapper, id);
	}

	public List<User> getAllUsers() {
		final String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, userRowMapper);
	}
}
