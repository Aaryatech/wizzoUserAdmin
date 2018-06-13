package com.ats.wizzo.model;

public class UserPwd {
	private int userId;
	private String userPassword;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "UserPwd [userId=" + userId + ", userPassword=" + userPassword + "]";
	}

}
