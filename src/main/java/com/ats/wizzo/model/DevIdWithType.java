package com.ats.wizzo.model;

public class DevIdWithType {
	
	private int devId;
	private int type;
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "DevIdWithType [devId=" + devId + ", type=" + type + "]";
	}
	 
	
	

}
