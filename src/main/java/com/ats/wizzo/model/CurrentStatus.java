package com.ats.wizzo.model;

public class CurrentStatus {

	String devMac;
	String devType;
	String status;
	
	
	public String getDevMac() {
		return devMac;
	}
	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CurrentStatus [devMac=" + devMac + ", devType=" + devType + ", status=" + status + "]";
	}
	
	
	
}
