package com.fusionx.lending.transaction.enums;


public enum AccountStatus {
    APPROVED("AP", "APPROVED"),
    ACTIVE("AP", "ACTIVE"),
    APPROVED_FINAL_WITHDRAWAL("CW", "APPROVED FINAL WITHDRAWAL");

    private String statusCode;
    private String statusDescription;

    private AccountStatus(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
