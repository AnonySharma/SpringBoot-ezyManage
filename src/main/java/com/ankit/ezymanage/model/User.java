package com.ankit.ezymanage.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String role;
	private String email;
	private boolean isAdmin;
	private boolean isVerified;

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(int id, String username, String password, String role, String email, boolean isAdmin,
			boolean isVerified) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.isAdmin = isAdmin;
		this.isVerified = isVerified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", id=" + id + ", isAdmin=" + isAdmin + ", isVerified=" + isVerified
				+ ", password=" + password + ", role=" + role + ", username=" + username + "]";
	}

}
