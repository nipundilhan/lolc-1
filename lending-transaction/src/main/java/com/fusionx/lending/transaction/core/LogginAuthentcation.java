package com.fusionx.lending.transaction.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogginAuthentcation {
    private static final Logger logger = LoggerFactory.getLogger(LoggerRequest.class);
    private static LogginAuthentcation instance;
    private String userName;

    public static LogginAuthentcation getInstance() {
        if (instance == null) {
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
