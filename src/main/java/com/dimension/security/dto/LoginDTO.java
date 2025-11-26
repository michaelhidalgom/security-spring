package com.dimension.security.dto;

public class LoginDTO {

	private String email;  // ← Cambiar de username a email
	private String password;

	public String getEmail() {  // ← Cambiar getter
		return email;
	}

	public void setEmail(String email) {  // ← Cambiar setter
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}