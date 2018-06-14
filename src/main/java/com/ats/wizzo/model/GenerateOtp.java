package com.ats.wizzo.model;

public class GenerateOtp {

	private int otp;
	private int userId;

 

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "GenerateOtp [otp=" + otp + ", userId=" + userId + "]";
	}

}
