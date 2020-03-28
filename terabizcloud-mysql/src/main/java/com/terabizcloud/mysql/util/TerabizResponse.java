package com.terabizcloud.mysql.util;

import java.util.Map;

public class TerabizResponse {
	
	private int status;
	private Boolean success;
	private String message;
	private Map<String, Object> data;
	private Map<String, Object> meta;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public TerabizResponse(int status, boolean success, String message, Map<String, Object> data, Map<String, Object> meta) {
		super();
		this.status = status;
		this.success = success;
		this.message = message;
		this.data = data;
		this.meta = meta;
	}
	
	public TerabizResponse(int status, boolean success, String message, Map<String, Object> data) {
		super();
		this.status = status;
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public TerabizResponse(int status, boolean success, String message) {
		super();
		this.status = status;
		this.success = success;
		this.message = message;
	}
	
	public TerabizResponse() {
		super();
	}
	
	public TerabizResponse(int status, String message, Map<String, Object> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public TerabizResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}
