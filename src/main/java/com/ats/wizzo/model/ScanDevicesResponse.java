package com.ats.wizzo.model;

import java.util.List;

public class ScanDevicesResponse {

	private List<ScanList> scanList = null;
	private Boolean error;
	private String message;
	
	public List<ScanList> getScanList() {
		return scanList;
	}
	public void setScanList(List<ScanList> scanList) {
		this.scanList = scanList;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ScanDevicesResponse [scanList=" + scanList + ", error=" + error + ", message=" + message + "]";
	}

	
	
}
