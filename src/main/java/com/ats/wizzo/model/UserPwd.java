package com.ats.wizzo.model;

public class UserPwd {

	private int userPwdId;
	private int userId;
	private String userPassword;

	public int getUserPwdId() {
		return userPwdId;
	}

	public void setUserPwdId(int userPwdId) {
		this.userPwdId = userPwdId;
	}

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
		return "UserPwd [userPwdId=" + userPwdId + ", userId=" + userId + ", userPassword=" + userPassword + "]";
	}

}
