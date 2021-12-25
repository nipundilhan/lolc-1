package com.fusionx.lending.transaction.enums;

public enum ChargeType {
    CREATION("CRE", "CREATION"),
    EXECUTION("EXE", "EXECUTION");

    private String code;
    private String description;

    private ChargeType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


}
