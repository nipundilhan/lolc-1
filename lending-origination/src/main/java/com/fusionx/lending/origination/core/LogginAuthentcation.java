package com.fusionx.lending.origination.core;

public class LogginAuthentcation {

	private static LogginAuthentcation instance;
	private String userName;
	
	public static LogginAuthentcation getInstance() {
		if(instance==null) {
			instance = new LogginAuthentcation();
		}
        return instance;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
