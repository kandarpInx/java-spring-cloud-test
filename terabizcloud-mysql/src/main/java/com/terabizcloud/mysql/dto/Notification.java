package com.terabizcloud.mysql.dto;

import java.io.Serializable;

public class Notification implements Serializable {

    public Notification() {
    }

    private String notificationType;
    private String msg;
    
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}