package com.ats.wizzo.model;

public class LoginResponse {

	private boolean error;
	private String message;
	private User user;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LoginResponse [error=" + error + ", message=" + message + ", user=" + user + "]";
	}

	 

}
